package org.crudtest.core.service.logic;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.crudtest.core.service.bean.MetaTableInfo;
import org.crudtest.core.util.DbUtil.DbMetaFunction;

public class OracleMetaDataCreator {

    public DbMetaFunction<DatabaseMetaData, MetaTableInfo> create(String schema, String tableName) {
        return new DbMetaFunction<DatabaseMetaData, MetaTableInfo>() {

            @Override
            public MetaTableInfo apply(DatabaseMetaData meta) throws SQLException {
                MetaTableInfo tableInfo = new MetaTableInfo();
                tableInfo.setTableName(tableName);
                ResultSet rs = meta.getColumns(null, schema, tableName, "%");
                List<MetaTableInfo.ColumnInfo> columnInfoList = new ArrayList<>();
                while (rs.next()) {
                    MetaTableInfo.ColumnInfo ci = new MetaTableInfo.ColumnInfo();
                    ci.setColumnName(rs.getString("COLUMN_NAME"));
                    ci.setDataType(rs.getInt("DATA_TYPE"));
                    ci.setTypeName(rs.getString("TYPE_NAME"));
                    ci.setNullable(rs.getInt("NULLABLE"));
                    ci.setIsNullable(rs.getString("IS_NULLABLE"));
                    columnInfoList.add(ci);
                }

                rs = meta.getPrimaryKeys(null, schema, tableName);
                List<MetaTableInfo.MetaPkColumnInfo> pkColumnInfoList = new ArrayList<>();
                while (rs.next()) {
                    MetaTableInfo.MetaPkColumnInfo pki = new MetaTableInfo.MetaPkColumnInfo();
                    pki.setColumnName(rs.getString("COLUMN_NAME"));
                    pki.setKeySeq(rs.getInt("KEY_SEQ"));
                }
                tableInfo.setColumnList(columnInfoList);
                tableInfo.setPkColumnList(pkColumnInfoList);
                return tableInfo;
            }
        };
    }
}
