package ua.com.shop.shop.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.com.shop.shop.entity.Maker;

@Getter
@Setter
@NoArgsConstructor

public class MakerResponse {
    private Long id;

    private String name;

    private CountryResponse country;


    public MakerResponse(Maker maker) {
        id = maker.getId();
        name = maker.getName();
        country = new CountryResponse(maker.getCountry());

    }

}