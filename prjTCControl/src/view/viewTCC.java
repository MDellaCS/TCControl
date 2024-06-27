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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

public class viewTCC implements Tela {

	private TextField txtNome = new TextField();
	private TextField txtAprovado = new TextField();

	private controller.ctrlTCC control;
	private TableView<model.TCC> table = new TableView<>();

	private BorderPane principal;
	private Executor executor;

	public viewTCC(Executor executor) {
		this.executor = executor;
	}

	public viewTCC() {

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
		Bindings.bindBidirectional(txtAprovado.textProperty(), control.aprovadoProperty());
	}

	@SuppressWarnings("unchecked")
	public void abastecerTableView() {

		TableColumn<model.TCC, String> colNome = new TableColumn<>("Nome");
		colNome.setCellValueFactory(new PropertyValueFactory<model.TCC, String>("nomeProjeto"));

		TableColumn<model.TCC, String> colAprovado = new TableColumn<>("Aprovado?");
		colAprovado.setCellValueFactory(new PropertyValueFactory<model.TCC, String>("aprovado"));

		TableColumn<model.TCC, Void> colAcoes = new TableColumn<>("Ações");
		Callback<TableColumn<model.TCC, Void>, TableCell<model.TCC, Void>> callBack = new Callback<>() {

			@Override
			public TableCell<model.TCC, Void> call(TableColumn<model.TCC, Void> col) {
				TableCell<model.TCC, Void> tCell = new TableCell<>() {

					final Button btnExcluir = new Button("Excluir");
					{
						btnExcluir.setOnAction(e -> {
							model.TCC t = table.getItems().get(getIndex());
							try {
								control.excluir(t);
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

		double espaco = 1920 * 0.75 / 3.0;
		colNome.setPrefWidth(espaco);
		colAprovado.setPrefWidth(espaco);
		colAcoes.setPrefWidth(espaco);

		table.getColumns().addAll(colNome, colAprovado, colAcoes);
		table.setItems(control.getLista());

		table.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<model.TCC>() {
			@Override
			public void onChanged(Change<? extends model.TCC> t) {
				if (!t.getList().isEmpty()) {
					control.fromEntity(t.getList().get(0));
				}
			}
		});
	}

	@Override
	public void start() {

		try {
			control = new controller.ctrlTCC();
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
		grid.add(new Label("Aprovado: "), 0, 1);
		grid.add(txtAprovado, 1, 1);

		ligacoes();
		abastecerTableView();

		Button btnLimpar = new Button("Limpar");
		btnLimpar.setOnAction(e -> control.limpar());

		Button btnSalvar = new Button("Salvar");
		btnSalvar.setOnAction(e -> adicionar());

		Button btnPesquisar = new Button("Pesquisar");
		btnPesquisar.setOnAction(e -> pesquisar());

		FlowPane painelBotoes = new FlowPane();
		painelBotoes.getChildren().addAll(btnSalvar, btnPesquisar);

		grid.add(btnLimpar, 3, 0);
		grid.add(painelBotoes, 4, 0);
	}

	@Override
	public Pane render() {
		return principal;
	}

}
