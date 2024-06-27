package model;

public class AlunoSimples extends Pessoa implements Aluno{

	private String RA;
	private String Semestre;

	public String getRA() {
		return RA;
	}

	public void setRA(String rA) {
		RA = rA;
	}

	public String getSemestre() {
		return Semestre;
	}

	public void setSemestre(String semestre) {
		Semestre = semestre;
	}

}
