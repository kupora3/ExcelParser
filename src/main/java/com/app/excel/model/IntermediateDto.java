package com.app.excel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntermediateDto {
    private String productName;
    private Double productCount;
    private Integer bruttoPrice;
    private Double nettoPrice;
    private Double exchangeRate;
    private Integer myPercent;

    public static ResponseDto toRespDto(IntermediateDto intermediateDto) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setProductName(intermediateDto.getProductName());
        responseDto.setProductCount(intermediateDto.getProductCount());
        responseDto.setExchangeValue(intermediateDto.getExchangeRate());
        responseDto.setProductPrice(intermediateDto.getNettoPrice() +
                (intermediateDto.getNettoPrice() * intermediateDto.getMyPercent() / 100)
                );
        return responseDto;
    }
}
