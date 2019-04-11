package ua.com.shop.shop.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor

public class ProductRequest {
    @NotNull
    private Long makerId;

    @NotNull
    private Long categoryId;

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private String model;

    @NotBlank
    @NotNull
    private Integer year;

    @NotBlank
    @NotNull
    private String description;

    @NotNull
    private Integer price;

    @NotNull
    private Integer amount;

    @NotNull
    private String imagePath;
}
