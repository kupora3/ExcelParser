package com.app.excel.service;

import com.app.excel.model.RequestDto;

import java.util.List;

public interface ExcelReader {
    List<RequestDto> read();
}
