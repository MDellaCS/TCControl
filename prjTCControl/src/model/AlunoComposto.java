package model;

import java.util.ArrayList;
import java.util.List;

class AlunoComposto implements Aluno {

	private String nome;
	private List<Aluno> alunos;

	public AlunoComposto(String nome) {
		this.nome = nome;
		alunos = new ArrayList<>();
	}

	public void addAluno(Aluno aluno) {
		alunos.add(aluno);
	}

	public void delAluno(Aluno aluno) {
		alunos.remove(aluno);
	}

	@Override
	public String getNome() {
		String nomes = "";
		for (Aluno a : alunos) {
			nomes += a.getNome();
		}
		return nomes;
	}

	@Override
	public String toString() {
		return "AlunoComposto [nome=" + nome + "]";
	}

}