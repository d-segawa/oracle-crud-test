package org.crudtest.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.crudtest.exception.JdbcException;
import org.crudtest.log.AppLogger;

public class DbUtil {

    private static AppLogger log = AppLogger.getLogger(DbUtil.class);

    public static <T> T getMetaData(DbMetaFunction<DatabaseMetaData, T> function) throws JdbcException {

        try (Connection connection = ConnectionUtil.getManager().getConnection()) {

            DatabaseMetaData metaData = connection.getMetaData();
            return function.apply(metaData);

        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    public static <T> T execute(String sql, SqlConsumer sqlConsumer,
            ResultFunction<T> resultFunction) throws JdbcException {

        try (Connection con = ConnectionUtil.getManager().getConnection()) {
            log.info("Execute sql : " + sql);

            PreparedStatement ps = con.prepareStatement(sql);
            sqlConsumer.accept(ps);

            try (ResultSet rs = ps.executeQuery()) {
                return resultFunction.apply(rs);
            }
        } catch (SQLException | IOException e) {
            throw new JdbcException(e);
        }
    }

    public static <T> T execute(String sql,
            ResultFunction<T> resultFunction) throws JdbcException {

        try (Connection con = ConnectionUtil.getManager().getConnection()) {
            log.info("Execute sql : " + sql);

            Statement st = con.createStatement();

            try (ResultSet rs = st.executeQuery(sql)) {
                return resultFunction.apply(rs);
            }
        } catch (SQLException | IOException e) {
            throw new JdbcException(e);
        }
    }

    public static <T> int executeUpdate(String sql, SqlConsumer sqlConsumer) throws JdbcException {

        try (Connection con = ConnectionUtil.getManager().getConnection()) {
            log.info("Execute sql : " + sql);

            PreparedStatement ps = con.prepareStatement(sql);
            sqlConsumer.accept(ps);

            try {
                int count = ps.executeUpdate();
                DbUtil.commit(con);
                return count;

            } catch (SQLException e) {
                DbUtil.rollback(con);
                throw new JdbcException(e);
            }
        } catch (SQLException se) {
            throw new JdbcException(se);
        }
    }

    public static Optional<LocalDateTime> sqlToLocalDateTime(java.sql.Date date) {
        return Optional.ofNullable(date).map(d -> {
            Instant ins = Instant.ofEpochMilli(d.getTime());
            return LocalDateTime.ofInstant(ins, ZoneId.systemDefault());
        });
    }

    public static void rollback(Connection con) throws JdbcException {
        try {
            if (con != null && !con.getAutoCommit()) {
                con.rollback();
            }
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    public static void commit(Connection con) throws JdbcException {
        try {
            if (con != null && !con.getAutoCommit()) {
                con.commit();
            }
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    @FunctionalInterface
    public static interface DbMetaFunction<T, R> {
        R apply(T t) throws SQLException;
    }

    @FunctionalInterface
    public static interface SqlConsumer {
        void accept(PreparedStatement ps) throws SQLException;
    }

    @FunctionalInterface
    public static interface ResultFunction<T> {
        T apply(ResultSet rs) throws SQLException, IOException;
    }

}
