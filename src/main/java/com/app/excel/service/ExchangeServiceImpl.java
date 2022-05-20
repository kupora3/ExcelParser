package com.app.excel.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.app.excel.exception.ExchangeServiceException;
import com.app.excel.model.ExchangeDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeServiceImpl implements ExchangeService {
    private static final String REQ_URL = "http://api.nbp.pl/api/exchangerates/rates/A/USD";

    private final RestTemplateService restTemplateService;
    private final ObjectMapper mapper;

    @Override
    public double getExchange() {
        ResponseEntity<String> responseEntity = restTemplateService.sendGet(REQ_URL, new HttpHeaders());
        String exchangeValue = responseEntity.getBody();
        try {
            ExchangeDto exchangeDto = mapper.readValue(exchangeValue, ExchangeDto.class);
            return exchangeDto.getRates().get(0).getMid();
        } catch (Exception ex) {
            log.error("Ошибка при получении курса валют", ex);
            throw new ExchangeServiceException(ex.getMessage());
        }
    }
}
