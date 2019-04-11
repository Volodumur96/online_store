package ua.com.shop.shop.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor

public class OrderForProductRequest {

    @NotBlank
    private Long userId;

    @NotBlank
    private Long productId;

    @NotBlank
    private Integer amount;

    private Boolean addToOrder;
}
