package org.crudtest.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.List;

import org.crudtest.exception.JdbcException;
import org.crudtest.log.AppLogger;
import org.crudtest.poi.CellWrapper;
import org.crudtest.poi.WorkBookWrapper;
import org.crudtest.properties.ApplicationProperties;
import org.crudtest.repository.OracleRepository;
import org.crudtest.repository.entity.LogTable;
import org.crudtest.service.bean.LogRecoredBean;
import org.crudtest.service.bean.LogRecoredBean.Data;
import org.crudtest.service.logic.DataConverter;

public class ExcelPrintService {

    private static AppLogger log = AppLogger.getLogger(ExcelPrintService.class);

    private final OracleRepository repo;

    private final DataConverter converter;

    public static final String LOGS_TABLE_NAME = ApplicationProperties.LOG_TABLE_NAME.getValue();

    private int startRowIndex = 1;
    private int startColIndex = 0;

    public ExcelPrintService() {
        this.repo = new OracleRepository();
        this.converter = new DataConverter();
    }

    public void printMultiTable(List<String> tableList, Path filePath) {
        try {

            try (WorkBookWrapper book = new WorkBookWrapper()) {

                createSheetData(book, tableList);

                try (BufferedOutputStream fso = new BufferedOutputStream(new FileOutputStream(filePath.toFile()))) {
                    book.write(fso);
                }
            }

        } catch (Exception e) {
            log.error("Error occured print excel.", e);
        }
    }

    private void createSheetData(WorkBookWrapper book, List<String> tableList) throws JdbcException {

        for (String targetTableName : tableList) {

            List<LogTable> logsTableList = repo.selectLog(LOGS_TABLE_NAME, targetTableName);
            List<LogRecoredBean> recoredList = converter.converteList(logsTableList);

            book.createSheet(targetTableName);

            if (recoredList == null || recoredList.size() == 0) {
                continue;
            }

            CellWrapper cell = book.moveCell(0, 0);
            cell.setTextStyleNonBorder();
            cell.setCellValue(targetTableName);

            createHeader(book, recoredList);
            createSecondeHeader(book, recoredList);
            createColmunData(book, recoredList);
            createBodyData(book, recoredList);

        }

    }

    private void createHeader(WorkBookWrapper book, List<LogRecoredBean> recoredList) {

        book.moveCell(startRowIndex, startColIndex).setHeaderStyle();
        book.moveCell(startRowIndex + 1, startColIndex).setHeaderStyle();

        boolean isSkip = false;
        int rowIndex = startRowIndex;
        int colIndex = startColIndex + 1;

        for (LogRecoredBean bean : recoredList) {

            if (isSkip) {
                isSkip = false;
                continue;
            }

            CellWrapper cell = book.moveCell(rowIndex, colIndex);
            cell.setHeaderStyle();
            cell.setCellValue(bean.getCrudType());

            colIndex++;
            if ("UPDATE".equals(bean.getCrudType())) {
                cell = book.moveCell(rowIndex, colIndex);
                cell.setHeaderStyle();
                book.cellUnion(rowIndex, rowIndex, colIndex - 1, colIndex);

                isSkip = true;
                colIndex++;
            }

        }
    }

    private void createSecondeHeader(WorkBookWrapper book, List<LogRecoredBean> recoredList) {

        int rowIndex = startRowIndex + 1;
        int colIndex = startColIndex + 1;

        for (LogRecoredBean bean : recoredList) {

            CellWrapper cell = book.moveCell(rowIndex, colIndex);
            cell.setHeaderStyle();
            cell.setCellValue(bean.getHistoryType());
            colIndex++;
        }
    }

    private void createColmunData(WorkBookWrapper book, List<LogRecoredBean> recoredList) {

        LogRecoredBean firstBean = recoredList.get(0);
        int rowIndex = startRowIndex + 2;
        int colIndex = startColIndex;

        for (Data data : firstBean.getDataList()) {
            CellWrapper cell = book.moveCell(rowIndex, colIndex);
            cell.setHeaderStyle();
            cell.setCellValue(data.getKey());
            rowIndex++;
        }

        book.autoSizeColumn(colIndex);
    }

    private void createBodyData(WorkBookWrapper book, List<LogRecoredBean> recoredList) {

        int colIndex = startColIndex + 1;

        for (int n = 0; n < recoredList.size(); n++) {
            LogRecoredBean bean = recoredList.get(n);

            int rowIndex = startRowIndex + 2;

            for (int i = 0; i < bean.getDataList().size(); i++) {
                Data data = bean.getDataList().get(i);

                CellWrapper cell = book.moveCell(rowIndex, colIndex);
                cell.setTextStyle();
                cell.setCellValue(data.getValue());
                if ("<NULL>".equals(data.getValue())) {
                    cell.redText();
                }

                switch (bean.getCrudType()) {
                case "INSERT":
                case "DELETE":
                    cell.backgroundYellow();
                    break;

                case "UPDATE":
                    if ("NEW".equals(bean.getHistoryType())) {
                        if (!recoredList.get(n - 1).getDataList().get(i).getValue().equals(data.getValue())) {
                            cell.backgroundYellow();
                            break;
                        }
                    }
                }

                rowIndex++;
            }

            book.autoSizeColumn(colIndex);
            colIndex++;
        }

    }
}
