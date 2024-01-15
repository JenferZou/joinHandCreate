package com.atxbai.online.common.excel;


import org.apache.poi.ss.usermodel.*;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.*;

public class ExcelHandle<T> {
    /**
     *
     * @param inputStream
     * @throws Exception
     */
    public List<Object> handlerData(InputStream inputStream,LinkedHashMap<String, String> fieldMap,Class<T> clazz) throws Exception {
        // 创建构造器
        List<Object> objects=new ArrayList<>();
        // 创建表格对象
        //XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        Workbook workbook=WorkbookFactory.create(inputStream);
        //有多少个sheet
        int sheets = workbook.getNumberOfSheets();
        for (int i = 0; i < sheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            List<String> name=new ArrayList<>();
            //获取多少行
            int rows = sheet.getPhysicalNumberOfRows();
            //第零行,获取表头信息
            Row row = sheet.getRow(0);
            for(int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++)
            {
                Object cel=getCellValue(row.getCell(cellNum));
                if(Objects.nonNull(cel)) {
                    name.add(cel.toString());
                }
            }
            //注意：第0为表头，第1行开始解析数据
            for (int currentRow = 1; currentRow < rows; currentRow++) {
               Object o= parseCellData(sheet, currentRow,name, fieldMap,clazz);
               if(o!=null)
                objects.add(o);
            }
        }
        // 所有数据解析完成后
        return objects;
    }
    /**
     * 获取表格数据
     * @param sheet
     * @param currentRow
     * @return
     */
    private Object parseCellData(Sheet sheet, int currentRow,List<String> list,LinkedHashMap<String, String> fieldMap,Class<T> clazz) throws Exception {
        //存放一行数据的map
        Map<String,Object> maps=new HashMap<>();
        //获得第行号
        Row row = sheet.getRow(currentRow);
        if(isRowEmpty(row))
            return null;
        //System.out.println("行号:row:"+row);
        // 解析正文数据
        List<Object> values = new ArrayList<>();
        //System.out.println("last:"+row.getLastCellNum());
        for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            Object value = getCellValue(cell);
            maps.put(list.get(cellNum),value);
        }
       Object student = clazz.getDeclaredConstructor().newInstance();
        maps.forEach((k,v)->{
            String name="";
            try {
                 name=fieldMap.get(k);
                if(name!=null) {
                    if (Character.isLowerCase(name.charAt(1))) {
                        name = name.substring(0, 1).toUpperCase() + name.substring(1);
                    }
                    if(name.equals("Score")){
                        Method m = clazz.getMethod("set" + name, Double.class);
                        m.invoke(student, new Object[]{v == null ? "" : Double.valueOf(v.toString())});
                    }else {
                        Method m = clazz.getMethod("set" + name, String.class);
                        m.invoke(student, new Object[]{v == null ? "" : v.toString()});
                    }

                }
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
      return student;
        //endRow(values, currentRow);
    }
   // 没有数据的空行和没格式的空行判断
    public static boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() !=CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    /**
     * 解析数据类型
     *
     * @param cell
     * @return
     */
    private Object getCellValue(Cell cell) {
        //1.获取到单元格的属性类型
        CellType cellType = cell.getCellType();
        //科学记数法转换
        NumberFormat nf = NumberFormat.getInstance();
        //2.根据单元格数据类型获取数据
        Object value = null;
        switch (cellType) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    //日期格式
                    value = cell.getDateCellValue();
                } else {
                    //数字,poi解析的数值都是double类型
                    value = String.valueOf(nf.format(cell.getNumericCellValue()));
                    value = ((String) value).replace(",","");
                }
                break;
            case FORMULA:
                //公式
                value = cell.getCellFormula();
                break;
            default:
                break;
        }
        return value;
    }

}
