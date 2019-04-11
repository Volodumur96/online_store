package ua.com.shop.shop.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.com.shop.shop.entity.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor

public class OrderResponse {
    private Long id;

    private LocalDate date;

    private List<OrderForProductResponse> orderForProductResponse;

    private String userLogin;
    private Long userId;

    public OrderResponse(Order order) {
        id = order.getId();
        date = order.getDate();
        userId = order.getUser().getId();
        userLogin = order.getUser().getLogin();
        orderForProductResponse = order.getOrderForProducts().stream().map(OrderForProductResponse::new).collect(Collectors.toList());
    }
}
