package view;

import java.sql.SQLException;

import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

public class viewBanca implements Tela {

	private TextField txtData = new TextField();
	private TextField txtOrientadores = new TextField();
	private TextField txtAlunos = new TextField();

	private controller.ctrlBanca control;
	private TableView<model.Banca> table = new TableView<>();

	private BorderPane principal;
	private Executor executor;

	public viewBanca(Executor executor) {
		this.executor = executor;
	}

	public viewBanca() {

	}

	public void ligacoes() {
		Bindings.bindBidirectional(txtData.textProperty(), control.dataProperty());
		Bindings.bindBidirectional(txtAlunos.textProperty(), control.alunosProperty());
		Bindings.bindBidirectional(txtOrientadores.textProperty(), control.orientadoresProperty());
	}

	@SuppressWarnings("unchecked")
	public void abastecerTableView() {

		TableColumn<model.Banca, String> colData = new TableColumn<>("Data");
		colData.setCellValueFactory(new PropertyValueFactory<model.Banca, String>("data"));

		TableColumn<model.Banca, String> colAlunos = new TableColumn<>("Alunos");
		colAlunos.setCellValueFactory(new PropertyValueFactory<model.Banca, String>("alunos"));

		TableColumn<model.Banca, String> colOrientadores = new TableColumn<>("Orientadores");
		colOrientadores.setCellValueFactory(new PropertyValueFactory<model.Banca, String>("orientadores"));

		TableColumn<model.Banca, Void> colAcoes = new TableColumn<>("Ações");
		Callback<TableColumn<model.Banca, Void>, TableCell<model.Banca, Void>> callBack = new Callback<>() {

			@Override
			public TableCell<model.Banca, Void> call(TableColumn<model.Banca, Void> col) {
				TableCell<model.Banca, Void> tCell = new TableCell<>() {

					final Button btnExcluir = new Button("Excluir");
					{
						btnExcluir.setOnAction(e -> {
							model.Banca b = table.getItems().get(getIndex());
							try {
								control.excluir(b);
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

		double espaco = 1920 * 0.75 / 4.0;
		colData.setPrefWidth(espaco);
		colAlunos.setPrefWidth(espaco);
		colOrientadores.setPrefWidth(espaco);
		colAcoes.setPrefWidth(espaco);

		table.getColumns().addAll(colData, colAlunos, colOrientadores, colAcoes);
		table.setItems(control.getLista());

		table.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<model.Banca>() {
			@Override
			public void onChanged(Change<? extends model.Banca> b) {
				if (!b.getList().isEmpty()) {
					control.fromEntity(b.getList().get(0));
				}
			}
		});
	}

	@Override
	public void start() {

		try {
			control = new controller.ctrlBanca();
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("ERRO NO BD");
			System.out.println(e.getStackTrace());
		}

		principal = new BorderPane();

		GridPane grid = new GridPane();
		principal.setTop(grid);
		principal.setCenter(table);

		grid.add(new Label("Data: "), 0, 0);
		grid.add(txtData, 1, 0);

		grid.add(new Label("Alunos: "), 0, 1);
		grid.add(txtAlunos, 1, 1);

		grid.add(new Label("Orientadores: "), 0, 2);
		grid.add(txtOrientadores, 1, 2);

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

		Button btnOrientadores = new Button("Ver Orientadores");
		btnOrientadores.setOnAction(e -> executor.abrir("orientador"));

		grid.add(btnLimpar, 2, 0);
		grid.add(btnSalvar, 3, 0);
		grid.add(btnPesquisar, 4, 0);
		grid.add(btnAlunos, 2, 2);
		grid.add(btnOrientadores, 3, 2);
	}

	@Override
	public Pane render() {
		return principal;
	}

}
