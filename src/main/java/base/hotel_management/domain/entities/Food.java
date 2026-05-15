package base.hotel_management.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String foodName;
    private Integer number;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "food_type_id")
    private FoodType foodType;

}
