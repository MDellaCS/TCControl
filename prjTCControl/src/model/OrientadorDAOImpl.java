package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrientadorDAOImpl implements OrientadorDAO {
	private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	private static final String JDBC_URL = "jdbc:mariadb://localhost:3306/DB_TCControl?allowMultiQueries=true";
	private static final String JDBC_USER = "root";
	private static final String JDBC_PASS = "alunofatec";
	private Connection con;

	public OrientadorDAOImpl() throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER);
		con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
	}

	@Override
	public model.Orientador adicionar(model.Orientador o) throws SQLException {
		String sql = "INSERT INTO orientador(nome, cpf, rg, curso, alunos) VALUES (?, ?, ?, ?, ?)";

		PreparedStatement st = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		st.setString(1, o.getNome());
		st.setString(2, o.getCPF());
		st.setString(3, o.getRG());
		st.setString(4, o.getCurso());
		st.setString(5, o.getAlunosOrientados());
		st.executeUpdate();

		return o;
	}

	@Override
	public void atualizar(String cpf, model.Orientador o) throws SQLException {
		String sql = "UPDATE orientador SET nome = ?, cpf = ?, rg = ?, curso = ?, alunos = ? WHERE cpf = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, o.getNome());
		st.setString(2, o.getCPF());
		st.setString(3, o.getRG());
		st.setString(4, o.getCurso());
		st.setString(5, o.getAlunosOrientados());
		st.setString(6, cpf);
		st.executeUpdate();

	}

	@Override
	public void remover(String cpf) throws SQLException {
		String sql = "DELETE FROM orientador WHERE cpf = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, cpf);
		st.executeUpdate();
	}

	@Override
	public List<model.Orientador> procurarPorNome(String nome) throws SQLException {

		List<model.Orientador> lista = new ArrayList<>();

		String sql = "SELECT * FROM orientador WHERE nome LIKE ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%" + nome + "%");
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			model.Orientador o = new model.Orientador();
			o.setId(rs.getLong("id"));
			o.setNome(rs.getString("nome"));
			o.setCPF(rs.getString("cpf"));
			o.setRG(rs.getString("rg"));
			o.setCurso(rs.getString("curso"));
			o.setAlunosOrientados(rs.getString("alunos"));

			lista.add(o);
		}
		return lista;
	}


}
