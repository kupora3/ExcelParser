package root.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import root.exception.ExcelReaderException;
import root.exception.ExchangeServiceException;
import root.model.ErrorDto;

import static root.consts.ErrorCodes.*;

@RestControllerAdvice
@Slf4j
public class ExcelParserExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception error) {
        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        log.error("handle exception message: {}", error.getMessage(), error);
        return new ResponseEntity<>(createErrorDto(error.getMessage(), UNKNOWN_ERROR_CODE), HttpStatus.OK);
    }

    @ExceptionHandler(ExcelReaderException.class)
    public ResponseEntity<Object> handleExcelException(ExcelReaderException error) {
        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        log.error("handle exception message: {}", error.getMessage(), error);
        return new ResponseEntity<>(createErrorDto(error.getMessage(), EXCEL_READER_EXCEPTION_CODE), HttpStatus.OK);
    }

    @ExceptionHandler(ExchangeServiceException.class)
    public ResponseEntity<Object> handleExchangeException(ExchangeServiceException error) {
        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        log.error("handle exception message: {}", error.getMessage(), error);
        return new ResponseEntity<>(createErrorDto(error.getMessage(), EXCHANGE_SERVICE_EXCEPTION_CODE), HttpStatus.OK);
    }

    private ErrorDto createErrorDto(String message, int code) {
        return ErrorDto.builder()
                .code(code)
                .message(message)
                .build();
    }
}
