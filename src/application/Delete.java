package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import db.DB;
import db.DBIntegrityException;

public class Delete {
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement st = null;

		try {
			conn = DB.getConnection();

			st = conn.prepareStatement("DELETE FROM Department WHERE Id = ?;");

			st.setInt(1, 2);

			int rowsAffected = st.executeUpdate();

			System.out.println("Done! Rows affected: " + rowsAffected);
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new DBIntegrityException(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(st);
			DB.closeClonnection();
		}

	}
}
