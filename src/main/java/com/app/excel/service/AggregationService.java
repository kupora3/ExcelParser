package com.app.excel.service;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import com.app.excel.model.ResponseDto;

public interface AggregationService {

    Button createPercentButton(TextField myPercent, CheckBox checkBox, TextField exchange, TableView<ResponseDto> tableView);

    TableView<ResponseDto> createTable();

    CheckBox createCheckBox(BorderPane centerBP, TextField exchange);
}
