package model;

import java.sql.SQLException;
import java.util.List;

public interface AlunoDAO {

	model.AlunoSimples adicionar(model.AlunoSimples a) throws SQLException;

	void atualizar(long id, model.AlunoSimples a) throws SQLException;

	void remover(String string) throws SQLException;

	List<model.AlunoSimples> procurarPorNome(String nome) throws SQLException;

}
