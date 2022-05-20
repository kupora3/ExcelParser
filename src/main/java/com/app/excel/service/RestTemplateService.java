package com.app.excel.service;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public interface RestTemplateService {

    ResponseEntity<String> sendGet(String exchangeUrl, MultiValueMap<String, String> headers);
}
