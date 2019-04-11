package ua.com.shop.shop.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.com.shop.shop.entity.Country;


@Getter
@Setter
@NoArgsConstructor

public class CountryResponse {
    private Long id;
    private String name;

    public CountryResponse(Country country) {
        id = country.getId();
        name = country.getName();
    }
}
