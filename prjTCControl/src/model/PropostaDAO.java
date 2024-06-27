package model;

import java.sql.SQLException;
import java.util.List;

public interface PropostaDAO {

	model.propostaTCC adicionar(model.propostaTCC p) throws SQLException;

	void atualizar(long id, model.propostaTCC p) throws SQLException;

	void remover(long id) throws SQLException;

	List<model.propostaTCC> procurarPorTema(String tema) throws SQLException;

}
