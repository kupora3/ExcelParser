package com.app.excel.service;

import com.app.excel.model.RequestDto;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public interface TemplateService {

    List<RequestDto> polandTemplate(Workbook workbook, int size);

    List<RequestDto> germanyTemplate(Workbook workbook, int size);
}
