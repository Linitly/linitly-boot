package org.linitly.boot.base.utils;//package com.zhencang.pipe.gallery.utils;
//
//import ExceptionResultEnum;
//import CommonException;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.poi.hssf.usermodel.HSSFDateUtil;
//import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.openxml4j.opc.OPCPackage;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.xssf.eventusermodel.XSSFReader;
//import org.apache.poi.xssf.model.SharedStringsTable;
//import org.apache.poi.xssf.model.StylesTable;
//import org.apache.poi.xssf.usermodel.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.xml.sax.Attributes;
//import org.xml.sax.InputSource;
//import org.xml.sax.SAXException;
//import org.xml.sax.XMLReader;
//import org.xml.sax.helpers.DefaultHandler;
//import org.xml.sax.helpers.XMLReaderFactory;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.UnsupportedEncodingException;
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.math.BigDecimal;
//import java.net.URLEncoder;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * @author Administrator
// */
//public class ExcelImportUtil {
//    public static final String END_POSITION = "ENDPOS";
//
//    /**
//     * 根据文件名后缀，创建不同Workbook
//     *
//     * @param is
//     * @param excelFilename
//     * @return
//     * @throws IOException
//     */
//    public static Workbook createWorkbook(InputStream is, String excelFilename) throws IOException {
//        if (excelFilename.endsWith(".xls")) {
//            return new HSSFWorkbook(is);
//        } else if (excelFilename.endsWith(".xlsx")) {
//            return new XSSFWorkbook(is);
//        } else {
//            throw new CommonException(ExceptionResultEnum.EXCEL_UPLOAD_FORMAT_ERROR);
//        }
//    }
//
//    /**
//     * 根据不同workbook，创建不同公式读取器
//     *
//     * @param workbook
//     * @return
//     */
//    public static FormulaEvaluator createFormulaEvaluator(Workbook workbook) {
//        if (workbook instanceof HSSFWorkbook) {
//            return new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
//        } else if (workbook instanceof XSSFWorkbook) {
//            return new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
//        } else {
//            return null;
//        }
//    }
//
//    public static Date formatDate(String source, String formatStr) throws ParseException {
//        SimpleDateFormat format = new SimpleDateFormat(formatStr);
//        return format.parse(source);
//    }
//
//    /**
//     * 检验Excel中sheet数及有效记录行数
//     *
//     * @param wb
//     * @param floorLimit:行数下限
//     * @param upperLimit:行数上限
//     */
//    public static void validateNum(Workbook wb, int floorLimit, int upperLimit) {
//        int numberOfNullSheet = 0;
//        if (wb.getNumberOfSheets() < 1) {
//            throw new CommonException(ExceptionResultEnum.EXCEL_SHEET_ANALIZE_ERROR);
//        }
//        int totalNum = 0;
//        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
//            if (wb.getSheetAt(i).getPhysicalNumberOfRows() == 0) {
//                numberOfNullSheet++;
//            } else {
//                totalNum += wb.getSheetAt(i).getPhysicalNumberOfRows();
//            }
//        }
//        if ((totalNum - (wb.getNumberOfSheets() - numberOfNullSheet)) <= floorLimit) {
//            throw new CommonException(ExceptionResultEnum.EXCEL_FLOOR_LIMIT_ERROR);
//        }
//        if ((totalNum - (wb.getNumberOfSheets() - numberOfNullSheet)) > upperLimit) {
//            throw new CommonException(ExceptionResultEnum.EXCEL_UPPER_LIMIT_ERROR);
//        }
//    }
//
//    /**
//     * 检查单元格是否为null
//     *
//     * @param cell
//     * @param rowIndex
//     * @param columnIndex
//     */
//    public static void assertNull(Cell cell, int rowIndex, int columnIndex) {
//        if (null == cell) {
//            throw new CommonException(421, "第" + (rowIndex + 1) + "行，第" + (columnIndex + 1) + "列为空。\n");
//        }
//    }
//
//    public static void assertNull(String cell, int rowIndex, int columnIndex) {
//        if (null == cell) {
//            throw new CommonException(421, "第" + (rowIndex + 1) + "行，第" + (columnIndex + 1) + "列为空。\n");
//        }
//    }
//
//    /**
//     * 获取单元格行列信息
//     *
//     * @param cell
//     * @return
//     */
//    public static String indexInfo(Cell cell) {
//        StringBuffer buffer = new StringBuffer();
//        return buffer.append("第").append(cell.getRowIndex() + 1).append("行").append(" 第")
//                .append(cell.getColumnIndex() + 1).append("列").toString();
//    }
//
//    /**
//     * 从单元格中获取String值，可为空
//     *
//     * @param cell
//     * @return
//     */
//    public static String getStringCellValue(Cell cell) {
//        if (null == cell) {
//            return null;
//        }
//        switch (cell.getCellType()) {
//            case BLANK:
//                return null;
//            case STRING:
//                return cell.getStringCellValue().trim();
//            case NUMERIC:
//                return String.valueOf(BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0, BigDecimal.ROUND_HALF_UP));
//            default:
//                throw new CommonException(421, indexInfo(cell) + "单元格解析失败。\n");
//        }
//    }
//
//    /**
//     * 从单元格中获取String值，空值抛错，为防止cell为null时获取不到行列信息，将行数、列数作为参数传递 浮点数直接读取成int
//     *
//     * @param cell
//     * @return
//     */
//    public static String getNotBlankStringCellValue(Cell cell, int rowNum, int columnNum) {
//        String value;
//        if (null == cell) {
//            throw new CommonException(421, "第" + (rowNum + 1) + "行，第" + (columnNum + 1) + "列为空。\n");
//        }
//        switch (cell.getCellType()) {
//            case BLANK:
//                throw new CommonException(421, indexInfo(cell) + "单元格为空。\n");
//            case NUMERIC:
//                value = String.valueOf(BigDecimal.valueOf(cell.getNumericCellValue()).setScale(0));// 可以先转成int
//                break;
//            case STRING:
//                value = cell.getStringCellValue().replace("\u200e", "").trim();
//                break;
//            default:
//                throw new CommonException(421, indexInfo(cell) + "单元格格式解析失败。\n");
//        }
//        if (StringUtils.isBlank(value)) {
//            throw new CommonException(421, indexInfo(cell) + "单元格值为空。\n");
//        }
//        return value;
//    }
//
//    /**
//     * 从文本或日期格式单元格中读取Date(允许返回null时，方法中 cell 未做null判断，调用时需提前检查)
//     *
//     * @param cell
//     * @param nullable 是否允许返回null值
//     * @return
//     */
//    public static Date getDateCellValue(Cell cell, boolean nullable) {
//        if (nullable && null == cell) {
//            return null;
//        }
//        Date value = null;
//        switch (cell.getCellType()) {
//            case BLANK:
//                value = null;
//                break;
//            case NUMERIC:
//                if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                    value = cell.getDateCellValue();
//                    break;
//                }
//                throw new CommonException(421, indexInfo(cell) + "无法解析为日期。\n");
//            case STRING:
//                try {
//                    String str = cell.getStringCellValue();
//                    if (str.matches("^((((19|20)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((19|20)\\d{2})-(0?[469]|11)-(0?[1-9]|[12]\\d|30))|(((19|20)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|((((19|20)([13579][26]|[2468][048]|0[48]))|(2000))-0?2-(0?[1-9]|[12]\\d)))(\\s(20|21|22|23|[0-1]\\d).[0-5]\\d.[0-5]\\d)?")) {
//                        value = formatDate(cell.getStringCellValue(), "yyyy-MM-dd");
//                    } else if (str.matches("^((((19|20)\\d{2})/(0?[13578]|1[02])/(0?[1-9]|[12]\\d|3[01]))|(((19|20)\\d{2})/(0?[469]|11)/(0?[1-9]|[12]\\d|30))|(((19|20)\\d{2})/0?2/(0?[1-9]|1\\d|2[0-8]))|((((19|20)([13579][26]|[2468][048]|0[48]))|(2000))/0?2/(0?[1-9]|[12]\\d)))(\\s(20|21|22|23|[0-1]\\d).[0-5]\\d.[0-5]\\d)?")) {
//                        value = formatDate(cell.getStringCellValue(), "yyyy/MM/dd");
//                    } else if (str.matches("^((((19|20)\\d{2})\\.(0?[13578]|1[02])\\.(0?[1-9]|[12]\\d|3[01]))|(((19|20)\\d{2})\\.(0?[469]|11)\\.(0?[1-9]|[12]\\d|30))|(((19|20)\\d{2})\\.0?2\\.(0?[1-9]|1\\d|2[0-8]))|((((19|20)([13579][26]|[2468][048]|0[48]))|(2000))\\.0?2\\.(0?[1-9]|[12]\\d)))(\\s(20|21|22|23|[0-1]\\d).[0-5]\\d.[0-5]\\d)?")) {
//                        value = formatDate(cell.getStringCellValue(), "yyyy.MM.dd");
//                    } else if (str.matches("^//d{8}$")) {
//                        value = formatDate(cell.getStringCellValue(), "yyyyMMdd");
//                    }
//                    throw new ParseException(null, 0);
//                } catch (ParseException e) {
//                    throw new CommonException(421, indexInfo(cell) + "无法解析为日期。\n");
//                }
//            default:
//                throw new CommonException(421, indexInfo(cell) + "格式无法解析。\n");
//        }
//        if (!nullable && null == value) {
//            throw new CommonException(421, indexInfo(cell) + "日期为空。\n");
//        }
//        return value;
//    }
//
//    /**
//     * 从单元格中获取double值(两位小数)，空值返回0
//     *
//     * @param cell
//     * @return
//     */
//    public static Double getDoubleCellValue(Cell cell, FormulaEvaluator formulaEvaluator) {
//        double value;
//        if (null == cell) {
//            return 0d;
//        }
//        switch (cell.getCellType()) {
//            case BLANK:
//                return 0d;
//            case NUMERIC:
//                value = cell.getNumericCellValue();
//                break;
//            case FORMULA:
//                value = formulaEvaluator.evaluate(cell).getNumberValue();
//                break;
//            case STRING:
//                String stringValue = cell.getStringCellValue();
//                if (StringUtils.isBlank(stringValue)) {
//                    value = 0d;
//                } else {
//                    try {
//                        value = Double.valueOf(stringValue.trim());
//                    } catch (NumberFormatException e) {
//                        throw new CommonException(421, indexInfo(cell) + "数值读取失败。\n");
//                    }
//                }
//                break;
//            default:
//                throw new CommonException(421, indexInfo(cell) + "单元格格式解析失败。\n");
//        }
//        BigDecimal b = new BigDecimal(value);
//        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();// 保留两位小数
//    }
//
//    /**
//     * Excel导出
//     *
//     * @param title
//     * @param headers
//     * @param codes
//     * @param dataSet
//     * @param response
//     */
//    public static <T> void downloadExcel(String title, String guide, String[] headers, String[] codes,
//                                         List<T> dataSet, HttpServletResponse response) {
//        String downloadName = null;
//        try {
//            downloadName = URLEncoder.encode(title + ".xlsx", "UTF-8");
//        } catch (UnsupportedEncodingException e1) {
//            e1.printStackTrace();
//        }
//        response.reset();
//        response.setContentType("application/ms-excel;charset=utf-8");
//        response.setHeader("Content-Disposition", "attachment;filename=" + downloadName);
//        OutputStream outs = null;
//        try {
//            outs = response.getOutputStream();
//            exportExcel(title, guide, headers, codes, dataSet, outs);
//        } catch (Exception e) {
//            response.setContentType("application/json");
//            e.printStackTrace();
//            throw new CommonException(ExceptionResultEnum.FILEDOWNLOAD_ERROR);
//        } finally {
//            try {
//                outs.flush();
//                outs.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 通用导出excel
//     *
//     * @param title：sheet名
//     * @param headers：列名
//     * @param codes:列名对应的属性
//     * @param dataSet:放入javabean数据集合
//     * @param out：流对象
//     */
//    @SuppressWarnings("rawtypes")
//    public static <T> void exportExcel(String title, String guide, String[] headers, String[] codes, List<T> dataSet,
//                                       OutputStream out) throws Exception {
//        @SuppressWarnings("resource")
//        XSSFWorkbook wb = new XSSFWorkbook();
//        XSSFSheet sheet = wb.createSheet(title);
////        SXSSFWorkbook wb = new SXSSFWorkbook(100);
////        Sheet sheet = wb.createSheet(title);
//        sheet.setDefaultColumnWidth(15);
//        Row row;
//        Cell cell;
//
//        // 通过判断guide字段是否为空 从而得出是下载模板还是导出数据 若是下载模板则保留第一行合并 用于展示模板填写说明
//        if (null != guide && !"".equals(guide.trim())) {
//            int height = guide.split("\n").length;
////			XSSFCellStyle cellStyle = wb.createCellStyle();
//            CellStyle cellStyle = wb.createCellStyle();
//            cellStyle.setWrapText(true);
//            cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
//            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//            Row guideRow = sheet.createRow(0);
//            Cell guideCell = guideRow.createCell(0);
//            guideCell.setCellStyle(cellStyle);
//            guideRow.setHeight((short) (280 * height));
//            guideCell.setCellValue(guide);
//            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length - 1));
//            row = sheet.createRow(1);
//        } else {
//            row = sheet.createRow(0);
//        }
//
//        for (int i = 0; i < headers.length; i++) {
//            sheet.setColumnWidth(i, (headers[i].length() * 576));
//            cell = row.createCell(i);
//            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
//            cell.setCellValue(text);
//        }
//        // 创建数据行
//        if (null != dataSet) {
//            Iterator<T> it = dataSet.iterator();
//            int index = 0;
//            while (it.hasNext()) {
//                index++;
//                row = sheet.createRow(index);
//                T cpr = it.next();
//                for (int i = 0; i < headers.length; i++) {
//                    cell = row.createCell(i);
//                    Object value = null;
//                    if (codes[i].contains(".")) {
//                        String[] ss = codes[i].split("\\.");
//                        value = getTarget(cpr, ss[0]);
//                        for (int j = 1; j < ss.length; j++) {
//                            value = getTarget(value, ss[j]);
//                        }
//                    } else {
//                        value = getTarget(cpr, codes[i]);
//                    }
//                    if (value instanceof Double) {
//                        setCellDoubleValue(cell, (Double) value);
//                    } else if (value instanceof Date) {
//                        setCellStringDateValue(cell, (Date) value);
//                    } else if (value instanceof String) {
//                        setCellStringValue(cell, (String) value);
//                    } else if (value instanceof Boolean) {
//                        setCellStringValue(cell, value.toString());
//                    } else if (value instanceof BigDecimal) {
//                        setBigDecimalValue(cell, (BigDecimal) value);
//                    } else if (value instanceof Integer) {
//                        setCellStringValue(cell, value.toString());
//                    } else if (value instanceof Long) {
//                        setCellStringValue(cell, value.toString());
//                    }else {
//                        cell.setBlank();
//                    }
//                }
//            }
//        }
//        wb.write(out);
//    }
//
//    public static Object getTarget(Object obj, String code) throws Exception {
//        return obj.getClass().getMethod("get" + StringUtil.toUpperCaseFirstOne(code)).invoke(obj);
//    }
//
//    public static void setCellStringValue(Cell cell, String str) {
//        if (str == null) {
//            cell.setBlank();
//        } else {
//            cell.setCellValue(str);
//        }
//    }
//
//    public static void setBigDecimalValue(Cell cell, BigDecimal bigDecimalValue) {
//        if (bigDecimalValue == null) {
//            cell.setBlank();
//        } else {
//            cell.setCellValue(bigDecimalValue.doubleValue());
//        }
//    }
//
//    public static void setCellDoubleValue(Cell cell, Double doubleValue) {
//        if (doubleValue == null) {
//            cell.setBlank();
//        } else {
//            cell.setCellValue(doubleValue);
//        }
//    }
//
//    public static void setCellStringDateValue(Cell cell, Date date) {
//        if (date == null) {
//            cell.setBlank();
//        } else {
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String signDate = format.format(date);
//            cell.setCellValue(signDate);
//        }
//    }
//
//    /**
//     * excel值读取至对象属性
//     *
//     * @param excelFile 上传excel文件
//     * @param clz       javaBean的class类型
//     * @return javaBean集合
//     */
//    public static <T> List<T> readExcel(MultipartFile excelFile, Class<T> clz) {
//        String fileName = excelFile.getOriginalFilename();
//        InputStream ins = null;
//        Workbook wb = null;
//        try {
//            ins = excelFile.getInputStream();
//            wb = ExcelImportUtil.createWorkbook(ins, fileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        List<T> list = new ArrayList<>();
//        if (wb.getNumberOfSheets() < 1) {
//            throw new CommonException(ExceptionResultEnum.EXCEL_SHEET_ANALIZE_ERROR);
//        }
//        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
//            Sheet sheet = wb.getSheetAt(i);
//            if (null != sheet) {
//                int totalRows = sheet.getPhysicalNumberOfRows();
//                int totalCells = 0;
//                if (totalRows >= 3 && sheet.getRow(2) != null) {
//                    totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
//                }
//                // 获取表单中的set方法属性
//                String[] nameOfSetMethod = new String[totalCells];
//                Method[] setMethods = new Method[totalCells];
//                try {
//                    for (int j = 0; j < totalCells; j++) {
//                        nameOfSetMethod[j] = sheet.getRow(1).getCell(j).getStringCellValue();
//                        String first = nameOfSetMethod[j].substring(0, 1);
//                        nameOfSetMethod[j] = nameOfSetMethod[j].replaceFirst(first, first.toUpperCase());
//                        System.out.println(nameOfSetMethod[j]);
//                        Method[] allMethod = clz.getMethods();
//                        for (Method method : allMethod) {
//                            if (method.getName().contains("set" + nameOfSetMethod[j])) {
//                                setMethods[j] = method;
//                                break;
//                            }
//                        }
//                    }
//                } catch (Exception e) {
//                    throw new CommonException(ExceptionResultEnum.EXCEL_MODULE_ANALIZE_ERROR);
//                }
//                for (int r = 2; r < totalRows; r++) {
//                    Row row = sheet.getRow(r);
//                    if (row == null) {
//                        continue;
//                    }
//                    T t = null;
//                    try {
//                        t = clz.newInstance();
//                    } catch (InstantiationException e) {
//                        e.printStackTrace();
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                    for (int c = 0; c < totalCells; c++) {
//                        Cell cell = row.getCell(c);
//                        try {
//                            if (CellType.STRING.equals(cell.getCellType())) {
//                                String value = cell.getStringCellValue();
//                                setMethods[c].invoke(t, value);
//                            } else if (CellType.NUMERIC.equals(cell.getCellType())) {
//                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                                    setMethods[c].invoke(t, cell.getDateCellValue());
//                                } else {
//                                    setMethods[c].invoke(t, cell.getNumericCellValue());
//                                }
//                            } else {
//                                throw new CommonException(209, "第" + (r + 1) + "行，第" + (c + 1) + "列数据为空，请检查");
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            throw new CommonException(209, "第" + (r + 1) + "行，第" + (c + 1) + "列，数据为空或无法读取，请检查");
//                        }
//                    }
//                    list.add(t);
//                }
//            }
//        }
//        return list;
//    }
//    /**
//     * 排行榜excel值读取至对象属性
//     *
//     * @param excelFile 上传excel文件
//     * @param clz       javaBean的class类型
//     * @param propertyNameList excel 对应反射属性名称
//     * @param rankingId   上传排行榜id
//     * @param deadlineTime 上传截至时间
//     * @return javaBean集合
//     * @param <T>
//     */
//    public static <T> List<T> rankingReadExcelByReflex(MultipartFile excelFile, Class<T> clz, List<String> propertyNameList,Integer rankingId, Date deadlineTime) {
//        String fileName = excelFile.getOriginalFilename();
//        InputStream ins = null;
//        Workbook wb = null;
//        try {
//            ins = excelFile.getInputStream();
//            wb = ExcelImportUtil.createWorkbook(ins, fileName);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new CommonException(209, "上传文件仅限.xls、.xlsx，请重新上传");
//        }
//        List<T> list = new ArrayList<>();
//        if (wb.getNumberOfSheets() < 1) {
//            throw new CommonException(ExceptionResultEnum.EXCEL_SHEET_ANALIZE_ERROR);
//        }
//        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
//            if (i != 0) {
//                break;
//            }
//            Sheet sheet = wb.getSheetAt(i);
//            int totalRows = sheet.getLastRowNum() + 1;
//            if (null != sheet) {
//                // 确认数据获取行,列 "排名"的下一行
//                int startRow = 0;
//                int startCell = 0;
//                int totalCells = 0;
//                for (int j = 0; j < totalRows  ; j++) {
//                    Row row = sheet.getRow(j);
//                    if (row != null) {
//                        for (int k = 0; k < row.getLastCellNum() ; k++) {
//                            if (row.getCell(k) != null && row.getCell(k).getCellType() == CellType.STRING){
//                                if ("排名".equals(row.getCell(k).getStringCellValue())){
//                                    totalCells = row.getLastCellNum() + 1;
//                                    startRow = j + 1;
//                                    startCell = k;
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                }
//                if (startRow == 0){
//                    throw new CommonException(209, "模板错误,请重试");
//                }
//                for (int j = startRow; j < totalRows; j++) {
//                    Row row = sheet.getRow(j);
//                    if (row == null) {
//                        continue;
//                    }
//                    T t = null;
//                    try {
//                        t = clz.newInstance();
//                    } catch (InstantiationException e) {
//                        e.printStackTrace();
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        if (rankingId != null) {
//                            Method rankingIdSetMethod = clz.getDeclaredMethod("setRankingId",Integer.class);
//                            rankingIdSetMethod.invoke(t,rankingId);
//                        }
//                        if (deadlineTime != null) {
//                            Method rankingIdSetMethod = clz.getDeclaredMethod("setDeadlineTime",Date.class);
//                            rankingIdSetMethod.invoke(t,deadlineTime);
//                        }
//                    } catch (NoSuchMethodException e) {
//                        e.printStackTrace();
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    } catch (InvocationTargetException e) {
//                        e.printStackTrace();
//                    }
//
//                    boolean allow = false;
//                    int propertyId = 0;
//                    for (int k = startCell; k < totalCells ; k++) {
//                        Cell cell = row.getCell(k);
//                        if (cell == null ){
//                            continue;
//                        }
//                        // 列取值 对应模板列 越界 或 取出空值不处理
//                        if (propertyId > propertyNameList.size() - 1 || StringUtils.isBlank(propertyNameList.get(propertyId))) {
//                            continue;
//                        }
//                        String propertyName = propertyNameList.get(propertyId);
//                        propertyId +=1;
//                        String validatePropertyName = "valid" + StringUtil.toUpperCaseFirstOne(propertyName);
//                        try {
//                            Field field = clz.getDeclaredField(propertyName);
//                            propertyName = "set" + StringUtil.toUpperCaseFirstOne(propertyName);
//                            Method method = clz.getDeclaredMethod(propertyName,field.getType());
//                            if ("String".equals(field.getType().getSimpleName())){
//                                if (cell.getCellType().equals(CellType.BLANK)){
//                                    throw new CommonException(209, "导入文件中第" + (j + 1) + "行第" + (k + 1) + "列数据格式错误");
//                                }
//                                if (!CellType.STRING.equals(cell.getCellType())){
//                                    cell.setCellType(CellType.STRING);
//                                }
//                                method.invoke(t,cell.getStringCellValue());
//                                allow = true;
//                            }else if ("Integer".equals(field.getType().getSimpleName()) || "int".equals(field.getType().getSimpleName())){
//                                if (cell.getCellType().equals(CellType.BLANK)){
//                                    throw new CommonException(209, "导入文件中第" + (j + 1) + "行第" + (k + 1) + "列数据格式错误");
//                                }
//                                double dd = Double.valueOf(cell.getNumericCellValue());
//                                if (dd%1 != 0){
//                                    throw new CommonException(209, "导入文件中第" + (j + 1) + "行第" + (k + 1) + "列数据格式错误");
//                                }
//                                method.invoke(t,Double.valueOf(cell.getNumericCellValue()).intValue());
//                                allow = true;
//                            }else if ("Double".equals(field.getType().getSimpleName()) || "double".equals(field.getType().getSimpleName())){
//                                if (cell.getCellType().equals(CellType.BLANK)){
//                                    throw new CommonException(209, "导入文件中第" + (j + 1) + "行第" + (k + 1) + "列数据格式错误");
//                                }
//                                method.invoke(t,Double.valueOf(cell.getNumericCellValue()));
//                                allow = true;
//                            }else if ("Date".equals(field.getType().getSimpleName())){
//                                if (cell.getCellType().equals(CellType.BLANK)){
//                                    throw new CommonException(209, "导入文件中第" + (j + 1) + "行第" + (k + 1) + "列数据格式错误");
//                                }
//                                method.invoke(t,cell.getDateCellValue());
//                                allow = true;
//                            }else if ("Long".equals(field.getType().getSimpleName()) || "long".equals(field.getType().getSimpleName())){
//                                if (cell.getCellType().equals(CellType.BLANK)){
//                                    throw new CommonException(209, "导入文件中第" + (j + 1) + "行第" + (k + 1) + "列数据格式错误");
//                                }
//                                method.invoke(t,Double.valueOf(cell.getNumericCellValue()).longValue());
//                                allow = true;
//                            }else if ("BigDecimal".equals(field.getType().getSimpleName())){
//                                if (cell.getCellType().equals(CellType.BLANK)){
//                                    throw new CommonException(209, "导入文件中第" + (j + 1) + "行第" + (k + 1) + "列数据格式错误");
//                                }
//                                method.invoke(t,new BigDecimal(cell.getNumericCellValue()));
//                                allow = true;
//                            }else {
//                                throw new CommonException(209, "导入文件中第" + (j + 1) + "行第" + (k + 1) + "列数据格式错误");
//                            }
//                        }catch (InvocationTargetException e){
//                            throw new CommonException(209, "导入文件中第" + (j + 1) + "行第" + (k + 1) + "列数据格式错误");
//                        }catch (IllegalAccessException e){
//                            throw new CommonException(209, "导入文件中第" + (j + 1) + "行第" + (k + 1) + "列数据格式错误");
//                        }catch (Exception e) {
//                            e.printStackTrace();
//                            throw new CommonException(209, "导入文件中第" + (j + 1) + "行第" + (k + 1) + "列数据格式错误");
//                        }
//
//                    }
//                    if (allow){
//                        list.add(t);
//                    }
//                }
//            }
//        }
//        return list;
//    }
//
//    // 大数据量读取Excel
//    public static class LargeExcelFileReadUtil {
//
//        private LinkedHashMap<String, String> rowContents = new LinkedHashMap<String, String>();
//        private SheetHandler sheetHandler;
//        private List<LinkedHashMap<String, String>> sheetList = new ArrayList<LinkedHashMap<String, String>>();
//
//        private static StylesTable stylesTable;
//
//
//        public LinkedHashMap<String, String> getRowContents() {
//            return rowContents;
//        }
//
//        public void setRowContents(LinkedHashMap<String, String> rowContents) {
//            this.rowContents = rowContents;
//        }
//
//        public SheetHandler getSheetHandler() {
//            return sheetHandler;
//        }
//
//        public void setSheetHandler(SheetHandler sheetHandler) {
//            this.sheetHandler = sheetHandler;
//        }
//
//        public List<LinkedHashMap<String, String>> getSheetList() {
//            return sheetList;
//        }
//
//        public void setSheetList(List<LinkedHashMap<String, String>> sheetList) {
//            this.sheetList = sheetList;
//        }
//
//        public static StylesTable getStylesTable() {
//            return stylesTable;
//        }
//
//        public static void setStylesTable(StylesTable stylesTable) {
//            LargeExcelFileReadUtil.stylesTable = stylesTable;
//        }
//
//        /**
//         * 处理一个sheet
//         *
//         * @param is
//         * @throws Exception
//         */
//        public void processOneSheet(InputStream is) throws Exception {
//
//            OPCPackage pkg = OPCPackage.open(is);
//            XSSFReader r = new XSSFReader(pkg);
//            stylesTable = r.getStylesTable();
//            SharedStringsTable sst = r.getSharedStringsTable();
//
//            XMLReader parser = fetchSheetParser(sst);
//
//            // Seems to either be rId# or rSheet#
//            InputStream sheet2 = r.getSheet("rId1");
//            InputSource sheetSource = new InputSource(sheet2);
//            parser.parse(sheetSource);
//            this.rowContents = sheetHandler.getRowContents();
//            this.sheetList.add(sheetHandler.getRowContents());
//            sheet2.close();
//        }
//
//        /**
//         * 处理所有sheet
//         *
//         * @param is
//         * @throws Exception
//         */
//        public void processAllSheets(InputStream is) throws Exception {
//
//            OPCPackage pkg = OPCPackage.open(is);
//            XSSFReader r = new XSSFReader(pkg);
//            stylesTable = r.getStylesTable();
//            SharedStringsTable sst = r.getSharedStringsTable();
//
//            XMLReader parser = fetchSheetParser(sst);
//
//            Iterator<InputStream> sheets = r.getSheetsData();
//            InputStream sheet = null;
//            while (sheets.hasNext()) {
//                System.out.println("Processing new sheet:\n");
//                sheet = sheets.next();
//                InputSource sheetSource = new InputSource(sheet);
//                parser.parse(sheetSource);
//                this.sheetList.add(sheetHandler.getRowContents());
//            }
//            sheet.close();
//        }
//
//        /**
//         * 获取解析器
//         *
//         * @param sst
//         * @return
//         * @throws SAXException
//         */
//        public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
//            XMLReader parser =
//                    XMLReaderFactory.createXMLReader(
//                            "org.apache.xerces.parsers.SAXParser"
//                    );
//            SheetHandler handler = new SheetHandler(sst);
//            parser.setContentHandler(handler);
//            setSheetHandler(handler);
//            return parser;
//        }
//
//        /**
//         * 自定义解析处理器
//         * See org.xml.sax.helpers.DefaultHandler javadocs
//         */
//        private static class SheetHandler extends DefaultHandler {
//
//            private SharedStringsTable sst;
//            private String lastContents;
//            private boolean nextIsString;
//
//            private List<String> rowlist = new ArrayList<String>();
//            private LinkedHashMap<String, String> rowContents = new LinkedHashMap<String, String>();
//            private int curRow = 0;
//            private int curCol = 0;
//
//            //定义前一个元素和当前元素的位置，用来计算其中空的单元格数量，如A6和A8等
//            private String preRef = null, ref = null;
//            //定义该文档一行最大的单元格数，用来补全一行最后可能缺失的单元格
//            private String maxRef = null;
//
//            private CellDataType nextDataType = CellDataType.SSTINDEX;
//            private final DataFormatter formatter = new DataFormatter();
//            private short formatIndex;
//            private String formatString;
//
//            //用一个enum表示单元格可能的数据类型
//            enum CellDataType {
//                BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER, DATE, NULL
//            }
//
//            public List<String> getRowlist() {
//                return rowlist;
//            }
//
//            public void setRowlist(List<String> rowlist) {
//                this.rowlist = rowlist;
//            }
//
//            public LinkedHashMap<String, String> getRowContents() {
//                return rowContents;
//            }
//
//            public void setRowContents(LinkedHashMap<String, String> rowContents) {
//                this.rowContents = rowContents;
//            }
//
//            private SheetHandler(SharedStringsTable sst) {
//                this.sst = sst;
//            }
//
//            /**
//             * 解析一个element的开始时触发事件
//             */
//            public void startElement(String uri, String localName, String name,
//                                     Attributes attributes) throws SAXException {
//
//                // c => cell
//                if (name.equals("c")) {
//                    //前一个单元格的位置
//                    if (preRef == null) {
//                        preRef = attributes.getValue("r");
//                    } else {
//                        preRef = ref;
//                    }
//                    //当前单元格的位置
//                    ref = attributes.getValue("r");
//
//                    this.setNextDataType(attributes);
//
//                    // Figure out if the value is an index in the SST
//                    String cellType = attributes.getValue("t");
//                    if (cellType != null && cellType.equals("s")) {
//                        nextIsString = true;
//                    } else {
//                        nextIsString = false;
//                    }
//
//                }
//                // Clear contents cache
//                lastContents = "";
//            }
//
//            /**
//             * 根据element属性设置数据类型
//             *
//             * @param attributes
//             */
//            public void setNextDataType(Attributes attributes) {
//
//                nextDataType = CellDataType.NUMBER;
//                formatIndex = -1;
//                formatString = null;
//                String cellType = attributes.getValue("t");
//                String cellStyleStr = attributes.getValue("s");
//                if ("b".equals(cellType)) {
//                    nextDataType = CellDataType.BOOL;
//                } else if ("e".equals(cellType)) {
//                    nextDataType = CellDataType.ERROR;
//                } else if ("inlineStr".equals(cellType)) {
//                    nextDataType = CellDataType.INLINESTR;
//                } else if ("s".equals(cellType)) {
//                    nextDataType = CellDataType.SSTINDEX;
//                } else if ("str".equals(cellType)) {
//                    nextDataType = CellDataType.FORMULA;
//                }
//                if (cellStyleStr != null) {
//                    int styleIndex = Integer.parseInt(cellStyleStr);
//                    XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
//                    formatIndex = style.getDataFormat();
//                    formatString = style.getDataFormatString();
//                    if ("m/d/yy" == formatString) {
//                        nextDataType = CellDataType.DATE;
//                        //full format is "yyyy-MM-dd hh:mm:ss.SSS";
//                        formatString = "yyyy-MM-dd";
//                    }
//                    if (formatString == null) {
//                        nextDataType = CellDataType.NULL;
//                        formatString = BuiltinFormats.getBuiltinFormat(formatIndex);
//                    }
//                }
//            }
//
//            /**
//             * 解析一个element元素结束时触发事件
//             */
//            @Override
//            public void endElement(String uri, String localName, String name)
//                    throws SAXException {
//                // Process the last contents as required.
//                // Do now, as characters() may be called more than once
//                if (nextIsString) {
//                    int idx = Integer.parseInt(lastContents);
//                    lastContents = sst.getItemAt(idx).toString();
//                    nextIsString = false;
//                }
//
//                // v => contents of a cell
//                // Output after we've seen the string contents
//                if (name.equals("v")) {
//                    String value = this.getDataValue(lastContents.trim(), "");
//                    //补全单元格之间的空单元格
//                    if (!ref.equals(preRef)) {
//                        int len = countNullCell(ref, preRef);
//                        for (int i = 0; i < len; i++) {
//                            rowlist.add(curCol, "");
//                            curCol++;
//                        }
//                    }
//                    rowlist.add(curCol, value);
//                    rowContents.put(ref, value);
//                    rowContents.put(END_POSITION, ref);
//                    curCol++;
//                } else {
//                    //如果标签名称为 row，这说明已到行尾，调用 optRows() 方法
//                    if (name.equals("row")) {
//                        //默认第一行为表头，以该行单元格数目为最大数目
//                        if (curRow == 0) {
//                            maxRef = ref;
//                        }
//                        //补全一行尾部可能缺失的单元格
//                        if (maxRef != null) {
//                            int len = countNullCell(maxRef, ref);
//                            for (int i = 0; i <= len; i++) {
//                                rowlist.add(curCol, "");
//                                curCol++;
//                            }
//                        }
//
//                        curRow++;
//                        //一行的末尾重置一些数据
//                        rowlist.clear();
//                        curCol = 0;
//                        preRef = null;
//                        ref = null;
//                    }
//                }
//            }
//
//            /**
//             * 根据数据类型获取数据
//             *
//             * @param value
//             * @param thisStr
//             * @return
//             */
//            public String getDataValue(String value, String thisStr) {
//                switch (nextDataType) {
//                    //这几个的顺序不能随便交换，交换了很可能会导致数据错误
//                    case BOOL:
//                        char first = value.charAt(0);
//                        thisStr = first == '0' ? "FALSE" : "TRUE";
//                        break;
//                    case ERROR:
//                        thisStr = "\"ERROR:" + value.toString() + '"';
//                        break;
//                    case FORMULA:
//                        thisStr = '"' + value.toString() + '"';
//                        break;
//                    case INLINESTR:
//                        XSSFRichTextString rtsi = new XSSFRichTextString(value.toString());
//                        thisStr = rtsi.toString();
//                        rtsi = null;
//                        break;
//                    case SSTINDEX:
//                        String sstIndex = value.toString();
//                        thisStr = value.toString();
//                        break;
//                    case NUMBER:
//                        if (formatString != null) {
//                            thisStr = formatter.formatRawCellContents(Double.parseDouble(value), formatIndex, formatString).trim();
//                        } else {
//                            thisStr = value;
//                        }
//                        thisStr = thisStr.replace("_", "").trim();
//                        break;
//                    case DATE:
//                        try {
//                            thisStr = formatter.formatRawCellContents(Double.parseDouble(value), formatIndex, formatString);
//                        } catch (NumberFormatException ex) {
//                            thisStr = value.toString();
//                        }
//                        thisStr = thisStr.replace(" ", "");
//                        break;
//                    default:
//                        thisStr = "";
//                        break;
//                }
//                return thisStr;
//            }
//
//            /**
//             * 获取element的文本数据
//             */
//            public void characters(char[] ch, int start, int length)
//                    throws SAXException {
//                lastContents += new String(ch, start, length);
//            }
//
//            /**
//             * 计算两个单元格之间的单元格数目(同一行)
//             *
//             * @param ref
//             * @param preRef
//             * @return
//             */
//            public int countNullCell(String ref, String preRef) {
//                //excel2007最大行数是1048576，最大列数是16384，最后一列列名是XFD
//                String xfd = ref.replaceAll("\\d+", "");
//                String xfd_1 = preRef.replaceAll("\\d+", "");
//
//                xfd = fillChar(xfd, 3, '@', true);
//                xfd_1 = fillChar(xfd_1, 3, '@', true);
//
//                char[] letter = xfd.toCharArray();
//                char[] letter_1 = xfd_1.toCharArray();
//                int res = (letter[0] - letter_1[0]) * 26 * 26 + (letter[1] - letter_1[1]) * 26 + (letter[2] - letter_1[2]);
//                return res - 1;
//            }
//
//            /**
//             * 字符串的填充
//             *
//             * @param str
//             * @param len
//             * @param let
//             * @param isPre
//             * @return
//             */
//            String fillChar(String str, int len, char let, boolean isPre) {
//                int len_1 = str.length();
//                if (len_1 < len) {
//                    if (isPre) {
//                        for (int i = 0; i < (len - len_1); i++) {
//                            str = let + str;
//                        }
//                    } else {
//                        for (int i = 0; i < (len - len_1); i++) {
//                            str = str + let;
//                        }
//                    }
//                }
//                return str;
//            }
//        }
//    }
//
//    public static <T> T string2Obj(Class<T> clz, String str) {
//        String type = "";
//        try {
//            if (null == str)
//                return null;
//            Object r = null;
//            if (clz.equals(Integer.class)) {
//                type = "Integer";
//                r = Integer.parseInt(str);
//            } else if (clz.equals(Double.class)) {
//                type = "Double";
//                r = Double.parseDouble(str);
//            } else if (clz.equals(Float.class)) {
//                type = "Float";
//                r = Float.valueOf(str);
//            } else if (clz.equals(Boolean.class)) {
//                type = "Boolean";
//                r = Boolean.valueOf(str);
//            } else if (clz.equals(Date.class)) {
//                type = "Date";
//                SimpleDateFormat format = null;
//                if (str.indexOf("/") == -1) {
//                    format = new SimpleDateFormat("yyyy-MM-dd");
//                } else {
//                    format = new SimpleDateFormat("yyyy/MM/dd");
//                }
//                r = format.parse(str);
//            }
//            return (T) r;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new CommonException(1001, "字符串转" + type + "对象失败");
//        }
//    }
//
//    public static String toExcelColumnPosition(int len) {
//        if (len < 1) {
//            return null;
//        }
//        len -= 1;
//        StringBuffer sb = new StringBuffer();
//        int remain = len % 26;
//        int m = len / 26;
//        int a = 'A';
//        if (m > 0) {
//            sb.append((char) (a + m));
//        }
//        sb.append((char) (a + remain));
//        return sb.toString();
//    }
//}
//
