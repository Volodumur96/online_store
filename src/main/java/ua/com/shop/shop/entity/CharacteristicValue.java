package ua.com.shop.shop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class CharacteristicValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "_value")
    private String value;

    @ManyToOne
    private UnitOfMeasurement unitOfMeasurement;

    @ManyToOne
    private Characteristic characteristic;

    @ManyToMany
    private List<Product> products = new ArrayList<>();
}
