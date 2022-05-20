package com.app.excel.service;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.app.excel.model.ResponseDto;

@Service
@RequiredArgsConstructor
public class ControllerServiceImpl implements ControllerService {
    private final AggregationService aggregationService;

    @Override
    public BorderPane execute() {
        BorderPane root = new BorderPane();
        BorderPane topBp = new BorderPane();
        BorderPane centerBP = new BorderPane();

        TextField exchange = new TextField();
        exchange.setId("exchange");
        TextField myPercent = new TextField();
        myPercent.setId("myPercent");

        CheckBox checkBox = aggregationService.createCheckBox(centerBP, exchange);

        TableView<ResponseDto> tableView = aggregationService.createTable();

        Button percentButton = aggregationService.createPercentButton(myPercent, checkBox, exchange, tableView);

        topBp.setTop(centerBP);
        topBp.setCenter(myPercent);
        topBp.setRight(percentButton);

        root.setTop(topBp);
        root.setCenter(tableView);
        return root;
    }
}
