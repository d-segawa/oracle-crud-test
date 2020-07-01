package org.crudtest.service.logic;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

import org.crudtest.log.AppLogger;
import org.crudtest.properties.ApplicationProperties;
import org.crudtest.repository.entity.LogTable;
import org.crudtest.service.bean.LogRecoredBean;
import org.crudtest.service.bean.LogRecoredBean.Data;

public class DataConverter {

    static AppLogger log = AppLogger.getLogger(DataConverter.class);

    public List<LogRecoredBean> converteList(List<LogTable> logsTableList) {
        return logsTableList.stream().collect(() -> new ArrayList<LogRecoredBean>(), (recoredList, logs) -> {
            recoredList.add(converte(logs));
        }, (list1, list2) -> list1.addAll(list2));
    }

    public LogRecoredBean converte(LogTable logsTable) {
        String logsData = logsTable.getData();
        String[] dataArr = logsData.split(",");
        LogRecoredBean dataBean = Stream.of(dataArr).collect(() -> new LogRecoredBean(), (bean, data) -> {
            Data d = new Data();
            String[] kv = data.split(":");
            d.setKey(kv[0]);
            d.setValue(decode(kv[1]));
            bean.addDataList(d);
        }, (b1, b2) -> {
            b1.getDataList().addAll(b2.getDataList());
        });

        dataBean.setCrudType(logsTable.getCrudType());
        dataBean.setHistoryType(logsTable.getHistoryType());

        log.info(dataBean.toString());
        return dataBean;
    }

    String decode(String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        byte[] byteArr = Base64.getDecoder().decode(value.getBytes(dbCharSet()));
        return new String(byteArr, dbCharSet());
    }

    Charset dbCharSet() {
        return Charset.forName(ApplicationProperties.DB_CHARSET.getValue());
    }
}
