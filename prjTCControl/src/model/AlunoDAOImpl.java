package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAOImpl implements AlunoDAO {
	private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	private static final String JDBC_URL = "jdbc:mariadb://localhost:3306/DB_TCControl?allowMultiQueries=true";
	private static final String JDBC_USER = "root";
	private static final String JDBC_PASS = "alunofatec";
	private Connection con;

	public AlunoDAOImpl() throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER);
		con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
	}

	@Override
	public model.AlunoSimples adicionar(model.AlunoSimples a) throws SQLException {
		String sql = "INSERT INTO aluno(nome, cpf, rg, curso, ra, semestre) VALUES (?, ?, ?, ?, ?, ?)";

		PreparedStatement st = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		st.setString(1, a.getNome());
		st.setString(2, a.getCPF());
		st.setString(3, a.getRG());
		st.setString(4, a.getCurso());
		st.setString(5, a.getRA());
		st.setString(6, a.getSemestre());
		st.executeUpdate();

		return a;
	}

	@Override
	public void atualizar(long ra, model.AlunoSimples a) throws SQLException {
		String sql = "UPDATE aluno SET nome = ?, cpf = ?, rg = ?, curso = ?, ra = ?, semestre = ? WHERE ra = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, a.getNome());
		st.setString(2, a.getCPF());
		st.setString(3, a.getRG());
		st.setString(4, a.getCurso());
		st.setString(5, a.getRA());
		st.setString(6, a.getSemestre());
		st.executeUpdate();

	}

	@Override
	public void remover(String ra) throws SQLException {
		String sql = "DELETE FROM aluno WHERE ra = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, ra);
		st.executeUpdate();
	}

	@Override
	public List<model.AlunoSimples> procurarPorNome(String nome) throws SQLException {

		List<model.AlunoSimples> lista = new ArrayList<>();

		String sql = "SELECT * FROM aluno WHERE nome LIKE ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%" + nome + "%");
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			model.AlunoSimples a = new model.AlunoSimples();
			a.setNome(rs.getString("nome"));
			a.setCPF(rs.getString("cpf"));
			a.setRG(rs.getString("rg"));
			a.setCurso(rs.getString("curso"));
			a.setRA(rs.getString("ra"));
			a.setSemestre(rs.getString("semestre"));

			lista.add(a);
		}
		return lista;
	}

}
