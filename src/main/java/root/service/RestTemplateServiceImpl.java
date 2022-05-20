package root.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestTemplateServiceImpl implements RestTemplateService {

    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<String> sendGet(String exchangeUrl, MultiValueMap<String, String> headers) {
        var httpHeaders = new HttpHeaders(headers);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(httpHeaders);
        try {
            return restTemplate.exchange(exchangeUrl, HttpMethod.GET, requestEntity, String.class);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity.ok("");
        }

    }
}
