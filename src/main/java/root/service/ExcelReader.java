package root.service;

import root.model.RequestDto;

import java.util.List;

public interface ExcelReader {
    List<RequestDto> read();
}
