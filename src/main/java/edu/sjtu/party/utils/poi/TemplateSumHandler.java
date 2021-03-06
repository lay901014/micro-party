package edu.sjtu.party.utils.poi;

import cn.afterturn.easypoi.util.PoiCellUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.afterturn.easypoi.util.PoiElUtil.SUM;

/**
 * @description:
 * @author: lay
 * @create: 2020/09/14 10:31
 **/
public class TemplateSumHandler {

    private Map<String, TemplateSumEntity> sumMap = new HashMap<String, TemplateSumEntity>();

    public TemplateSumHandler(Sheet sheet) {
        getAllSumCell(sheet);
    }

    /**
     * 统计计算所有的统计单元格
     */
    private void getAllSumCell(Sheet sheet) {
        Row row = null;
        int index = 0;
        while (index <= sheet.getLastRowNum()) {
            row = sheet.getRow(index++);
            if (row == null) {
                continue;
            }
            for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
                if (row.getCell(i) != null && PoiCellUtil.getCellValue(row.getCell(i)).contains(SUM)) {
                    addSumCellToList(row.getCell(i));
                }
            }
        }
    }

    private void addSumCellToList(Cell cell) {
        String cellValue = cell.getStringCellValue();
        int index = 0;
        while ((index = indexOfIgnoreCase(cellValue, SUM, index)) != -1) {
            TemplateSumHandler.TemplateSumEntity entity = new TemplateSumHandler.TemplateSumEntity();
            entity.setCellValue(cellValue);
            entity.setSumKey(getSumKey(cellValue, index++));
            entity.setCol(cell.getColumnIndex());
            entity.setRow(cell.getRowIndex());
            sumMap.put(entity.getSumKey(), entity);
        }
    }

    public boolean isSumKey(String key) {
        return sumMap.containsKey(key);
    }

    /**
     * SUM:(key)
     *
     * @param cellValue
     * @param index
     * @return
     */
    private String getSumKey(String cellValue, int index) {
        return cellValue.substring(index + 5, cellValue.indexOf(")", index));
    }

    public void addValueOfKey(String key, String val) {
        if (StringUtils.isNoneEmpty(key)) {
            sumMap.get(key).setValue(sumMap.get(key).getValue() + Double.valueOf(val));
        }
    }

    public List<TemplateSumEntity> getDataList() {
        return new ArrayList<TemplateSumEntity>(sumMap.values());
    }

    public void addListSizeToSumEntity() {

    }

    /**
     *
     * @param rowIndex
     * @param size
     */
    public void shiftRows(int rowIndex, int size) {
        for (TemplateSumHandler.TemplateSumEntity entity : getDataList()) {
            if (entity.getRow() > rowIndex) {
                entity.setRow(entity.getRow() + size);
            }
        }

    }

    private static int indexOfIgnoreCase(String str, String searchStr, int startPos) {
        if (str == null || searchStr == null) {
            return -1;
        }
        if (startPos < 0) {
            startPos = 0;
        }
        int endLimit = (str.length() - searchStr.length()) + 1;
        if (startPos > endLimit) {
            return -1;
        }
        if (searchStr.length() == 0) {
            return startPos;
        }
        for (int i = startPos; i < endLimit; i++) {
            if (str.regionMatches(true, i, searchStr, 0, searchStr.length())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 统计对象
     * @author JueYue
     */
    protected class TemplateSumEntity {
        /**
         * CELL的值
         */
        private String cellValue;
        /**
         * 需要计算的KEY
         */
        private String sumKey;
        /**
         * 列
         */
        private int    col;
        /**
         * 行
         */
        private int    row;
        /**
         * 最后值
         */
        private double value;

        public String getCellValue() {
            return cellValue;
        }

        public void setCellValue(String cellValue) {
            this.cellValue = cellValue;
        }

        public String getSumKey() {
            return sumKey;
        }

        public void setSumKey(String sumKey) {
            this.sumKey = sumKey;
        }

        public int getCol() {
            return col;
        }

        public void setCol(int col) {
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

    }

}
