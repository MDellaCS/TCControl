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

public class viewProposta implements Tela {

	private TextField txtAlunos = new TextField();
	private TextField txtOrientador = new TextField();
	private TextField txtTema = new TextField();
	private TextField txtTipo = new TextField();

	private controller.ctrlProposta control;
	private TableView<model.propostaTCC> table = new TableView<>();

	private BorderPane principal;
	private Executor executor;

	public viewProposta(Executor executor) {
		this.executor = executor;
	}

	public viewProposta() {

	}

	public void ligacoes() {
		Bindings.bindBidirectional(txtAlunos.textProperty(), control.alunosProperty());
		Bindings.bindBidirectional(txtOrientador.textProperty(), control.orientadorProperty());
		Bindings.bindBidirectional(txtTema.textProperty(), control.temaProperty());
		Bindings.bindBidirectional(txtTipo.textProperty(), control.tipoProperty());
	}

	@SuppressWarnings("unchecked")
	public void abastecerTableView() {

		TableColumn<model.propostaTCC, String> colAlunos = new TableColumn<>("Alunos");
		colAlunos.setCellValueFactory(new PropertyValueFactory<model.propostaTCC, String>("alunos"));

		TableColumn<model.propostaTCC, String> colOrientador = new TableColumn<>("Orientador");
		colOrientador.setCellValueFactory(new PropertyValueFactory<model.propostaTCC, String>("orientador"));

		TableColumn<model.propostaTCC, String> colTema = new TableColumn<>("Tema");
		colTema.setCellValueFactory(new PropertyValueFactory<model.propostaTCC, String>("tema"));

		TableColumn<model.propostaTCC, String> colTipo = new TableColumn<>("Tipo");
		colTipo.setCellValueFactory(new PropertyValueFactory<model.propostaTCC, String>("tipo"));

		TableColumn<model.propostaTCC, Void> colAcoes = new TableColumn<>("Ações");
		Callback<TableColumn<model.propostaTCC, Void>, TableCell<model.propostaTCC, Void>> callBack = new Callback<>() {

			@Override
			public TableCell<model.propostaTCC, Void> call(TableColumn<model.propostaTCC, Void> col) {
				TableCell<model.propostaTCC, Void> tCell = new TableCell<>() {

					final Button btnExcluir = new Button("Excluir");
					{
						btnExcluir.setOnAction(e -> {
							model.propostaTCC p = table.getItems().get(getIndex());
							try {
								control.excluir(p);
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

		double espaco = 1920 * 0.75 / 5.0;
		colAlunos.setPrefWidth(espaco);
		colOrientador.setPrefWidth(espaco);
		colTema.setPrefWidth(espaco);
		colTipo.setPrefWidth(espaco);
		colAcoes.setPrefWidth(espaco);

		table.getColumns().addAll(colAlunos, colOrientador, colTema, colTipo, colAcoes);
		table.setItems(control.getLista());

		table.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<model.propostaTCC>() {
			@Override
			public void onChanged(Change<? extends model.propostaTCC> p) {
				if (!p.getList().isEmpty()) {
					control.fromEntity(p.getList().get(0));
				}
			}
		});
	}

	@Override
	public void start() {

		try {
			control = new controller.ctrlProposta();
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("ERRO NO BD");
			System.out.println(e.getStackTrace());
		}

		principal = new BorderPane();

		GridPane grid = new GridPane();
		principal.setTop(grid);
		principal.setCenter(table);

		grid.add(new Label("Alunos: "), 0, 0);
		grid.add(txtAlunos, 1, 0);

		grid.add(new Label("Orientador: "), 0, 1);
		grid.add(txtOrientador, 1, 1);

		grid.add(new Label("Tema: "), 0, 2);
		grid.add(txtTema, 1, 2);
		
		grid.add(new Label("Tipo: "), 0, 3);
		grid.add(txtTipo, 1, 3);

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
		grid.add(btnAlunos, 2, 3);
		grid.add(btnOrientadores, 3, 3);
	}

	@Override
	public Pane render() {
		return principal;
	}

}
