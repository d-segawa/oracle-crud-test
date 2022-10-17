package org.crudtest.core.service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.crudtest.core.properties.ApplicationProperties;
import org.crudtest.core.repository.OracleRepository;
import org.crudtest.core.repository.entity.LogTable;
import org.crudtest.core.service.bean.LogRecoredBean;
import org.crudtest.core.service.logic.DataConverter;
import org.crudtest.core.log.AppLogger;
import org.crudtest.core.util.IOUtil;

public class TsvPrintService {

    private static AppLogger log = AppLogger.getLogger(TsvPrintService.class);

    public static final String LOG_TABLE_NAME = ApplicationProperties.LOG_TABLE_NAME.getValue();

    final OracleRepository repo;

    final DataConverter converter;

    public TsvPrintService() {
        this.repo = new OracleRepository();
        this.converter = new DataConverter();
    }

    public void print(String targetTableName, Path filePath) {
        try {
            List<LogTable> logsTableList = repo.selectLog(LOG_TABLE_NAME, targetTableName);
            List<LogRecoredBean> recoredList = converter.converteList(logsTableList);

            List<String> tsvList = new ArrayList<>();
            tsvList.addAll(createHeaderList(recoredList));
            tsvList.addAll(createBodyList(recoredList));

            IOUtil.writeFile(filePath, tsvList);

        } catch (Exception e) {
            log.error("Error occured print tsv.", e);
        }
    }

    List<String> createHeaderList(List<LogRecoredBean> recoredList) {
        List<String> headerList = new ArrayList<>();
        if (recoredList.isEmpty()) {
            return headerList;
        }

        StringBuilder row1 = new StringBuilder();
        StringBuilder row2 = new StringBuilder();
        for (LogRecoredBean logsBean : recoredList) {
            if (row1.length() != 0 && row2.length() != 0) {
                row1.append("\t");
                row2.append("\t");
            }
            row1.append(logsBean.getCrudType());
            row2.append(logsBean.getHistoryType());
        }
        headerList.add(row1.toString());
        headerList.add(row2.toString());
        return headerList;
    }

    List<String> createBodyList(List<LogRecoredBean> recoredList) {
        List<String> bodyList = new ArrayList<>();
        if (recoredList.isEmpty()) {
            return bodyList;
        }

        int colListSize = recoredList.get(0).getDataList().size();
        for (int i = 0; i < colListSize; i++) {
            StringBuilder row = new StringBuilder();
            for (LogRecoredBean rec : recoredList) {
                if (row.length() != 0) {
                    row.append("\t");
                }
                row.append(rec.getDataList().get(i).getValue());
            }
            bodyList.add(row.toString());
        }
        return bodyList;
    }

}
