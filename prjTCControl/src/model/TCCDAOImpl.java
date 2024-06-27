package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TCCDAOImpl implements TCCDAO {
	private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	private static final String JDBC_URL = "jdbc:mariadb://localhost:3306/DB_TCControl?allowMultiQueries=true";
	private static final String JDBC_USER = "root";
	private static final String JDBC_PASS = "alunofatec";
	private Connection con;

	public TCCDAOImpl() throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER);
		con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
	}

	@Override
	public model.TCC adicionar(model.TCC t) throws SQLException {
		String sql = "INSERT INTO TCC(nome, aprovado) VALUES (?, ?)";

		PreparedStatement st = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		st.setString(1, t.getNomeProjeto());
		st.setString(2, t.getAprovado());
		st.executeUpdate();

		return t;
	}

	@Override
	public void atualizar(long id, model.TCC t) throws SQLException {
		String sql = "UPDATE TCC SET nome = ?, aprovado = ? WHERE id = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, t.getNomeProjeto());
		st.setString(2, t.getAprovado());
		st.setLong(3, id);
		st.executeUpdate();

	}

	@Override
	public void remover(long id) throws SQLException {
		String sql = "DELETE FROM TCC WHERE id = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setLong(1, id);
		st.executeUpdate();
	}

	@Override
	public List<model.TCC> procurarPorNome(String nome) throws SQLException {

		List<model.TCC> lista = new ArrayList<>();

		String sql = "SELECT * FROM TCC WHERE nome LIKE ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%" + nome + "%");
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			model.TCC t = new model.TCC();
			t.setId(rs.getLong("id"));
			t.setNomeProjeto(rs.getString("nome"));
			t.setAprovado(rs.getString("aprovado"));

			lista.add(t);
		}
		return lista;
	}

}
