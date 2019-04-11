package ua.com.shop.shop.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor

public class OrderRequest {


    @NonNull @NotEmpty
    private Long[] orderForProductId;


    @NotNull
    private Long userId;
}
