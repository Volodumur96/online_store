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

public class CategoryResponse {
    private Long id;
    private String name;
    private List<CharacteristicResponse> characteristicResponseList;


    public CategoryResponse (Category category) {
        id = category.getId();
        name = category.getName();
        characteristicResponseList = category.getCharacteristics().stream().map(CharacteristicResponse::new).collect(Collectors.toList());
    }
}
