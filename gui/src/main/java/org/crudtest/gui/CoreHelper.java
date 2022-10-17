package org.crudtest.gui;

import java.awt.Choice;
import java.nio.file.Path;
import java.util.List;

import org.crudtest.core.service.CrudTestService;
import org.crudtest.core.service.ExcelPrintService;
import org.crudtest.core.service.HtmlPrintService;

public class CoreHelper {

    private static final CrudTestService crudTestService = new CrudTestService();

    private static final HtmlPrintService htmlPrintService = new HtmlPrintService();

    private static final ExcelPrintService excelPrintService = new ExcelPrintService();

    public static void renewChoice(Choice c) {
        c.removeAll();
        if (crudTestService.existsMngTable()) {
            List<String> tableList = crudTestService.selectCreatedTriggerTableName();
            if (tableList != null) {
                tableList.forEach(t -> c.add(t));

            }
        }
    }

    public static int countData(List<String> tableList) {
        return htmlPrintService.countData(tableList);
    }

    public static void deleteData(List<String> tableList) {
        for (String tableName : tableList) {
            crudTestService.deleteLogsTable(tableName);
        }
    }

    public static void truncateLogTable() {
        if (crudTestService.existsLogTable()) {
            crudTestService.truncateLogTable();
        }
    }

    public static final Result createTrigger(String text) {
        String message = crudTestService.create(text);
        return new Result(message.isEmpty(), message);
    }

    public static final Result allDropTrigger() {
        if (crudTestService.existsMngTable()) {
            String message = crudTestService.dropAllTrigger();
            return new Result(message.isEmpty(), message);
        }
        return new Result(true, "");
    }

    public static void dropObject() {
        crudTestService.dropObject();
    }

    public static void printHtml(List<String> targetTableList, Path filePath) {
        htmlPrintService.printMultiTable(targetTableList, filePath);
    }

    public static void printExcel(List<String> targetTableList, Path filePath) {
        excelPrintService.printMultiTable(targetTableList, filePath);
    }

    public static class Result {
        public boolean result;
        public String errorMessage;

        public Result(boolean result, String errorMessage) {
            this.result = result;
            this.errorMessage = errorMessage;
        }
    }

}
