package model;

import java.sql.SQLException;
import java.util.List;

public interface TCCDAO {

	model.TCC adicionar(model.TCC p) throws SQLException;

	void atualizar(long id, model.TCC p) throws SQLException;

	void remover(long id) throws SQLException;

	List<model.TCC> procurarPorNome(String nome) throws SQLException;

}
