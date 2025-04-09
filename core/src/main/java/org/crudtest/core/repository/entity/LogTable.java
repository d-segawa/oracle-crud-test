package org.crudtest.core.repository.entity;

import java.time.LocalDateTime;

public class LogTable {

    private String id;

    private String tableName;

    private String crudType;

    private String historyType;

    private String data;

    private LocalDateTime inserDate;
    
    private String refelenceId;

    public String getRefelenceId() {
		return refelenceId;
	}

	public void setRefelenceId(String refelenceId) {
		this.refelenceId = refelenceId;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public LocalDateTime getInserDate() {
        return inserDate;
    }

    public void setInserDate(LocalDateTime inserDate) {
        this.inserDate = inserDate;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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
		return "LogTable [id=" + id + ", tableName=" + tableName + ", crudType=" + crudType + ", historyType="
				+ historyType + ", data=" + data + ", inserDate=" + inserDate + ", refelenceId=" + refelenceId + "]";
	}

}
