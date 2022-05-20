package com.app.excel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeDto {
    private String table;
    private String currency;
    private String code;
    private List<ExchangeData> rates;
}
