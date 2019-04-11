package ua.com.shop.shop.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor

public class CharacteristicValueRequest {
    @NotEmpty @NonNull
    private String value;

    @NonNull
    private Long unitOfMeasurement;

    @NonNull
    private Long characteristic;

    @NonNull @NotEmpty
    private Long[] products;
}
