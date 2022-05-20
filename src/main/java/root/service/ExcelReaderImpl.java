package root.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import root.exception.ExcelReaderException;
import root.model.RequestDto;
import root.validation.ValidationService;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExcelReaderImpl implements ExcelReader {
    private final ValidationService validationService;

    @Override
    public List<RequestDto> read() {
        try {
            FileInputStream inputStream = new FileInputStream("src/main/resources/MARPA-AGD 16.05.2022.xls");
            Workbook workbook = new HSSFWorkbook(inputStream);
            List<RequestDto> requestDtos = new ArrayList<>();

            for (int i = 1; i < 100; i++) {
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
        } catch (Exception ex) {
            log.error("Ошибка при чтении excel документа ", ex);
            throw new ExcelReaderException(ex.getMessage());
        }
    }
}
