package org.crudtest.service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.crudtest.log.AppLogger;
import org.crudtest.properties.ApplicationProperties;
import org.crudtest.repository.OracleRepository;
import org.crudtest.repository.entity.LogTable;
import org.crudtest.service.bean.LogRecoredBean;
import org.crudtest.service.logic.DataConverter;
import org.crudtest.util.IOUtil;

public class HtmlPrintService {

    private static AppLogger log = AppLogger.getLogger(HtmlPrintService.class);

    private final OracleRepository repo;

    private final DataConverter converter;

    public static final String LOGS_TABLE_NAME = ApplicationProperties.LOG_TABLE_NAME.getValue();

    public HtmlPrintService() {
        this.repo = new OracleRepository();
        this.converter = new DataConverter();
    }

    public void print(String targetTableName, Path filePath) {
        try {
            List<LogTable> logsTableList = repo.selectLog(LOGS_TABLE_NAME, targetTableName);
            List<LogRecoredBean> recoredList = converter.converteList(logsTableList);

            Path path = IOUtil.getTemplatePath("template.html");
            List<String> lines = IOUtil.readAllLine(path);

            String thead1 = createTHeadFirst(recoredList);
            String thead2 = createTHeadSecond(recoredList);
            String tbody = createTBody(recoredList);

            // replace
            lines.replaceAll(line -> {
                return line.replace("${TABLE_NAME}", targetTableName).replace("${THEAD1}", thead1)
                        .replace("${THEAD2}", thead2)
                        .replace("${TBODY}", tbody);
            });

            IOUtil.writeFile(filePath, lines);
        } catch (Exception e) {
            log.error("Error occured print html.", e);
        }
    }

    String createTHeadFirst(List<LogRecoredBean> recoredList) {
        StringBuilder sb = new StringBuilder();
        sb.append("<th>").append("</th>");

        boolean isSkip = false;
        for (LogRecoredBean rec : recoredList) {

            if (isSkip) {
                isSkip = false;
                continue;
            }

            if ("UPDATE".equals(rec.getCrudType())) {
                sb.append("<th colspan=\"2\">");
                isSkip = true;
            } else {
                sb.append("<th>");
            }
            sb.append(rec.getCrudType()).append("</th>");

        }
        return sb.toString();
    }

    String createTHeadSecond(List<LogRecoredBean> recoredList) {
        StringBuilder sb = new StringBuilder();
        sb.append("<th>").append("</th>");
        recoredList.forEach(rec -> {
            sb.append("<th>");
            sb.append(rec.getHistoryType());
            sb.append("</th>");
        });
        return sb.toString();
    }

    String createTBody(List<LogRecoredBean> recoredList) {
        List<String> tdList = createTDList(recoredList);

        StringBuilder sb = new StringBuilder();
        tdList.forEach(td -> {
            sb.append("<tr>").append(td).append("</tr>").append(System.lineSeparator());
        });

        return sb.toString();
    }

    List<String> createTDList(List<LogRecoredBean> recoredList) {
        List<String> dataList = new ArrayList<>();
        if (recoredList.isEmpty() || recoredList.get(0).getDataList().isEmpty()) {
            return dataList;
        }

        for (int i = 0; i < recoredList.get(0).getDataList().size(); i++) {
            StringBuilder data = new StringBuilder();
            String oldValue = null;
            for (LogRecoredBean rec : recoredList) {
                if (data.length() == 0) {
                    // Column name
                    data.append("<td class='column-green'>");
                    data.append(rec.getDataList().get(i).getKey());
                    data.append("</td>");
                }

                // diff update column
                // TODO refactoring
                if ("UPDATE".equals(rec.getCrudType())) {
                    if ("OLD".equals(rec.getHistoryType())) {
                        oldValue = rec.getDataList().get(i).getValue();
                        data.append("<td>");
                    }
                    if ("NEW".equals(rec.getHistoryType())) {
                        if (!oldValue.equals(rec.getDataList().get(i).getValue())) {
                            data.append("<td class='diff-color'>");
                        } else {
                            data.append("<td>");
                        }
                    }
                } else {
                    data.append("<td class='diff-color'>");
                }

                //                data.append("<pre>");
                data.append(escapeHtml(rec.getDataList().get(i).getValue()));
                //                data.append("</pre>");
                data.append("</td>");
            }

            dataList.add(data.toString());
        }
        return dataList;
    }

    String escapeHtml(String value) {
        if (value == null || value.length() == 0) {
            return value;
        }
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
            case '<':
                sb.append("&lt;");
                break;
            case '>':
                sb.append("&gt;");
                break;
            case '\r':
                sb.append("&#13;"); // TODO HTMLでは改行されない
                break;
            case '\n':
                sb.append("&#10;");
                break;
            case ' ':
                sb.append("&nbsp;");
                break;
            case '　':
                sb.append("&emsp;");
                break;
            default:
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
