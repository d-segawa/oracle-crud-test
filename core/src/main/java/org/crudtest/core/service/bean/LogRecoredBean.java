package org.crudtest.core.service.bean;

import java.util.ArrayList;
import java.util.List;

public class LogRecoredBean {

    private String crudType;

    private String historyType;

    private final List<Data> dataList;

    public LogRecoredBean() {
        this.dataList = new ArrayList<>();
    }

    public List<Data> getDataList() {
        return dataList;
    }

    public void addDataList(Data data) {
        this.dataList.add(data);
    }

    public static class Data {

        private String key;

        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Data [key=" + key + ", value=" + value + "]";
        }

    }

    public String getCrudType() {
        return crudType;
    }

    public void setCrudType(String crudType) {
        this.crudType = crudType;
    }

    public String getHistoryType() {
        return historyType;
    }

    public void setHistoryType(String historyType) {
        this.historyType = historyType;
    }

    @Override
    public String toString() {
        return "LogsRecoredBean [crudType=" + crudType + ", historyType=" + historyType + ", dataList=" + dataList
                + "]";
    }

}
