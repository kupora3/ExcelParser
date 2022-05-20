package com.app.excel.service;

import com.app.excel.model.IntermediateDto;
import com.app.excel.model.RequestDto;
import com.app.excel.model.ResponseDto;
import com.app.excel.validation.ValidationService;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AggregationServiceImpl implements AggregationService {

    private final ValidationService validationService;
    private final ExchangeService exchangeService;
    private final ExcelReader excelReader;

    @Override
    public Button createPercentButton(TextField myPercent,
                                      CheckBox checkBox,
                                      TextField exchange,
                                      TableView<ResponseDto> tableView) {
        Button percentButton = new Button("Click");
        percentButton.setOnAction(event -> {
            String inputPercent = myPercent.getText();
            if (validationService.validate(inputPercent)) {
                double exchangeVal = !checkBox.isSelected() ? exchangeService.getExchange()
                        : Double.parseDouble(exchange.getText());
                List<ResponseDto> responseDtos = aggregateInputFields(Integer.parseInt(inputPercent), exchangeVal);
                tableView.getItems().removeIf(p -> true);
                tableView.getItems().addAll(responseDtos);
            } else {
                System.out.println("Некорректный ввод данных");
            }
        });
        return percentButton;
    }

    @Override
    public TableView<ResponseDto> createTable() {
        TableView<ResponseDto> tableView = new TableView<>();
        TableColumn<ResponseDto, String> productName = new TableColumn<>("Имя продукта");
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        TableColumn<ResponseDto, String> productCount = new TableColumn<>("Количество продукта");
        productCount.setCellValueFactory(new PropertyValueFactory<>("productCount"));
        TableColumn<ResponseDto, String> productPrice = new TableColumn<>("Цена");
        productPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        TableColumn<ResponseDto, String> exchangeColumn = new TableColumn<>("Курс");
        exchangeColumn.setCellValueFactory(new PropertyValueFactory<>("exchangeValue"));
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setOnKeyPressed(e -> {
            if (e.isControlDown() && e.getCode() == KeyCode.C) {
                ObservableList<ResponseDto> selectedItems = tableView.getSelectionModel().getSelectedItems();
                String copyString = selectedItems
                        .stream()
                        .map(rD -> rD.getProductName() + " "
                                + rD.getProductCount() + " "
                                + rD.getProductPrice() + " "
                                + rD.getExchangeValue())
                        .collect(Collectors.joining(System.lineSeparator()));

                StringSelection stringSelection = new StringSelection(copyString);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
            }
        });
        tableView.getColumns().add(productName);
        tableView.getColumns().add(productCount);
        tableView.getColumns().add(productPrice);
        tableView.getColumns().add(exchangeColumn);
        return tableView;
    }

    @Override
    public CheckBox createCheckBox(BorderPane centerBP, TextField exchange) {
        CheckBox checkBox = new CheckBox("Курс доллара(Если нет интернета)");
        centerBP.setLeft(checkBox);
        checkBox.setOnAction(events -> {
            if (!checkBox.isSelected()) {
                centerBP.setCenter(null);
            } else {
                centerBP.setCenter(exchange);
            }
        });
        return checkBox;
    }

    private List<IntermediateDto> getIntermediateDtos(List<RequestDto> requestDtos, int percent, double exchangeVal) {

        return requestDtos.stream()
                .map(i -> RequestDto.toIntDto(i, percent, exchangeVal))
                .collect(Collectors.toList());
    }

    private List<ResponseDto> aggregateInputFields(int percent, double exchangeVal) {

        List<RequestDto> excelInputData = excelReader.read();
        List<IntermediateDto> intermediateDtos = getIntermediateDtos(excelInputData, percent, exchangeVal);
        return intermediateDtos.stream()
                .map(IntermediateDto::toRespDto)
                .collect(Collectors.toList());
    }
}
