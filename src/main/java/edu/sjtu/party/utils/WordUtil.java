package edu.sjtu.party.utils;

import com.aspose.words.Document;
import com.aspose.words.FontSettings;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @description:
 * @author: lay
 * @create: 2021/10/12 09:21
 **/
public class WordUtil {

    private static Configuration configuration = null;
    //private static String templateFolder = "C:/Users/Jason/Desktop";
    private static String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    static {
        configuration = new Configuration(Configuration.getVersion());
        configuration.setDefaultEncoding("utf-8");
//        try {
//            configuration.setDirectoryForTemplateLoading(new File(templateFolder));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    @SuppressWarnings("rawtypes")
    public static void exporPdf(HttpServletRequest request, HttpServletResponse response, Object object, String title, String ftlFile) throws IOException {
        configuration.setServletContextForTemplateLoading(request.getSession().getServletContext(), "/template");
        Template freemarkerTemplate = configuration.getTemplate(ftlFile);
        File doc = File.createTempFile("temp", ".doc");
        OutputStream out = new FileOutputStream(doc);;
        Writer w = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/pdf;charset=utf-8");
            String fileName = title + ".pdf";
            String userAgent = request.getHeader("USER-AGENT");
            String finalFileName = null;
            //解决Firefox下载英文+中文组合的文件名的问题
            if(StringUtils.contains(userAgent, "Firefox")){//火狐浏览器
                finalFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }else if(StringUtils.contains(userAgent, "windows")){//IE，google等其他浏览器
                finalFileName = URLEncoder.encode(fileName,"UTF8").replace("+", "%20");
            }else {
                finalFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }
            response.setHeader("Content-Disposition", "attachment; filename=\"" + finalFileName + "\"");
//            response.setHeader("Content-Disposition", "attachment;filename="
//                    .concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));

            w = new OutputStreamWriter(out, "utf-8");
            freemarkerTemplate.process(object, w);
            w.flush();
            doc2pdf(doc.getCanonicalPath(), response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null) out.close();
            if (w != null) w.close();
            doc.delete();
        }
    }

    /**
     * @return java.lang.String
     * @Description //TODO 处理转义字符
     * @Date 20:01 2019/3/26
     * @Param [str]
     **/
    public static String transform(String str) {
        if (str.contains("<") || str.contains(">") || str.contains("&")) {
            str = str.replaceAll("&", "&amp;");
            str = str.replaceAll("<", "&lt;");
            str = str.replaceAll(">", "&gt;");
        }
        return str;
    }

    public static void doc2pdf(String Address, HttpServletResponse response) throws IOException {

        if (!getLicense()) {          // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        OutputStream os = null;
        try {
            long old = System.currentTimeMillis();
            os = response.getOutputStream();
            FontSettings.setFontsFolder("/opt/fonts", false);
            Document doc = new Document(Address);                    //Address是将要被转化的word文档
            doc.save(os, SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒");  //转化用时
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (os != null){
                os.close();
            }
        }
    }

    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = WordUtil.class.getClassLoader().getResourceAsStream("license.xml"); //  license.xml应放在..\WebRoot\WEB-INF\classes路径下
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
