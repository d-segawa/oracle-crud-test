package org.crudtest.core.service.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MetaTableInfo {

    private Optional<String> tableName;

    private Optional<List<ColumnInfo>> columnList;

    private Optional<List<MetaPkColumnInfo>> pkColumnList;

    public String getTableName() {
        return tableName.orElse("");
    }

    public void setTableName(String tableName) {
        this.tableName = Optional.ofNullable(tableName);
    }

    public List<ColumnInfo> getColumnList() {
        return columnList.orElse(new ArrayList<>());
    }

    public void setColumnList(List<ColumnInfo> columnList) {
        this.columnList = Optional.ofNullable(columnList);
    }

    public List<MetaPkColumnInfo> getPkColumnList() {
        return pkColumnList.orElse(new ArrayList<>());
    }

    public void setPkColumnList(List<MetaPkColumnInfo> pkColumnList) {
        this.pkColumnList = Optional.ofNullable(pkColumnList);
    }

    public static class ColumnInfo {

        private Optional<String> columnName;

        // java.sql.Types;
        private int dataType;

        private Optional<String> typeName;

        // 0=notnull, 1=nullable, 2=unknown
        private int nullable;

        // YES=nullable, NO=notnull, emptyString=unknown
        private String isNullable;

        public String getColumnName() {
            return columnName.orElse("");
        }

        public void setColumnName(String columnName) {
            this.columnName = Optional.ofNullable(columnName);
        }

        public int getDataType() {
            return dataType;
        }

        public void setDataType(int dataType) {
            this.dataType = dataType;
        }

        public String getTypeName() {
            return typeName.orElse("");
        }

        public void setTypeName(String typeName) {
            this.typeName = Optional.ofNullable(typeName);
        }

        public int getNullable() {
            return nullable;
        }

        public void setNullable(int nullable) {
            this.nullable = nullable;
        }

        public String getIsNullable() {
            return isNullable;
        }

        public void setIsNullable(String isNullable) {
            this.isNullable = isNullable;
        }
    }

    public static class MetaPkColumnInfo {

        private Optional<String> columnName;

        private int keySeq;

        public String getColumnName() {
            return columnName.orElse("");
        }

        public void setColumnName(String columnName) {
            this.columnName = Optional.ofNullable(columnName);
        }

        public int getKeySeq() {
            return keySeq;
        }

        public void setKeySeq(int keySeq) {
            this.keySeq = keySeq;
        }
    }
}
