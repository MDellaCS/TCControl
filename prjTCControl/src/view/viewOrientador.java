package view;

import java.sql.SQLException;

import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

public class viewOrientador implements Tela {

	private TextField txtNome = new TextField();
	private TextField txtCPF = new TextField();
	private TextField txtRG = new TextField();
	private TextField txtCurso = new TextField();
	private TextField txtAlunos = new TextField();

	private controller.ctrlOrientador control;
	private TableView<model.Orientador> table = new TableView<>();

	private BorderPane principal;
	private Executor executor;

	public viewOrientador(Executor executor) {
		this.executor = executor;
	}

	public viewOrientador() {

	}

	public void ligacoes() {
		Bindings.bindBidirectional(txtNome.textProperty(), control.nomeProperty());
		Bindings.bindBidirectional(txtCPF.textProperty(), control.cpfProperty());
		Bindings.bindBidirectional(txtRG.textProperty(), control.rgProperty());
		Bindings.bindBidirectional(txtCurso.textProperty(), control.cursoProperty());
		Bindings.bindBidirectional(txtAlunos.textProperty(), control.alunosProperty());
	}

	@SuppressWarnings("unchecked")
	public void abastecerTableView() {

		TableColumn<model.Orientador, String> colNome = new TableColumn<>("Nome");
		colNome.setCellValueFactory(new PropertyValueFactory<model.Orientador, String>("nome"));

		TableColumn<model.Orientador, String> colCPF = new TableColumn<>("CPF");
		colCPF.setCellValueFactory(new PropertyValueFactory<model.Orientador, String>("cPF"));

		TableColumn<model.Orientador, String> colRG = new TableColumn<>("RG");
		colRG.setCellValueFactory(new PropertyValueFactory<model.Orientador, String>("rG"));

		TableColumn<model.Orientador, String> colCurso = new TableColumn<>("Curso");
		colCurso.setCellValueFactory(new PropertyValueFactory<model.Orientador, String>("curso"));

		TableColumn<model.Orientador, String> colAlunos = new TableColumn<>("Orientados");
		colAlunos.setCellValueFactory(new PropertyValueFactory<model.Orientador, String>("alunosOrientados"));

		TableColumn<model.Orientador, Void> colAcoes = new TableColumn<>("Ações");
		Callback<TableColumn<model.Orientador, Void>, TableCell<model.Orientador, Void>> callBack = new Callback<>() {

			@Override
			public TableCell<model.Orientador, Void> call(TableColumn<model.Orientador, Void> col) {
				TableCell<model.Orientador, Void> tCell = new TableCell<>() {

					final Button btnExcluir = new Button("Excluir");
					{
						btnExcluir.setOnAction(e -> {
							model.Orientador o = table.getItems().get(getIndex());
							try {
								control.excluir(o);
							} catch (SQLException e1) {
								System.out.println("ERRO AO EXCLUIR");
								e1.printStackTrace();
							}
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btnExcluir);
						}
					}
				};
				return tCell;
			}
		};

		colAcoes.setCellFactory(callBack);

		double espaco = 1920 * 0.75 / 6.0;
		colNome.setPrefWidth(espaco);
		colCPF.setPrefWidth(espaco);
		colRG.setPrefWidth(espaco);
		colCurso.setPrefWidth(espaco);
		colAlunos.setPrefWidth(espaco);
		colAcoes.setPrefWidth(espaco);

		table.getColumns().addAll(colNome, colCPF, colRG, colCurso, colAlunos, colAcoes);
		table.setItems(control.getLista());

		table.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<model.Orientador>() {
			@Override
			public void onChanged(Change<? extends model.Orientador> o) {
				if (!o.getList().isEmpty()) {
					control.fromEntity(o.getList().get(0));
				}
			}
		});
	}

	@Override
	public void start() {

		try {
			control = new controller.ctrlOrientador();
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("ERRO NO BD");
			System.out.println(e.getStackTrace());
		}

		principal = new BorderPane();

		GridPane grid = new GridPane();
		principal.setTop(grid);
		principal.setCenter(table);

		grid.add(new Label("Nome: "), 0, 0);
		grid.add(txtNome, 1, 0);

		grid.add(new Label("CPF: "), 0, 1);
		grid.add(txtCPF, 1, 1);

		grid.add(new Label("RG: "), 0, 2);
		grid.add(txtRG, 1, 2);

		grid.add(new Label("Curso: "), 0, 3);
		grid.add(txtCurso, 1, 3);

		grid.add(new Label("Alunos Orientados: "), 0, 4);
		grid.add(txtAlunos, 1, 4);

		ligacoes();
		abastecerTableView();

		Button btnLimpar = new Button("Limpar");
		btnLimpar.setOnAction(e -> control.limpar());

		Button btnSalvar = new Button("Salvar");
		btnSalvar.setOnAction(e -> {
			try {
				control.adicionar();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		Button btnPesquisar = new Button("Pesquisar");
		btnPesquisar.setOnAction(e -> {
			try {
				control.pesquisar();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		Button btnAlunos = new Button("Ver Alunos");
		btnAlunos.setOnAction(e -> executor.abrir("aluno"));

		grid.add(btnLimpar, 2, 0);
		grid.add(btnSalvar, 3, 0);
		grid.add(btnPesquisar, 4, 0);
		grid.add(btnAlunos, 2, 4);
	}

	@Override
	public Pane render() {
		return principal;
	}

}
