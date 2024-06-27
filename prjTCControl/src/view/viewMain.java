package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class viewMain extends Application implements Executor {

	private Tela aluno;
	private Tela orientador;

	private BorderPane telaPrincipal;

	@Override
	public void start(Stage stage) throws Exception {

		aluno = new viewMainAluno();
		aluno.start();

		orientador = new viewMainOrientador();
		orientador.start();

		telaPrincipal = new BorderPane();

		TabPane abas = new TabPane();

		telaPrincipal.setCenter(abas);
		MenuBar mnuBar = new MenuBar();
		Menu mnuCadastro = new Menu("Cadastro");

		mnuBar.getMenus().addAll(mnuCadastro);

		MenuItem mnuAluno = new MenuItem("Aluno");
		mnuAluno.setOnAction(e -> {
			stage.setTitle("  |  TCControl - Cadastro de Aluno");
			abrir("aluno");
		});

		MenuItem mnuOrientador = new MenuItem("Orientador");
		mnuOrientador.setOnAction(e -> {
			stage.setTitle("  |  TCControl - Cadastro de Orientador");
			abrir("orientador");
		});

		MenuItem mnuBanca = new MenuItem("Banca");
		mnuBanca.setOnAction(e -> {
			stage.setTitle("  |  TCControl - Cadastro de Banca");
			abrir("banca");
		});

		MenuItem mnuTCC = new MenuItem("TCC");
		mnuTCC.setOnAction(e -> {
			stage.setTitle(" |  TCControl - Cadastro de TCC");
			abrir("tcc");
		});

		MenuItem mnuProposta = new MenuItem("Proposta TCC");
		mnuProposta.setOnAction(e -> {
			stage.setTitle("  |  TCControl - Cadastro de Proposta de TCC");
			abrir("proposta");
		});

		mnuCadastro.getItems().addAll(mnuAluno, mnuOrientador, mnuBanca, mnuTCC, mnuProposta);

		telaPrincipal.setTop(mnuBar);

		Scene scn = new Scene(telaPrincipal, (1920 * 0.75), (1080 * 0.75));

		Image icone = new Image("Logo.png");
		stage.getIcons().add(icone);

		scn.getStylesheets().add("design.css");

		stage.setScene(scn);
		stage.setResizable(true);
		stage.setTitle("  |  TCControl");
		stage.show();
	}

	public void abrir(String cmd) {
		if ("aluno".equals(cmd)) {
			telaPrincipal.setCenter(aluno.render());
		}
		if ("orientador".equals(cmd)) {
			telaPrincipal.setCenter(orientador.render());
		}
		if ("banca".equals(cmd)) {
			telaPrincipal.setCenter(banca.render());
		}
		if ("tcc".equals(cmd)) {
			telaPrincipal.setCenter(TCC.render());
		}
		if ("proposta".equals(cmd)) {
			telaPrincipal.setCenter(propostaTCC.render());
		}
	}

	public static void main(String[] args) {
		Application.launch(viewMain.class, args);
	}

}
