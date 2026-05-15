package base.hotel_management.domain.entities;

import base.hotel_management.domain.enums.FoodCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class FoodType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private FoodCategory category;   // STANDARD / DELUXE / SUITE

    @OneToMany(mappedBy = "foodType")
    private List<Food> foods;
}
