package ua.com.shop.shop.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.com.shop.shop.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor

public class ProductResponse {
    private Long id;

    private MakerResponse maker;
    private String countryName;
    private CategoryResponse category;
    private String name;
    private String model;
    private Integer year;
    private String description;
    private Integer price;
    private Integer amount;
    private String imagePath;
    private List<CharacteristicValueResponse> characteristicValueRespons;

    public ProductResponse(Product product) {
        id = product.getId();
        maker = new MakerResponse(product.getMaker());
        countryName = product.getMaker().getCountry().getName();
        category = new CategoryResponse(product.getCategory());
        name = product.getName();
        model = product.getModel();
        year = product.getYear();
        description = product.getDescription();
        price = product.getPrice();
        amount = product.getAmount();
        imagePath = product.getImagePath();
        characteristicValueRespons = product.getCharacteristicsValue().stream().map(CharacteristicValueResponse::new).collect(Collectors.toList());
    }
}
