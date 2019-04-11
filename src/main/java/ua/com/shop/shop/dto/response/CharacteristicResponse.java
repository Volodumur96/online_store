package ua.com.shop.shop.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.com.shop.shop.entity.Category;
import ua.com.shop.shop.entity.Characteristic;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor

public class CharacteristicResponse {
    private Long id;

    private String name;

    private List<CharacteristicValueResponse>  characteristicValueResponse;


    public CharacteristicResponse(Characteristic characteristic) {
        id = characteristic.getId();
        name = characteristic.getName();
        characteristicValueResponse = characteristic.getCharacteristicsValue().stream().map(CharacteristicValueResponse::new).collect(Collectors.toList());
    }
}
