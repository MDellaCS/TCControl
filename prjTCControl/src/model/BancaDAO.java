package model;

import java.sql.SQLException;
import java.util.List;

public interface BancaDAO {

	model.Banca adicionar(model.Banca b) throws SQLException;

	void atualizar(long id, model.Banca b) throws SQLException;

	void remover(long id) throws SQLException;

	List<model.Banca> procurarPorData(String data) throws SQLException;

}
