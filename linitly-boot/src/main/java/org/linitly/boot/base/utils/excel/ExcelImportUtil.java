package org.linitly.boot.base.utils.excel;

import org.linitly.boot.base.annotation.ExcelImport;
import org.linitly.boot.base.annotation.ExcelProperty;
import org.linitly.boot.base.constant.global.GlobalConstant;
import org.linitly.boot.base.enums.DateFormat;
import org.linitly.boot.base.enums.ResultEnum;
import org.linitly.boot.base.exception.ExcelException;
import org.linitly.boot.base.utils.IDateUtil;
import org.linitly.boot.base.utils.NumberUtil;
import org.linitly.boot.base.utils.db.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: linxiunan
 * @date: 2020/5/8 9:20
 * @descrption: excel工具类(数据量较小excel,万行以下建议使用)
 */
@Slf4j
public class ExcelImportUtil {

    public static <T> List<T> excelImport(MultipartFile file, Class<T> targetClass) throws IOException {
        if (file == null) {
            throw new RuntimeException("file can not be null");
        }
        ExcelImport excelImport = getExcelReadEntityAnno(targetClass);
        // 获取workbook
        Workbook workbook = getWorkbook(file.getInputStream(), file.getOriginalFilename());
        // 获取sheets
        List<Sheet> sheets = getSheets(workbook, excelImport);
        // 读取sheet返回结果
        return readSheets(sheets, targetClass, excelImport);
    }

    private static ExcelImport getExcelReadEntityAnno(Class<?> targetClass) {
        if (targetClass.isAnnotationPresent(ExcelImport.class)) {
            return targetClass.getAnnotation(ExcelImport.class);
        } else {
            throw new RuntimeException("can not found ExcelImport annotation at " + targetClass.getName() + " class");
        }
    }

    private static Workbook getWorkbook(InputStream inputStream, String fileName) throws IOException {
        if (fileName.endsWith(".xls")) {
            return new HSSFWorkbook(inputStream);
        } else if (fileName.endsWith(".xlsx")) {
            return new XSSFWorkbook(inputStream);
        } else {
            throw new ExcelException(ResultEnum.EXCEL_UPLOAD_FORMAT_ERROR);
        }
    }

    private static List<Sheet> getSheets(Workbook workbook, ExcelImport excelImport) {
        List<Sheet> sheets = new ArrayList<>();
        int sheetNumber = workbook.getNumberOfSheets();
        if (sheetNumber < 1) {
            throw new ExcelException(ResultEnum.EXCEL_SHEET_ANALYSE_ERROR);
        }
        Sheet sheet = null;
        switch (excelImport.sheetReadType()) {
            case FIRST:
                sheet = workbook.getSheetAt(0);
                sheets.add(sheet);
                break;
            case ALL:
                for (int i = 0; i < sheetNumber; i++) {
                    sheet = workbook.getSheetAt(i);
                    sheets.add(sheet);
                }
                break;
            case CUSTOMIZE:
                for (int i = excelImport.startReadSheet() > sheetNumber ? sheetNumber : excelImport.startReadSheet(); i < (excelImport.endReadSheet() > sheetNumber ? sheetNumber : excelImport.endReadSheet()); i++) {
                    sheet = workbook.getSheetAt(i);
                    sheets.add(sheet);
                }
                break;
            default:
                break;
        }
        return sheets;
    }

    private static <T> List<T> readSheets(List<Sheet> sheets, Class<T> targetClass, ExcelImport excelImport) {
        Sheet sheet = null;
        List<T> resultList = new ArrayList<>();
        for (int i = 0; i < sheets.size(); i++) {
            sheet = sheets.get(i);
            if (sheet != null) {
                List<T> list = readRows(sheet, targetClass, excelImport);
                resultList.addAll(list);
            }
        }
        if (resultList.isEmpty()) {
            throw new ExcelException(ResultEnum.EXCEL_READ_EMPTY_ERROR);
        }
        return resultList;
    }

    private static <T> List<T> readRows(Sheet sheet, Class<T> targetClass, ExcelImport excelImport) {
        List<T> list = new ArrayList<>();
        int totalRowNumber = sheet.getPhysicalNumberOfRows();
        if (totalRowNumber < excelImport.startReadRow() + 1) {
//            throw new RuntimeException("row count was min to start read row number");
            throw new ExcelException(ResultEnum.EXCEL_ROW_NUMBER_ERROR);
        }
        if (sheet.getRow(excelImport.startReadRow()) == null) {
//            throw new RuntimeException("start read row was empty");
            throw new ExcelException(ResultEnum.EXCEL_ROW_NUMBER_ERROR);
        }
        if (totalRowNumber > excelImport.maxReadRow()) {
            throw new ExcelException(ResultEnum.EXCEL_UPPER_LIMIT_ERROR);
        }
        Row row = null;
        for (int i = excelImport.startReadRow(); i < totalRowNumber; i++) {
            row = sheet.getRow(i);
            if (row == null) {
                list.add(null);
                continue;
            }
            T t = null;
            try {
                t = targetClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("target class has not no args constructor");
            }
            list.add(readCells(row, t, targetClass, excelImport.startReadCell()));
        }
        return list;
    }

