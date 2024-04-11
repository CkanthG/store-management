package com.meghana.store.dto;

import com.meghana.store.entity.Item;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * This Item Dto is used for accept input from end user.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ItemDto {
    private String id;
    @NotBlank(message = "Item name shouldn't be NULL or EMPTY")
    private String name;
    @Min(value = 100, message = "Item price can't be less than 100")
    @Max(value = 500000, message = "Item price can't be greater than 500000")
    private double itemPrice;
    @Min(value = 1, message = "Item quantity is not defined !")
    private int itemQuantity;

    public Item toEntity(ItemDto itemDto) {
        return new Item(Long.valueOf(itemDto.getId()), itemDto.getName(), itemDto.getItemPrice(), itemDto.getItemQuantity());
    }
}
