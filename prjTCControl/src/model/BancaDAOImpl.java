package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BancaDAOImpl implements BancaDAO {
	private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	private static final String JDBC_URL = "jdbc:mariadb://localhost:3306/DB_TCControl?allowMultiQueries=true";
	private static final String JDBC_USER = "root";
	private static final String JDBC_PASS = "alunofatec";
	private Connection con;

	public BancaDAOImpl() throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER);
		con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
	}

	@Override
	public model.Banca adicionar(model.Banca b) throws SQLException {
		String sql = "INSERT INTO banca(data, alunos, orientadores) VALUES (?, ?, ?)";

		PreparedStatement st = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		st.setString(1, b.getData());
		st.setString(2, b.getAlunos());
		st.setString(3, b.getOrientadores());
		st.executeUpdate();

		return b;
	}

	@Override
	public void atualizar(long id, model.Banca b) throws SQLException {
		String sql = "UPDATE banca SET data = ?, alunos = ?, orientadores = ? WHERE id = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, b.getData());
		st.setString(2, b.getAlunos());
		st.setString(3, b.getOrientadores());
		st.setLong(4, id);
		st.executeUpdate();

	}

	@Override
	public void remover(long id) throws SQLException {
		String sql = "DELETE FROM banca WHERE id = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setLong(1, id);
		st.executeUpdate();
	}

	@Override
	public List<model.Banca> procurarPorData(String data) throws SQLException {

		List<model.Banca> lista = new ArrayList<>();

		String sql = "SELECT * FROM banca WHERE data LIKE ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%" + data + "%");
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			model.Banca b = new model.Banca();
			b.setId(rs.getLong("id"));
			b.setData(rs.getString("data"));
			b.setAlunos(rs.getString("alunos"));
			b.setOrientadores(rs.getString("orientadores"));

			lista.add(b);
		}
		return lista;
	}

}
