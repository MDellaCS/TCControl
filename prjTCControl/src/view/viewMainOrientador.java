package view;

import java.sql.SQLException;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

public class viewMainOrientador implements Tela {

	private TextField txtNome = new TextField();
	private TextField txtCPF = new TextField();
	private TextField txtRG = new TextField();
	private TextField txtCurso = new TextField();
	private TextField txtRA = new TextField();
	private TextField txtSemestre = new TextField();

	private controller.ctrlAluno control;
	private TableView<model.AlunoSimples> table = new TableView<>();

	private BorderPane principal;

	public viewAluno() {

	}

	public void adicionar() {
		try {
			control.adicionar();
		} catch (SQLException e) {
			System.out.println("ERRO AO ADICIONAR");
			e.printStackTrace();
		}
	}

	public void pesquisar() {
		try {
			control.pesquisar();
		} catch (SQLException e) {
			System.out.println("ERRO AO PESQUISAR");
			e.printStackTrace();
		}
	}

	public void ligacoes() {
		Bindings.bindBidirectional(txtNome.textProperty(), control.nomeProperty());
		Bindings.bindBidirectional(txtCPF.textProperty(), control.cpfProperty());
		Bindings.bindBidirectional(txtRG.textProperty(), control.rgProperty());
		Bindings.bindBidirectional(txtCurso.textProperty(), control.cursoProperty());
		Bindings.bindBidirectional(txtRA.textProperty(), control.raProperty());
		Bindings.bindBidirectional(txtSemestre.textProperty(), control.semestreProperty());
	}

	@SuppressWarnings("unchecked")
	public void abastecerTableView() {

		TableColumn<model.AlunoSimples, String> colNome = new TableColumn<>("Nome");
		colNome.setCellValueFactory(new PropertyValueFactory<model.AlunoSimples, String>("nome"));

		TableColumn<model.AlunoSimples, String> colCPF = new TableColumn<>("CPF");
		colCPF.setCellValueFactory(new PropertyValueFactory<model.AlunoSimples, String>("cPF"));

		TableColumn<model.AlunoSimples, String> colRG = new TableColumn<>("RG");
		colRG.setCellValueFactory(new PropertyValueFactory<model.AlunoSimples, String>("rG"));

		TableColumn<model.AlunoSimples, String> colCurso = new TableColumn<>("Curso");
		colCurso.setCellValueFactory(new PropertyValueFactory<model.AlunoSimples, String>("curso"));

		TableColumn<model.AlunoSimples, String> colRA = new TableColumn<>("RA");
		colRA.setCellValueFactory(new PropertyValueFactory<model.AlunoSimples, String>("rA"));

		TableColumn<model.AlunoSimples, String> colSemestre = new TableColumn<>("Semestre");
		colSemestre.setCellValueFactory(new PropertyValueFactory<model.AlunoSimples, String>("semestre"));

		TableColumn<model.AlunoSimples, Void> colAcoes = new TableColumn<>("Ações");
		Callback<TableColumn<model.AlunoSimples, Void>, TableCell<model.AlunoSimples, Void>> callBack = new Callback<>() {

			@Override
			public TableCell<model.AlunoSimples, Void> call(TableColumn<model.AlunoSimples, Void> col) {
				TableCell<model.AlunoSimples, Void> tCell = new TableCell<>() {

					final Button btnExcluir = new Button("Excluir");
					{
						btnExcluir.setOnAction(e -> {
							model.AlunoSimples a = table.getItems().get(getIndex());
							try {
								control.excluir(a);
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

		double espaco = 1920 * 0.75 / 7.0;
		colNome.setPrefWidth(espaco);
		colCPF.setPrefWidth(espaco);
		colRG.setPrefWidth(espaco);
		colCurso.setPrefWidth(espaco);
		colRA.setPrefWidth(espaco);
		colSemestre.setPrefWidth(espaco);
		colAcoes.setPrefWidth(espaco);

		table.getColumns().addAll(colNome, colCPF, colRG, colCurso, colRA, colSemestre, colAcoes);
		table.setItems(control.getLista());

		table.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<model.AlunoSimples>() {
			@Override
			public void onChanged(Change<? extends model.AlunoSimples> a) {
				if (!a.getList().isEmpty()) {
					control.fromEntity(a.getList().get(0));
				}
			}
		});
	}

	@Override
	public void start() {

		try {
			control = new controller.ctrlAluno();
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
		grid.add(new Label("RA: "), 0, 4);
		grid.add(txtRA, 1, 4);
		grid.add(new Label("Semestre: "), 0, 5);
		grid.add(txtSemestre, 1, 5);

		ligacoes();
		abastecerTableView();

		Button btnLimpar = new Button("Limpar");
		btnLimpar.setOnAction(e -> control.limpar());

		Button btnSalvar = new Button("Salvar");
		btnSalvar.setOnAction(e -> adicionar());

		Button btnPesquisar = new Button("Pesquisar");
		btnPesquisar.setOnAction(e -> pesquisar());

		FlowPane painelBotoes = new FlowPane();
		painelBotoes.getChildren().addAll(btnLimpar, btnSalvar, btnPesquisar);

		grid.add(painelBotoes, 3, 0);
	}

	@Override
	public Pane render() {
		return principal;
	}

}
