package controller;

import java.sql.SQLException;
import java.util.List;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ctrlBanca {

	private LongProperty id = new SimpleLongProperty(0);
	private StringProperty data = new SimpleStringProperty("");
	private StringProperty alunos = new SimpleStringProperty("");
	private StringProperty orientadores = new SimpleStringProperty("");

	private model.BancaDAO BancaDAO;

	public ctrlBanca() throws ClassNotFoundException, SQLException {
		BancaDAO = new model.BancaDAOImpl();
	}

	private long contadorId = 1;

	private ObservableList<model.Banca> listaBancas = FXCollections.observableArrayList();

	public void limpar() {

		id.set(0);
		data.set("");
		alunos.set("");
		orientadores.set("");

	}

	public void excluir(model.Banca b) throws SQLException {
		listaBancas.remove(b);
		BancaDAO.remover(b.getId());
	}

	public void fromEntity(model.Banca b) {
		id.set(b.getId());
		data.set(b.getData());
		alunos.set(b.getAlunos());
		orientadores.set(b.getOrientadores());
	}

	public void adicionar() throws SQLException {
		if (id.get() == 0) {
			id.set(contadorId++);
			model.Banca b = new model.Banca();
			b.setId(id.get());
			b.setData(data.get());
			b.setAlunos(alunos.get());
			b.setOrientadores(orientadores.get());
			BancaDAO.adicionar(b);

			listaBancas.add(b);
		} else {
			model.Banca b = new model.Banca();
			b.setId(id.get());
			b.setData(data.get());
			b.setAlunos(alunos.get());
			b.setOrientadores(orientadores.get());

			for (int i = 0; i < listaBancas.size(); i++) {
				model.Banca banca = listaBancas.get(i);
				if (banca.getId() == id.get()) {
					listaBancas.set(i, b);
				}
			}
			BancaDAO.atualizar(id.get(), b);
		}
	}

	public void pesquisar() throws SQLException {
		listaBancas.clear();
		List<model.Banca> lst = BancaDAO.procurarPorData(data.get());
		listaBancas.addAll(lst);
	}

	public StringProperty dataProperty() {
		return data;
	}

	public StringProperty alunosProperty() {
		return alunos;
	}

	public StringProperty orientadoresProperty() {
		return orientadores;
	}

	public ObservableList<model.Banca> getLista() {
		return listaBancas;
	}

}
