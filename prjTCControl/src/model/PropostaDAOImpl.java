package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PropostaDAOImpl implements PropostaDAO {
	private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	private static final String JDBC_URL = "jdbc:mariadb://localhost:3306/DB_TCControl?allowMultiQueries=true";
	private static final String JDBC_USER = "root";
	private static final String JDBC_PASS = "alunofatec";
	private Connection con;

	public PropostaDAOImpl() throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER);
		con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
	}

	@Override
	public model.propostaTCC adicionar(model.propostaTCC p) throws SQLException {
		String sql = "INSERT INTO PropostaTCC(alunos, orientador, tema, tipo) VALUES (?, ?, ?, ?)";

		PreparedStatement st = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		st.setString(1, p.getAlunos());
		st.setString(2, p.getOrientador());
		st.setString(3, p.getTema());
		st.setString(4, p.getTipo());
		st.executeUpdate();

		return p;
	}

	@Override
	public void atualizar(long id, model.propostaTCC p) throws SQLException {
		String sql = "UPDATE PropostaTCC SET alunos = ?, orientador = ?, tema = ?, tipo = ? WHERE id = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, p.getAlunos());
		st.setString(2, p.getOrientador());
		st.setString(3, p.getTema());
		st.setString(4, p.getTipo());
		st.setLong(5, id);
		st.executeUpdate();

	}

	@Override
	public void remover(long id) throws SQLException {
		String sql = "DELETE FROM PropostaTCC WHERE id = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setLong(1, id);
		st.executeUpdate();
	}

	@Override
	public List<model.propostaTCC> procurarPorTema(String tema) throws SQLException {

		List<model.propostaTCC> lista = new ArrayList<>();

		String sql = "SELECT * FROM PropostaTCC WHERE tema LIKE ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%" + tema + "%");
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			model.propostaTCC p = new model.propostaTCC();
			p.setId(rs.getLong("id"));
			p.setAlunos(rs.getString("alunos"));
			p.setOrientador(rs.getString("orientador"));
			p.setTema(rs.getString("tema"));
			p.setTipo(rs.getString("tipo"));

			lista.add(p);
		}
		return lista;
	}


}
