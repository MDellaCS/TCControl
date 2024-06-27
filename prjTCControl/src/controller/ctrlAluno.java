package controller;

import java.sql.SQLException;
import java.util.List;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ctrlAluno {

	private LongProperty id = new SimpleLongProperty(0);
	private StringProperty nome = new SimpleStringProperty("");
	private StringProperty cpf = new SimpleStringProperty("");
	private StringProperty rg = new SimpleStringProperty("");
	private StringProperty curso = new SimpleStringProperty("");
	private StringProperty ra = new SimpleStringProperty("");
	private StringProperty semestre = new SimpleStringProperty("");

	private model.AlunoDAO AlunoDAO;

	public ctrlAluno() throws ClassNotFoundException, SQLException {
		AlunoDAO = new model.AlunoDAOImpl();
	}

	private long contadorId = 1;

	private ObservableList<model.AlunoSimples> listaAlunos = FXCollections.observableArrayList();

	public void limpar() {

		id.set(0);
		nome.set("");
		cpf.set("");
		rg.set("");
		curso.set("");
		ra.set("");
		semestre.set("");

	}

	public void excluir(model.AlunoSimples a) throws SQLException {
		listaAlunos.remove(a);
		AlunoDAO.remover(a.getRA());
	}

	public void fromEntity(model.AlunoSimples a) {
		id.set(a.getId());
		nome.set(a.getNome());
		cpf.set(a.getCPF());
		rg.set(a.getRG());
		curso.set(a.getCurso());
		ra.set(a.getRA());
		semestre.set(a.getSemestre());
	}

	public void adicionar() throws SQLException {
		if (id.get() == 0) {
			id.set(contadorId++);
			model.AlunoSimples a = new model.AlunoSimples();
			a.setId(id.get());
			a.setNome(nome.get());
			a.setCPF(cpf.get());
			a.setRG(rg.get());
			a.setCurso(curso.get());
			a.setRA(ra.get());
			a.setSemestre(semestre.get());
			AlunoDAO.adicionar(a);

			listaAlunos.add(a);
		} else {
			model.AlunoSimples a = new model.AlunoSimples();
			a.setId(id.get());
			a.setNome(nome.get());
			a.setCPF(cpf.get());
			a.setRG(rg.get());
			a.setCurso(curso.get());
			a.setRA(ra.get());
			a.setSemestre(semestre.get());

			for (int i = 0; i < listaAlunos.size(); i++) {
				model.AlunoSimples aluno = listaAlunos.get(i);
				if (aluno.getId() == id.get()) {
					listaAlunos.set(i, a);
				}
			}
			AlunoDAO.atualizar(id.get(), a);
		}
	}

	public void pesquisar() throws SQLException {
		listaAlunos.clear();
		List<model.AlunoSimples> lst = AlunoDAO.procurarPorNome(nome.get());
		listaAlunos.addAll(lst);
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

	public StringProperty raProperty() {
		return ra;
	}

	public StringProperty semestreProperty() {
		return semestre;
	}

	public ObservableList<model.AlunoSimples> getLista() {
		return listaAlunos;
	}

}
