package com.app.excel.service;

import com.app.excel.model.RequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import com.app.excel.exception.ExcelReaderException;
import com.app.excel.validation.ValidationService;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static com.app.excel.consts.ExcelFieldName.ARTYKUL;
import static java.util.Objects.nonNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExcelReaderImpl implements ExcelReader, TemplateService {
    private static final String FILE_EXTENSION = "xls";
    private static final Integer XSSF_MISMATCH = 37;
    private final ValidationService validationService;

    @Override
    public List<RequestDto> read() {
        try {
            String fileName = findFile();
            String fileExtension = getFileExtension(fileName);
            FileInputStream inputStream = new FileInputStream(fileName);
            Workbook workbook = fileExtension.equals(FILE_EXTENSION) ? new HSSFWorkbook(inputStream)
                    : new XSSFWorkbook(inputStream);
            String fieldName = workbook.getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
            int size = workbook.getSheetAt(0).getPhysicalNumberOfRows();

            List<RequestDto> requestDtos = fieldName.equals(ARTYKUL) ? polandTemplate(workbook, size)
                    : germanyTemplate(workbook, size);

            workbook.close();
            return requestDtos;
        } catch (Exception ex) {
            log.error("Ошибка при чтении excel документа ", ex);
            throw new ExcelReaderException(ex.getMessage());
        }
    }

    @Override
    public List<RequestDto> polandTemplate(Workbook workbook, int size) {
        List<RequestDto> requestDtos = new ArrayList<>();
        for (int i = 1; i < size; i++) {
            String productName = workbook.getSheetAt(0).getRow(i).getCell(0).getStringCellValue();
            Double productCount = workbook.getSheetAt(0).getRow(i).getCell(1).getNumericCellValue();
            String productPrice = workbook.getSheetAt(0).getRow(i).getCell(4).getStringCellValue();
            String[] split = productPrice.split(" ");
            String priceValue = split[0];
            RequestDto requestDto = new RequestDto();
            requestDto.setProductName(productName);
            requestDto.setProductCount(productCount);
            requestDto.setProductPrice(validationService.validatePriceValue(priceValue));
            requestDtos.add(requestDto);
        }
        return requestDtos;
    }

    @Override
    public List<RequestDto> germanyTemplate(Workbook workbook, int size) {
        List<RequestDto> requestDtos = new ArrayList<>();
        for (int i = 1; i < size - XSSF_MISMATCH; i++) {
            String productName = workbook.getSheetAt(0).getRow(i).getCell(0).getStringCellValue();
            Double productCount = workbook.getSheetAt(0).getRow(i).getCell(1).getNumericCellValue();
            double productPrice = workbook.getSheetAt(0).getRow(i).getCell(2).getNumericCellValue();
            RequestDto requestDto = new RequestDto();
            requestDto.setProductName(productName);
            requestDto.setProductCount(productCount);
            requestDto.setProductPrice((int) productPrice);
            requestDtos.add(requestDto);
        }
        return requestDtos;
    }

    private String findFile() {
        File dir = new File("src/main/resources");
        File[] list = dir.listFiles(new Filter("xls,xlsx"));
        try {
            return nonNull(list[0]) ? list[0].getAbsolutePath() : "";
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new ExcelReaderException(ex.getMessage());
        }
    }

    private String getFileExtension(String fileName) {
        String[] extension = fileName.split("\\.");
        return extension[extension.length - 1];
    }
}