    private static <T> T readCells(Row row, T t, Class<T> targetClass, int startReadCell) {
        int totalCells = row.getPhysicalNumberOfCells();
        if (totalCells < startReadCell + 1) {
            throw new ExcelException(GlobalConstant.GENERAL_ERROR, "第" + (row.getRowNum() + 1) + "行有效列不足，请检查");
        }
        List<Field> fieldList = ExcelCommonUtil.getAnnotationFields(t.getClass(), 1);
        Cell cell = null;
        for (int i = startReadCell; i < totalCells; i++) {
            try {
                cell = row.getCell(i);
                Method method = ClassUtil.getSetMethod(fieldList.get(i), targetClass);
                ExcelProperty excelProperty = ExcelCommonUtil.getExcelPropertiesAnon(fieldList.get(i));
                if (excelProperty != null && excelProperty.cell() >= 0 && excelProperty.cell() != i) {
                    cell = row.getCell(excelProperty.cell());
                }
                if (cell == null) {
                    if (!excelProperty.blank()) throw new ExcelException(GlobalConstant.GENERAL_ERROR, "第" + (row.getRowNum() + 1) + "行，第" + (i + 1) + "列为空，请检查");
                }
                Class classes = null;
                switch (cell.getCellType()) {
                    case STRING:
                        Object value = cell.getStringCellValue();
                        if (excelProperty.importFormat() != DateFormat.NONE) {
                            value = IDateUtil.convertLDTToDate(IDateUtil.parseToLDT(String.valueOf(value), excelProperty.importFormat()));
                        }
                        value = ExcelCommonUtil.getEscapeValue(value, excelProperty, 1);
                        classes = getFieldClassAndAssertNotNull(fieldList.get(i), row.getRowNum() + 1, i + 1);
                        if (classes == Double.class) {
                            if (excelProperty.importDecimal() > 0) {
                                method.invoke(t, Double.valueOf(NumberUtil.stringFormat2String(excelProperty.importDecimal(), value.toString())));
                            } else {
                                method.invoke(t, Double.valueOf(value.toString()));
                            }
                        } else if (classes == Float.class) {
                            if (excelProperty.importDecimal() > 0) {
                                method.invoke(t, Float.valueOf(NumberUtil.stringFormat2String(excelProperty.importDecimal(), value.toString())));
                            } else {
                                method.invoke(t, Float.valueOf(value.toString()));
                            }
                        } else if (classes == BigDecimal.class) {
                            if (excelProperty.importDecimal() > 0) {
                                method.invoke(t, NumberUtil.stringFormat2BigDecimal(excelProperty.importDecimal(), value.toString()));
                            } else {
                                method.invoke(t, new BigDecimal(value.toString()));
                            }
                        } else if (classes == Integer.class) {
                            method.invoke(t, new BigDecimal(value.toString()).intValue());
                        } else {
                            method.invoke(t, value);
                        }
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            method.invoke(t, cell.getDateCellValue());
                        } else {
                            classes = getFieldClassAndAssertNotNull(fieldList.get(i), row.getRowNum() + 1, i + 1);
                            if (classes == Double.class) {
                                if (excelProperty.importDecimal() > 0) {
                                    method.invoke(t, NumberUtil.doubleFormat2Double(excelProperty.importDecimal(), cell.getNumericCellValue()));
                                } else {
                                    method.invoke(t, cell.getNumericCellValue());
                                }
                            } else if (classes == String.class) {
                                method.invoke(t, String.valueOf(cell.getNumericCellValue()));
                            } else if (classes == Float.class) {
                                if (excelProperty.importDecimal() > 0) {
                                    method.invoke(t, NumberUtil.doubleFormat2BigDecimal(excelProperty.importDecimal(), cell.getNumericCellValue()).floatValue());
                                } else {
                                    method.invoke(t, new BigDecimal(cell.getNumericCellValue()).floatValue());
                                }
                            } else if (classes == BigDecimal.class) {
                                if (excelProperty.importDecimal() > 0) {
                                    method.invoke(t, NumberUtil.doubleFormat2BigDecimal(excelProperty.importDecimal(), cell.getNumericCellValue()));
                                } else {
                                    method.invoke(t, new BigDecimal(cell.getNumericCellValue()));
                                }
                            } else if (classes == Integer.class) {
                                method.invoke(t, new BigDecimal(cell.getNumericCellValue()).intValue());
                            } else {
                                throw new ExcelException(GlobalConstant.GENERAL_ERROR, "第" + (row.getRowNum() + 1) + "行，第" + (i + 1) + "列数据类型不匹配");
                            }
                        }
                        break;
                    case BOOLEAN:
                        method.invoke(t, cell.getBooleanCellValue());
                        break;
                    case BLANK:
                        if (!excelProperty.blank()) throw new ExcelException(GlobalConstant.GENERAL_ERROR, "第" + (row.getRowNum() + 1) + "行，第" + (i + 1) + "列为空，请检查");
                        break;
                    default:
                        throw new ExcelException(GlobalConstant.GENERAL_ERROR, "第" + (row.getRowNum() + 1) + "行，第" + (i + 1) + "列格式不符合规范，请检查");
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                log.error("没有获取对应的set方法，属性名为：" + fieldList.get(i).getName());
                throw new RuntimeException("can not get set method of param " + fieldList.get(i).getName());
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    private static Class<?> getFieldClassAndAssertNotNull(Field field, int rowNumber, int cellNumber) {
        Class classes = ClassUtil.getFieldType(field);
        if (classes == null) {
            log.warn("第" + rowNumber + "行，第" + cellNumber + "列数字类型cell获取对应的field类类型失败");
            throw new ExcelException(GlobalConstant.GENERAL_ERROR, "第" + rowNumber + "行，第" + cellNumber + "列数据类型不匹配");
        }
        return classes;
    }
}
