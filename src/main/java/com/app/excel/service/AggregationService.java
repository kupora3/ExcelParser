package com.app.excel.service;

import com.app.excel.model.ResponseDto;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public interface AggregationService {

    Button createPercentButton(TextField myPercent, CheckBox checkBox, TextField exchange, TableView<ResponseDto> tableView);

    TableView<ResponseDto> createTable();

    CheckBox createCheckBox(BorderPane centerBP, TextField exchange);
}
