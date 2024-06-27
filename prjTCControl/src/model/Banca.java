package model;

public class Banca {

	private long id;
	private String data;
	private String orientadores;
	private String alunos;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getOrientadores() {
		return orientadores;
	}

	public void setOrientadores(String orientadores) {
		this.orientadores = orientadores;
	}

	public String getAlunos() {
		return alunos;
	}

	public void setAlunos(String alunos) {
		this.alunos = alunos;
	}

}
