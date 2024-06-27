package controller;

import java.sql.SQLException;
import java.util.List;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ctrlProposta {

	private LongProperty id = new SimpleLongProperty(0);
	private StringProperty alunos = new SimpleStringProperty("");
	private StringProperty orientador = new SimpleStringProperty("");
	private StringProperty tema = new SimpleStringProperty("");
	private StringProperty tipo = new SimpleStringProperty("");

	private model.PropostaDAO PropostaDAO;

	public ctrlProposta() throws ClassNotFoundException, SQLException {
		PropostaDAO = new model.PropostaDAOImpl();
	}

	private long contadorId = 1;

	private ObservableList<model.propostaTCC> listaPropostas = FXCollections.observableArrayList();

	public void limpar() {

		id.set(0);
		alunos.set("");
		orientador.set("");
		tema.set("");
		tipo.set("");

	}

	public void excluir(model.propostaTCC p) throws SQLException {
		listaPropostas.remove(p);
		PropostaDAO.remover(p.getId());
	}

	public void fromEntity(model.propostaTCC p) {
		id.set(p.getId());
		alunos.set(p.getAlunos());
		orientador.set(p.getOrientador());
		tema.set(p.getTema());
		tipo.set(p.getTipo());
	}

	public void adicionar() throws SQLException {
		if (id.get() == 0) {
			id.set(contadorId++);
			model.propostaTCC p = new model.propostaTCC();
			p.setId(id.get());
			p.setAlunos(alunos.get());
			p.setOrientador(orientador.get());
			p.setTema(tema.get());
			p.setTipo(tipo.get());
			PropostaDAO.adicionar(p);

			listaPropostas.add(p);
		} else {
			model.propostaTCC p = new model.propostaTCC();
			p.setId(id.get());
			p.setAlunos(alunos.get());
			p.setOrientador(orientador.get());
			p.setTema(tema.get());
			p.setTipo(tipo.get());

			for (int i = 0; i < listaPropostas.size(); i++) {
				model.propostaTCC proposta = listaPropostas.get(i);
				if (proposta.getId() == id.get()) {
					listaPropostas.set(i, p);
				}
			}
			PropostaDAO.atualizar(id.get(), p);
		}
	}

	public void pesquisar() throws SQLException {
		listaPropostas.clear();
		List<model.propostaTCC> lst = PropostaDAO.procurarPorTema(tema.get());
		listaPropostas.addAll(lst);
	}

	public StringProperty alunosProperty() {
		return alunos;
	}

	public StringProperty orientadorProperty() {
		return orientador;
	}

	public StringProperty temaProperty() {
		return tema;
	}

	public StringProperty tipoProperty() {
		return tipo;
	}

	public ObservableList<model.propostaTCC> getLista() {
		return listaPropostas;
	}

}
