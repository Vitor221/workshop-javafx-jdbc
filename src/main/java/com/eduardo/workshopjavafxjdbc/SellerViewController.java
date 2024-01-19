package com.eduardo.workshopjavafxjdbc;

import com.eduardo.workshopjavafxjdbc.db.DbIntegrityException;
import com.eduardo.workshopjavafxjdbc.listeners.DataChangeListener;
import com.eduardo.workshopjavafxjdbc.model.entities.Seller;
import com.eduardo.workshopjavafxjdbc.model.services.SellerService;
import com.eduardo.workshopjavafxjdbc.util.Alerts;
import com.eduardo.workshopjavafxjdbc.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SellerViewController implements Initializable, DataChangeListener {

    private SellerService service;

    @FXML
    private Button btNew;

    @FXML
    private TableView<Seller> tableViewSeller;

    @FXML
    private TableColumn<Seller, Integer> columnId;

    @FXML
    private TableColumn<Seller, String> columnName;

    @FXML
    private TableColumn<Seller, String> columnEmail;

    @FXML
    private TableColumn<Seller, Date> columnBirthDate;

    @FXML
    private TableColumn<Seller, Double> columnBaseSalary;

    @FXML
    private TableColumn<Seller, Seller> tableColumnEDIT;

    @FXML
    private TableColumn<Seller, Seller> tableColumnREMOVE;

    private ObservableList<Seller> obsList;

    @FXML
    public void onBtNewAction(ActionEvent event) {
        Stage parentStage = Utils.currentStage(event);
        Seller entity = new Seller();
        createDialogForm(entity, "gui/Sellerform-view.fxml", parentStage);
    }

    public void setSellerService(SellerService service) {
        this.service = service;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        columnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        Utils.formatTableColumnDate(columnBirthDate, "dd/MM/yyyy");

        columnBaseSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
        Utils.formatTableColumnDouble(columnBaseSalary, 2);

        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewSeller.prefHeightProperty().bind(stage.heightProperty());
    }

    public void updateTableView() {
        if(service == null) {
            throw new IllegalStateException("service is null");
        }

        List<Seller> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);

        tableViewSeller.setItems(obsList);

        initEditButtons();
        initRemoveButtons();
    }

    public void createDialogForm(Seller entity, String absoluteName, Stage parentStage) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
//            Pane pane = loader.load();
//
//            SellerFormController controller = loader.getController();
//            controller.setSeller(entity);
//            controller.setSellerService(new SellerService());
//            controller.subscribeDataChangeListener(this);
//            controller.updateFormData();
//
//            Stage dialogStage = new Stage();
//            dialogStage.setTitle("Enter Seller data");
//            dialogStage.setScene(new Scene(pane));
//            dialogStage.setResizable(false);
//            dialogStage.initOwner(parentStage);
//            dialogStage.initModality(Modality.WINDOW_MODAL);
//            dialogStage.showAndWait();
//
//        } catch (IOException e) {
//            Alerts.showAlert("IO Exception error", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
//        }
    }

    @Override
    public void onDataChanged() {
        updateTableView();
    }

    private void initEditButtons() {
        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>(){
            private final Button button = new Button("edit");

            protected void updateItem(Seller obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(
                        event -> createDialogForm(
                                obj, "gui/Sellerform-view.fxml",Utils.currentStage(event)));
            }
        });

    }

    private void initRemoveButtons() {
        tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
            private final Button button = new Button("remove");
            @Override
            protected void updateItem(Seller obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> removeEntity(obj));
            }
        });
    }

    private void removeEntity(Seller obj) {
        Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");

        if(result.get() == ButtonType.OK) {

            if(service == null) {
                throw new IllegalStateException("service was null");
            }

            try {
                service.delete(obj);
                updateTableView();
            } catch(DbIntegrityException e) {
                Alerts.showAlert("Error removing object", null, e.getMessage(), Alert.AlertType.ERROR);
            }

        }
    }
}
