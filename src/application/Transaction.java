package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import db.DB;
import db.DBException;
import db.DBIntegrityException;

public class Transaction {
	public static void main(String[] args) {
		Connection conn = null;
		Statement st = null;

		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false); // deixa as querys pedendes

			st = conn.createStatement();

			int rowsAffected1 = st.executeUpdate("UPDATE Seller SET BaseSalary = 2090 WHERE DepartmentId = 1;");
//			int x = 1;
//			if (x < 2)
//				throw new SQLException("Fake error");
			int rowsAffected2 = st.executeUpdate("UPDATE Seller SET BaseSalary = 3090 WHERE DepartmentId = 2;");

			conn.commit(); // permite que as querys pendentes sejam executadas
			
			System.out.println("Done! Rows affected1: " + rowsAffected1);
			System.out.println("Done! Rows affected2: " + rowsAffected2);
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new DBIntegrityException(e.getMessage());
		} catch (SQLException e) {
			try {
				conn.rollback();
				throw new DBException("Transaction rolled back! Caused by: " + e.getMessage());
			} catch (SQLException e1) {
				throw new DBException("Error trying rollbakc! Caused by: " + e1.getMessage());
			}
		} finally {
			DB.closeStatement(st);
			DB.closeClonnection();
		}

	}
}
