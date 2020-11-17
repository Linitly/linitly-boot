package org.linitly.boot.base.utils.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.linitly.boot.base.annotation.ExcelExport;
import org.linitly.boot.base.annotation.ExcelProperty;
import org.linitly.boot.base.enums.ResultEnum;
import org.linitly.boot.base.exception.CommonException;
import org.linitly.boot.base.utils.NumberUtil;
import org.linitly.boot.base.utils.StringUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author: linxiunan
 * @date: 2020/5/9 10:37
 * @descrption: excel导出工具类，仅适用于首行表头，其余为表内容的样式
 */
@Slf4j
public class ExcelExportUtil {

    public static <T> void templateExport(Class<T> targetClass, HttpServletResponse response) {
        excelExport(targetClass, null, response);
    }

    public static <T> void excelExport(Class<T> targetClass, List<T> list, HttpServletResponse response) {
        ExcelExport excelExport = getExcelExport(targetClass);
        Workbook workbook = createWorkbook(excelExport);
        setSheet(excelExport, workbook, list, targetClass);
        String exportName = StringUtils.isBlank(excelExport.exportName()) ? UUID.randomUUID().toString() : excelExport.exportName();
        String downloadName = null;
        try {
            downloadName = URLEncoder.encode(exportName + getSuffix(excelExport), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("转换UTF-8格式错误");
        }
        export(downloadName, response, workbook);
    }

    private static void export(String downloadName, HttpServletResponse response, Workbook workbook) {
        response.reset();
        response.setContentType("application/ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + downloadName);
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            response.setContentType("application/json");
            throw new CommonException(ResultEnum.FILE_DOWNLOAD_ERROR);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static <T> void setSheet(ExcelExport excelExport, Workbook workbook, List<T> list, Class<T> targetClass) {
        String sheetName = excelExport.sheetName();
        Sheet sheet = StringUtils.isBlank(sheetName) ? workbook.createSheet() : workbook.createSheet(sheetName);
        setRows(workbook, sheet, excelExport, list, targetClass);
    }

    private static <T> void setRows(Workbook workbook, Sheet sheet, ExcelExport excelExport, List<T> list, Class<T> targetClass) {
        int headHeight = excelExport.headHeight();
        // 设置宽度
        sheet.setColumnWidth(0, excelExport.width());
        // 设置表头高度
        Row headRow = sheet.createRow(0);
        headRow.setHeight((short) headHeight);
        // 填充表头内容
        setHeadRow(headRow, workbook, excelExport);
        // 填充表格内容
        setCellRow(workbook, sheet, excelExport, list, targetClass);
    }

    private static <T> void setCellRow(Workbook workbook, Sheet sheet, ExcelExport excelExport, List<T> list, Class<T> targetClass) {
        Row row = null;
        if (list != null && !list.isEmpty()) {
            List<Field> fields = ExcelCommonUtil.getAnnotationFields(targetClass, 2);
            if (fields.size() != excelExport.headers().length) {
                throw new RuntimeException("head number is not equals the field number who has export annotation");
            }
            for (int i = 0; i < list.size(); i++) {
                row = sheet.createRow(i + 1);
                row.setHeight((short) excelExport.cellHeight());
                setCellValue(row, workbook, excelExport, list.get(i), fields);
            }
        }
    }

    private static <T> void setCellValue(Row row, Workbook workbook, ExcelExport excelExport, T t, List<Field> fields) {
        CellStyle cellStyle = getCellStyle(workbook, excelExport, false);
        Cell cell = null;
        Object value = null;
        ExcelProperty excelProperty = null;
        for (int i = 0; i < excelExport.headers().length; i++) {
            try {
                cell = row.createCell(i);
                cell.setCellStyle(cellStyle);
                value = getTarget(t, fields.get(i).getName());
                excelProperty = ExcelCommonUtil.getExcelPropertiesAnon(fields.get(i));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                throw new RuntimeException("no such get method of field " + fields.get(i).getName());
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
                continue;
            }
            if (value instanceof Double) {
                setCellDoubleValue(cell, (Double) value, excelProperty.exportDecimal());
            } else if (value instanceof Date) {
                setCellStringDateValue(cell, (Date) value, excelProperty.exportFormat().getFormat());
            } else if (value instanceof String) {
                setCellStringValue(cell, (String) value);
            } else if (value instanceof Boolean) {
                if (StringUtils.isNotBlank(excelProperty.exportEscape())) {
                    value = ExcelCommonUtil.getEscapeValue(value, excelProperty, 2);
                }
                setCellStringValue(cell, value.toString());
            } else if (value instanceof BigDecimal) {
                setBigDecimalValue(cell, (BigDecimal) value, excelProperty.exportDecimal());
            } else if (value instanceof Integer) {
                if (StringUtils.isNotBlank(excelProperty.exportEscape())) {
                    value = ExcelCommonUtil.getEscapeValue(value, excelProperty, 2);
                }
                setCellStringValue(cell, value.toString());
            } else if (value instanceof Long) {
                setCellStringValue(cell, value.toString());
            } else {
                cell.setBlank();
            }
        }
    }

    private static void setHeadRow(Row row, Workbook workbook, ExcelExport excelExport) {
        CellStyle cellStyle = getCellStyle(workbook, excelExport, true);
        Cell cell = null;
        for (int i = 0; i < excelExport.headers().length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            if (StringUtils.isBlank(excelExport.headers()[i])) {
                cell.setBlank();
            } else {
                cell.setCellValue(excelExport.headers()[i]);
            }
        }
    }

    private static CellStyle getCellStyle(Workbook workbook, ExcelExport excelExport, boolean isHead) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = getFont(workbook, excelExport, isHead);
        if (isHead) {
            cellStyle.setFillBackgroundColor(excelExport.headBackGroundColor());
            cellStyle.setAlignment(excelExport.headAlignment());
            cellStyle.setVerticalAlignment(excelExport.headVAlignment());
        } else {
            cellStyle.setFillBackgroundColor(excelExport.cellBackGroundColor());
            cellStyle.setAlignment(excelExport.cellAlignment());
            cellStyle.setVerticalAlignment(excelExport.cellVAlignment());
        }
        cellStyle.setFont(font);
        return cellStyle;
    }

    private static Font getFont(Workbook workbook, ExcelExport excelExport, boolean isHead) {
        Font font = workbook.createFont();
        if (isHead) {
            font.setFontName(excelExport.headFontName());
            font.setColor(excelExport.headFontColor());
            font.setBold(excelExport.headBold());
            font.setFontHeightInPoints(excelExport.headFontSize());
        } else {
            font.setFontName(excelExport.cellFontName());
            font.setColor(excelExport.cellFontColor());
            font.setBold(excelExport.cellBold());
            font.setFontHeightInPoints(excelExport.cellFontSize());
        }
        return font;
    }

    private static Workbook createWorkbook(ExcelExport excelExport) {
        Workbook workbook = null;
        switch (excelExport.excelType()) {
            case XLS:
                workbook = new HSSFWorkbook();
                break;
            case XLSX:
                workbook = new XSSFWorkbook();
                break;
        }
        return workbook;
    }

    private static String getSuffix(ExcelExport excelExport) {
        String suffix = null;
        switch (excelExport.excelType()) {
            case XLS:
                suffix = ".xls";
                break;
            case XLSX:
                suffix = ".xlsx";
                break;
        }
        return suffix;
    }

    private static <T> ExcelExport getExcelExport(Class<T> targetClass) {
        if (targetClass.isAnnotationPresent(ExcelExport.class)) {
            return targetClass.getAnnotation(ExcelExport.class);
        } else {
            throw new RuntimeException("can not found ExcelImport annotation at " + targetClass.getName() + " class");
        }
    }

    private static Object getTarget(Object obj, String code) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return obj.getClass().getMethod("get" + StringUtil.toUpperCaseFirstOne(code)).invoke(obj);
    }

    private static void setCellDoubleValue(Cell cell, Double doubleValue, int decimal) {
        if (doubleValue == null) {
            cell.setBlank();
        } else {
            if (decimal > 0) {
                cell.setCellValue(NumberUtil.doubleFormat2BigDecimal(decimal, doubleValue).doubleValue());
            } else {
                cell.setCellValue(doubleValue);
            }
        }
    }

    private static void setCellStringDateValue(Cell cell, Date date, String formatStr) {
        if (date == null) {
            cell.setBlank();
        } else {
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            String signDate = format.format(date);
            cell.setCellValue(signDate);
        }
    }

    private static void setCellStringValue(Cell cell, String str) {
        if (str == null) {
            cell.setBlank();
        } else {
            cell.setCellValue(str);
        }
    }

    private static void setBigDecimalValue(Cell cell, BigDecimal bigDecimalValue, int decimal) {
        if (bigDecimalValue == null) {
            cell.setBlank();
        } else {
            if (decimal > 0) {
                cell.setCellValue(bigDecimalValue.setScale(decimal, BigDecimal.ROUND_HALF_UP).doubleValue());
            } else {
                cell.setCellValue(bigDecimalValue.doubleValue());
            }
        }
    }
}
