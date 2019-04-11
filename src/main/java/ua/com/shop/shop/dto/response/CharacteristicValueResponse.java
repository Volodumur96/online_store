package ua.com.shop.shop.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.com.shop.shop.entity.CharacteristicValue;
import ua.com.shop.shop.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor

public class CharacteristicValueResponse {
    private Long id;
    private String value;
    private UnitOfMeasurementResponse unit;
    private String characteristic ;
//    private List<ProductResponse> products;

    public CharacteristicValueResponse(CharacteristicValue characteristicValue) {
        id = characteristicValue.getId();
        value = characteristicValue.getValue();
        unit = new UnitOfMeasurementResponse(characteristicValue.getUnitOfMeasurement());
        characteristic = characteristicValue.getCharacteristic().getName();

    }
}
