package base.hotel_management.app.controllers.food;

import base.hotel_management.app.requests.food.FoodRequest;
import base.hotel_management.app.responses.food.FoodResponse;
import base.hotel_management.domain.enums.FoodCategory;
import base.hotel_management.domain.services.FoodService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@Tag(name = "Manage Food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    // Thêm món
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public FoodResponse addFood(@RequestBody FoodRequest request) {
        return foodService.addFood(request);
    }

    // Xóa món
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public void deleteFood(@PathVariable int id) {
        foodService.deleteFood(id);
    }

    // Tăng số lượng tồn kho
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @PutMapping("/{id}/increase")
    public FoodResponse increaseQuantity(
            @PathVariable int id,
            @RequestParam int amount
    ) {
        return foodService.increaseQuantity(id, amount);
    }

    // Danh sách món theo category
    @GetMapping("/category/{category}")
    public List<FoodResponse> getByCategory(
            @PathVariable FoodCategory category
    ) {
        return foodService.getFoodsByCategory(category);
    }

    //Sua gia tien
    @PutMapping("/{id}/price")
    public FoodResponse updatePrice(@PathVariable int id,
                                    @RequestParam double price) {
        return foodService.updatePrice(id, price);
    }
}