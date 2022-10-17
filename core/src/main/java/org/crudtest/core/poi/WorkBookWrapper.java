package org.crudtest.core.poi;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WorkBookWrapper implements Closeable {

    private Workbook book;

    private List<Sheet> sheets = new ArrayList<>();

    private int sheetIndex = 0;

    public WorkBookWrapper() {
//        this.book = new SXSSFWorkbook();
        this.book = new XSSFWorkbook();
    }

    public void createSheet(String sheetName) {
        Sheet sheet = book.createSheet(sheetName);
//        ((XSSFSheet) sheet).trackAllColumnsForAutoSizing();
        this.sheets.add(sheet);
        sheetIndex++;
    }

    public CellWrapper moveCell(int rowIndex, int colIndex) {
        Sheet sheet = getSheet();
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }

        Cell cell = row.createCell(colIndex);
        return new CellWrapper(this.book, cell);
    }

    public void autoSizeColumn(int i) {
        getSheet().autoSizeColumn(i);
    }

    public void cellUnion(int firstRow,
            int lastRow,
            int firstCol,
            int lastCol) {
        getSheet().addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }

    private Sheet getSheet() {
        if (sheetIndex == 0) {
            throw new IllegalStateException("Non create Sheet");
        }
        return this.sheets.get(sheetIndex - 1);
    }

    public void write(BufferedOutputStream fos) throws IOException {
        this.book.write(fos);
    }

    @Override
    public void close() throws IOException {
        if (this.book != null) {
            this.book.close();
        }
    }
}
