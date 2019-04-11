package ua.com.shop.shop.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class ProductFilterRequest {

    private Integer priceFrom;
    private Integer priceTo;
    private Integer yearFrom;
    private Integer yearTo;
    private String name;
    private String model;
    List<Long> makersId;
    List<Long> categoriesId;

    private PaginationRequest paginationRequest;
}
