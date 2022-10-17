package org.crudtest.core.poi;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class CellWrapper {

    private Workbook book;

    private Cell cell;

    public CellWrapper(Workbook book, Cell cell) {
        this.book = book;
        this.cell = cell;
    }

    public void setHeaderStyle() {
        this.cell.setCellStyle(headerStyle());
    }

    public void setTextStyle() {
        this.cell.setCellStyle(textStyle());
    }

    public void setTextStyleNonBorder() {
        this.cell.setCellStyle(textStyleNonBorder());
    }

    public void redText() {
        this.cell.getCellStyle().setFont(redFont(this.book));
    }

    public void backgroundYellow() {
        this.cell.getCellStyle().setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex());
        this.cell.getCellStyle().setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }

    public void backgroundGreen() {
        this.cell.getCellStyle().setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_GREEN.getIndex());
        this.cell.getCellStyle().setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }

    public void setCellValue(String value) {
        this.cell.setCellValue(value);
    }

    private CellStyle headerStyle() {
        CellStyle header = book.createCellStyle();
        header.setBorderBottom(BorderStyle.THIN);
        setBorder(header, BorderStyle.THIN);
        header.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        header.setVerticalAlignment(VerticalAlignment.TOP);
        header.setFont(font(book));
        return header;
    }

    private CellStyle textStyle() {
        CellStyle text = book.createCellStyle();
        setBorder(text, BorderStyle.THIN);
        text.setVerticalAlignment(VerticalAlignment.TOP);
        text.setWrapText(true);
        text.setFont(font(book));
        return text;
    }

    private CellStyle textStyleNonBorder() {
        CellStyle text = book.createCellStyle();
        text.setVerticalAlignment(VerticalAlignment.TOP);
        text.setWrapText(true);
        text.setFont(font(book));
        return text;
    }

    private void setBorder(CellStyle style, BorderStyle border) {
        style.setBorderBottom(border);
        style.setBorderTop(border);
        style.setBorderLeft(border);
        style.setBorderRight(border);
    }

    private Font font(Workbook book) {
        Font font = book.createFont();
        font.setFontName("ＭＳ ゴシック");
        font.setFontHeightInPoints((short) 9);
        return font;
    }

    private Font redFont(Workbook book) {
        Font font = book.createFont();
        font.setFontName("ＭＳ ゴシック");
        font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
        font.setFontHeightInPoints((short) 9);
        return font;
    }

}
