package ua.com.shop.shop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor

@Entity
public class Maker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToOne
    private Country country;

    @OneToMany(mappedBy = "maker")
    List<Product> products = new LinkedList<>();
}
