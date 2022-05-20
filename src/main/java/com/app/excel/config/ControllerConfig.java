package com.app.excel.config;

import com.app.excel.service.*;
import com.app.excel.validation.ValidationService;
import com.app.excel.validation.ValidationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class ControllerConfig {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Bean(value = "controllerServiceImpl")
    @Primary
    public ControllerService controllerServiceImpl() {
        return new ControllerServiceImpl(aggregationService());
    }

    @Bean
    public ValidationService validationService() {
        return new ValidationServiceImpl();
    }

    @Bean
    public AggregationService aggregationService() {
        return new AggregationServiceImpl(validationService(), exchangeService(), excelReader());
    }

    @Bean
    public ExcelReader excelReader() {
        return new ExcelReaderImpl(validationService());
    }

    @Bean
    public RestTemplateService restTemplateService() {
        return new RestTemplateServiceImpl(restTemplate);
    }

    @Bean
    public ExchangeService exchangeService() {
        return new ExchangeServiceImpl(restTemplateService(), objectMapper);
    }
}
