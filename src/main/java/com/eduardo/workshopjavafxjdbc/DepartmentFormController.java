package com.eduardo.workshopjavafxjdbc;

import com.eduardo.workshopjavafxjdbc.db.DbException;
import com.eduardo.workshopjavafxjdbc.model.entities.Department;
import com.eduardo.workshopjavafxjdbc.model.services.DepartmentService;
import com.eduardo.workshopjavafxjdbc.util.Alerts;
import com.eduardo.workshopjavafxjdbc.util.Constraints;
import com.eduardo.workshopjavafxjdbc.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentFormController implements Initializable {

    private Department entity;

    private DepartmentService service;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private Label labelErrorName;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    @FXML
    public void onBtSaveAction(ActionEvent event) {
        if(entity == null) {
            throw new IllegalStateException("entity is null");
        }
        if(service == null) {
            throw new IllegalStateException("service is null");
        }

        try {
            entity = getFormData();
            service.saveOrUpdate(entity);

            Utils.currentStage(event).close();
        } catch (DbException e) {
            Alerts.showAlert("Error saving object", null, e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    private Department getFormData() {
        Department obj = new Department();

        obj.setId(Utils.tryParseInt(txtId.getText()));
        obj.setName(txtName.getText());

        return obj;
    }

    @FXML
    public void onBtCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }

    public void setDepartment(Department entity) {
        this.entity = entity;
    }

    public void setDepartmentService(DepartmentService service) {
        this.service = service;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 30);
    }

    public void updateFormData() {
        if(entity == null) {
            throw new IllegalStateException("entity is null");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
    }
}
