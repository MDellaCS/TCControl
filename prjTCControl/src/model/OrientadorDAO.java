package model;

import java.sql.SQLException;
import java.util.List;

public interface OrientadorDAO {

	Orientador adicionar(Orientador o) throws SQLException;

	void atualizar(String cpf, Orientador o) throws SQLException;

	List<Orientador> procurarPorNome(String nome) throws SQLException;

	void remover(String cpf) throws SQLException;

}
