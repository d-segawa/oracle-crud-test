package org.crudtest.listener;

import java.awt.Choice;
import java.nio.file.Path;
import java.util.List;

import org.crudtest.service.CrudTestService;
import org.crudtest.service.HtmlPrintService;

public class CoreHelper {

    private static final CrudTestService crudTestService = new CrudTestService();

    private static final HtmlPrintService htmlPrintService = new HtmlPrintService();

    public static void renewChoice(Choice c) {
        if (crudTestService.existsMngTable()) {
            List<String> tableList = crudTestService.selectCreatedTriggerTableName();
            if (tableList != null) {
                c.removeAll();
                tableList.forEach(t -> c.add(t));

            }
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

    public static void printHtml(List<String> targetTableName, Path filePath) {
        htmlPrintService.printMultiTable(targetTableName, filePath);
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
