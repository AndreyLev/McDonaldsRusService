package ru.rosbank.javaschool.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rosbank.javaschool.web.model.*;

@Data
@NoArgsConstructor
public class ProductDetailsDto {
 private int id;
 private String name;
 private int price;
 private String size;

    public ProductDetailsDto(int id, String name, int price, String size) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.size = "";
    }

    public ProductDetailsDto(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public ProductDetailsDto(int id, String name, int price, int amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.size = Integer.toString(amount);
    }

    public static ProductDetailsDto from(ProductModel model) {

        if (model instanceof BurgerModel) {
            return new ProductDetailsDto(
                    model.getId(),
                    model.getName(),
                    model.getPrice()
            );
        }

        if (model instanceof DrinkModel) {
            return new ProductDetailsDto(
                    model.getId(),
                    model.getName(),
                    model.getPrice(),
                    ((DrinkModel) model).getVolume()
            );
        }

        if (model instanceof PotatoModel) {
            return new ProductDetailsDto(
                    model.getId(),
                    model.getName(),
                    model.getPrice(),
                    ((PotatoModel) model).getSize()
            );
        }

        if (model instanceof StarterModel) {
            return new ProductDetailsDto(
                    model.getId(),
                    model.getName(),
                    model.getPrice(),
                    ((StarterModel) model).getAmount()
            );
        }

        return null;
    }
}
