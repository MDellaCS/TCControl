package controller;

import java.sql.SQLException;
import java.util.List;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ctrlTCC {

	private LongProperty id = new SimpleLongProperty(0);
	private StringProperty nome = new SimpleStringProperty("");
	private StringProperty aprovado = new SimpleStringProperty("");

	private model.TCCDAO TCCDAO;

	public ctrlTCC() throws ClassNotFoundException, SQLException {
		TCCDAO = new model.TCCDAOImpl();
	}

	private long contadorId = 1;

	private ObservableList<model.TCC> listaTCCs = FXCollections.observableArrayList();

	public void limpar() {

		id.set(0);
		nome.set("");
		aprovado.set("");

	}

	public void excluir(model.TCC t) throws SQLException {
		listaTCCs.remove(t);
		TCCDAO.remover(t.getId());
	}

	public void fromEntity(model.TCC t) {
		id.set(t.getId());
		nome.set(t.getNomeProjeto());
		aprovado.set(t.getAprovado());
	}

	public void adicionar() throws SQLException {
		if (id.get() == 0) {
			id.set(contadorId++);
			model.TCC t = new model.TCC();
			t.setId(id.get());
			t.setNomeProjeto(nome.get());
			t.setAprovado(aprovado.get());
			TCCDAO.adicionar(t);

			listaTCCs.add(t);
		} else {
			model.TCC t = new model.TCC();
			t.setId(id.get());
			t.setNomeProjeto(nome.get());
			t.setAprovado(aprovado.get());

			for (int i = 0; i < listaTCCs.size(); i++) {
				model.TCC tcc = listaTCCs.get(i);
				if (tcc.getId() == id.get()) {
					listaTCCs.set(i, t);
				}
			}
			TCCDAO.atualizar(id.get(), t);
		}
	}

	public void pesquisar() throws SQLException {
		listaTCCs.clear();
		List<model.TCC> lst = TCCDAO.procurarPorNome(nome.get());
		listaTCCs.addAll(lst);
	}

	public StringProperty nomeProperty() {
		return nome;
	}

	public StringProperty aprovadoProperty() {
		return aprovado;
	}

	public ObservableList<model.TCC> getLista() {
		return listaTCCs;
	}

}
