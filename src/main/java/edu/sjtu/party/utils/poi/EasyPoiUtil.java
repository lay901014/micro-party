package edu.sjtu.party.utils.poi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import edu.sjtu.party.utils.ServletSessionUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Lay
 * @Date: 2019/6/5 10:03
 * @Description:
 */
public class EasyPoiUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(EasyPoiUtil.class);


    public static <T> List<T> importData(MultipartFile file, Class<T> cls) throws IOException {
        InputStream in = null;
        try {
            in = file.getInputStream();
            return EasyPoiUtil.importData(in, cls, new ImportParams());
        }catch (Exception e) {
            throw new RuntimeException("导入文件流异常", e);
        }finally {
            if(in != null) in.close();
        }
    }


    public static <T> List<T> importData(InputStream inputStream, Class<T> cls,
                                         ImportParams importParams) throws Exception {
        List<T> list = ExcelImportUtil.importExcel(inputStream, cls, importParams);
        return list;
    }


    public static <T> List<T> importData(Workbook workbook, Class<T> cls,
                                         ImportParams importParams) throws Exception {
        return new MyExcelImportService().importExcelByIs(workbook, cls, importParams).getList();
    }

    /**
     * easypoi 导出excel
     * @param templatePath  模板文件路径
     * @param fileName  导出后显示文件名称
     * @param dataList  数据列表
     * @param response  HttpServletResponse
     */
    public static void exportData(String templatePath, String fileName, Collection dataList, HttpServletResponse response) throws IOException {
        OutputStream out = null;
        try {
            EasyPoiUtil.setResponseHeader(fileName, response);

            out = response.getOutputStream();
            TemplateExportParams params = new TemplateExportParams(templatePath);
            Map<String,Object> data = new HashMap<>();
            data.put("list",dataList);
            Workbook wb = ExcelExportUtil.exportExcel(params, data);
            wb.write(out);
        }catch (Exception e) {
            LOGGER.error("导出文件异常", e);
        }finally {
            if(out != null) out.close();
        }

    }

    /**
     * easypoi 导出excel
     * @param templatePath  模板文件路径
     * @param fileName  导出后显示文件名称
     * @param data  数据
     * @param response  HttpServletResponse
     */
    public static void exportData(String templatePath, String fileName, Map<String, Object> data, HttpServletResponse response) throws IOException {
        OutputStream out = null;
        try {
            EasyPoiUtil.setResponseHeader(fileName, response);

            out = response.getOutputStream();
            TemplateExportParams params = new TemplateExportParams(templatePath);
            Workbook wb = ExcelExportUtil.exportExcel(params, data);
            wb.write(out);
        }catch (Exception e) {
            LOGGER.error("导出文件异常", e);
        }finally {
            if(out != null) out.close();
        }

    }

    /**
     *
     * @param fileName
     * @param dataList
     * @param cls
     * @param response
     * @param cellArr  需要导出的字段名称
     * @param <T>
     * @throws IOException
     */
    public static <T> void exportData(String fileName, Collection<T> dataList, Class<T> cls, HttpServletResponse response, String[] cellArr, String sheetName) throws IOException {
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            EasyPoiUtil.setResponseHeader(fileName, response);
            EasyPoiUtil.hideColumn(cls, cellArr);
            ExportParams params = new ExportParams(null,sheetName, ExcelType.XSSF);
            params.setStyle(CustomerExcelExportStylerImpl.class);
            Workbook wb = ExcelExportUtil.exportExcel(params, cls, dataList);
            wb.write(out);
        }catch (Exception e) {
            LOGGER.error("导出文件异常", e);
        }finally {
            if(out != null) out.close();
        }
    }

    /**
     * 导出文件模板
     * @param filePath
     * @param fileName
     * @param response
     * @throws IOException
     */
    public static void exportExcelTemplate(String filePath, String fileName, HttpServletResponse response) throws IOException {
        OutputStream out = null;
        FileInputStream fin = null;
        try {
            out = response.getOutputStream();
            EasyPoiUtil.setResponseHeader(fileName, response);
            fin = new FileInputStream(filePath);
            out = response.getOutputStream();
            byte[] buff = new byte[2048];
            int byteRead;
            while (-1 != (byteRead = fin.read(buff))) {
                out.write(buff, 0, byteRead);
            }
        }catch (Exception e) {
            LOGGER.error("导出模板文件异常", e);
        }
        finally {
            if(out != null) out.close();
            if(fin != null) fin.close();
        }
    }


    public static void setResponseHeader(String fileName, HttpServletResponse response) {
        try {
            String agent = ServletSessionUtil.getRequest().getHeader("USER-AGENT").toLowerCase();
            if (agent.contains("firefox")) {
                response.setCharacterEncoding("utf-8");
                fileName = fileName.replaceAll("\\s+","%20");
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } else {
                fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            }
            fileName=fileName.replaceAll("\\+","%20");

            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + fileName + ".xlsx");
        }catch (Exception e) {
            LOGGER.error("设置header异常", e);
        }

    }

    /**
     * 动态更改EasyPoi中控制列显示的值
     *
     * @param cls 实体类
     * @param cellArr 展示列数组
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static <T> void hideColumn(Class<T> cls, String[] cellArr) throws Exception {
        if (cls == null) {
            throw new ClassNotFoundException("TARGET OBJECT NOT FOUNT");
        }
        //获取目标对象的属性值
        Field[] fieldArr =  cls.getDeclaredFields();
        for(Field field : fieldArr) {
            Excel excelAnnon = field.getAnnotation(Excel.class);
            if(excelAnnon != null) {
                //获取代理
                InvocationHandler invocationHandler = Proxy.getInvocationHandler(excelAnnon);
                Field excelField = invocationHandler.getClass().getDeclaredField("memberValues");
                excelField.setAccessible(true);
                Map memberValues = (Map) excelField.get(invocationHandler);
                if(!ArrayUtils.isEmpty(cellArr) && !ArrayUtils.contains(cellArr, field.getName())) {
                    memberValues.put("isColumnHidden", true);
                }else {
                    memberValues.put("isColumnHidden", false);
                }
            }
        }
    }
}
