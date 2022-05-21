package com.app.excel.service;

import com.app.excel.model.ResponseDto;
import com.app.excel.validation.ValidationServiceImpl;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {AggregationServiceImpl.class})
class AggregationServiceImplTest {

    @MockBean
    private ValidationServiceImpl validationService;
    @MockBean
    private ExchangeServiceImpl exchangeService;
    @MockBean
    private ExcelReaderImpl excelReader;
    @Autowired
    private AggregationServiceImpl aggregationService;

    @Test
    void createPercentButton() {
        TextField textField = mock(TextField.class);
        CheckBox checkBox = mock(CheckBox.class);
        TableView<ResponseDto> tableView = createTableView();

        String percentAndExchange = "22";

        when(textField.getText()).thenReturn(percentAndExchange);
        when(validationService.validate(percentAndExchange)).thenReturn(true);
        when(checkBox.isSelected()).thenReturn(false);
        when(exchangeService.getExchange()).thenReturn(22D);

        Button percentButton = aggregationService.createPercentButton(textField, checkBox, textField, tableView);

        assertNotNull(percentButton);
        verify(textField, times(2)).getText();
        verify(validationService, times(1)).validate(percentAndExchange);
        verify(checkBox, times(1)).isSelected();
        verify(exchangeService, times(1)).getExchange();
    }

    @Test
    void createTable() {
    }

    @Test
    void createCheckBox() {
    }

    private TableView<ResponseDto> createTableView() {
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
}