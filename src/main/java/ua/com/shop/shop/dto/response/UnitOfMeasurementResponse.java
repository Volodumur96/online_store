package ua.com.shop.shop.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.com.shop.shop.entity.UnitOfMeasurement;

@Getter
@Setter
@NoArgsConstructor

public class UnitOfMeasurementResponse {
    private Long id;
    private String name;

    public UnitOfMeasurementResponse(UnitOfMeasurement uom) {
        id = uom.getId();
        name = uom.getName();
    }
}
