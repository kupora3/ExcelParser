package com.app.excel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    private String productName;
    private Double productCount;
    private Integer productPrice;

    public static IntermediateDto toIntDto(RequestDto requestDto, int percent, double exchangeVal) {
        IntermediateDto intermediateDto = new IntermediateDto();
        intermediateDto.setBruttoPrice(requestDto.getProductPrice());
        intermediateDto.setExchangeRate(exchangeVal);
        intermediateDto.setProductCount(requestDto.getProductCount());
        intermediateDto.setProductName(requestDto.getProductName());
        intermediateDto.setMyPercent(percent);
        intermediateDto.setNettoPrice(requestDto.getProductPrice() / 1.23 / exchangeVal);
        return intermediateDto;
    }
}
