package controller;

import java.sql.SQLException;
import java.util.List;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ctrlOrientador {

	private LongProperty id = new SimpleLongProperty(0);
	private StringProperty nome = new SimpleStringProperty("");
	private StringProperty cpf = new SimpleStringProperty("");
	private StringProperty rg = new SimpleStringProperty("");
	private StringProperty curso = new SimpleStringProperty("");
	private StringProperty alunos = new SimpleStringProperty("");

	private model.OrientadorDAO OrientadorDAO;

	public ctrlOrientador() throws ClassNotFoundException, SQLException {
		OrientadorDAO = new model.OrientadorDAOImpl();
	}

	private long contadorId = 1;

	private ObservableList<model.Orientador> listaOrientadores = FXCollections.observableArrayList();

	public void limpar() {

		id.set(0);
		nome.set("");
		cpf.set("");
		rg.set("");
		curso.set("");
		alunos.set("");

	}

	public void excluir(model.Orientador o) throws SQLException {
		listaOrientadores.remove(o);
		OrientadorDAO.remover(o.getCPF());
	}

	public void fromEntity(model.Orientador o) {
		id.set(o.getId());
		nome.set(o.getNome());
		cpf.set(o.getCPF());
		rg.set(o.getRG());
		curso.set(o.getCurso());
		alunos.set(o.getAlunosOrientados());
	}

	public void adicionar() throws SQLException {
		if (id.get() == 0) {
			id.set(contadorId++);
			model.Orientador o = new model.Orientador();
			o.setId(id.get());
			o.setNome(nome.get());
			o.setCPF(cpf.get());
			o.setRG(rg.get());
			o.setCurso(curso.get());
			o.setAlunosOrientados(alunos.get());
			OrientadorDAO.adicionar(o);

			listaOrientadores.add(o);
		} else {
			model.Orientador o = new model.Orientador();
			o.setId(id.get());
			o.setNome(nome.get());
			o.setCPF(cpf.get());
			o.setRG(rg.get());
			o.setCurso(curso.get());
			o.setAlunosOrientados(alunos.get());

			for (int i = 0; i < listaOrientadores.size(); i++) {
				model.Orientador orientador = listaOrientadores.get(i);
				if (orientador.getId() == id.get()) {
					listaOrientadores.set(i, o);
				}
			}
			OrientadorDAO.atualizar(cpf.get(), o);
		}
	}

	public void pesquisar() throws SQLException {
		listaOrientadores.clear();
		List<model.Orientador> lst = OrientadorDAO.procurarPorNome(nome.get());
		listaOrientadores.addAll(lst);
	}

	public StringProperty nomeProperty() {
		return nome;
	}

	public StringProperty cpfProperty() {
		return cpf;
	}

	public StringProperty rgProperty() {
		return rg;
	}

	public StringProperty cursoProperty() {
		return curso;
	}

	public StringProperty alunosProperty() {
		return alunos;
	}

	public ObservableList<model.Orientador> getLista() {
		return listaOrientadores;
	}

}
