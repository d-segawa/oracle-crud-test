package org.crudtest.core.repository.oracle;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.crudtest.core.exception.JdbcException;
import org.crudtest.core.repository.DataBaseAssessor;
import org.crudtest.core.repository.OracleRepository;
import org.crudtest.core.repository.SqlLiterals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OracleRepositoryTest {

	@Test
	void testSelectLogs() throws JdbcException {
		OracleRepository repo = new OracleRepository();
		repo.selectLog(SqlLiterals.selectLogNonUpdateNew_sql, "XXX_CRUD_LOG_T", "TRANSACTION");
		repo.selectLog(SqlLiterals.selectLogUpdateNew_sql, "XXX_CRUD_LOG_T", "TRANSACTION");
	}

	@Test
	@DisplayName("マルチスレッドでUPDATE文を発行する")
	void createMultiThreadUpdate() throws Exception {

		String sql = "insert into sample_t values(?,?,?)";

		int ret = 0;
		for (int n = 0; n < 15; n++) {
			final int n2 = n;
			try {
				ret += DataBaseAssessor.executeUpdate(sql, ps -> {
					ps.setString(1, String.format("%03d", n2));
					ps.setString(2, String.format("name%s", n2));
					ps.setInt(3, n2);
				});
			} catch (JdbcException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		assertThat(ret).isEqualTo(15);

		
		String update = "update sample_t set name = ?, old = ? where id = ?";
		List<Callable<Integer>> callables = new ArrayList<>();

		for (int i = 0; i < 15; i++) {
			Bean b = new Bean();
			b.n = i;
			Callable<Integer> c = () -> {
				try {
					return DataBaseAssessor.executeUpdate(update, ps -> {
						ps.setString(1, "Name:" + System.currentTimeMillis());
						ps.setInt(2, 100 - b.n);
						ps.setString(3, String.format("%03d", b.n));
					});
				} catch (JdbcException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return 0;
				}
			};
			callables.add(c);
		}
		
		ExecutorService es = Executors.newFixedThreadPool(10);
		List<Future<Integer>> results =  es.invokeAll(callables);
		results.forEach(res -> {
			try {
				res.get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	static class Bean {
		public int n;
	}
}
