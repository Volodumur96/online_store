package ua.com.shop.shop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Maker maker;

    private String model;

    private Integer year;

    @Type(type="text")
    private String description;

    private Integer price;

    @ManyToOne
    private Category category;

    private Integer amount;

    private String imagePath;

    @OneToMany(mappedBy = "product")
    List<OrderForProduct> orderForProducts = new LinkedList<>();

    @ManyToMany(mappedBy = "products")
    private List<CharacteristicValue> characteristicsValue = new ArrayList<>();
}