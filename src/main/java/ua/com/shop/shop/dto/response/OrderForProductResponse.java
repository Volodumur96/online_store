package ua.com.shop.shop.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.com.shop.shop.entity.OrderForProduct;

@Getter
@Setter
@NoArgsConstructor

public class OrderForProductResponse {
    private Long id;
    private ProductResponse product;
    private Integer amount;
    private Boolean addToOrder;

    public OrderForProductResponse(OrderForProduct orderForProduct) {
        id = orderForProduct.getId();
        product = new ProductResponse(orderForProduct.getProduct());
        amount = orderForProduct.getAmount();
        addToOrder = orderForProduct.getAddToOrder();
    }
}
