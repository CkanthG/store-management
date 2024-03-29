package com.meghana.store.dto;

import java.util.Objects;

/**
 * This Item Dto is used for accept input from end user.
 */
public class ItemDto {
    private String id;
    private String name;
    private double itemPrice;
    private int itemQuantity;

    public ItemDto(String id, String name, double itemPrice, int itemQuantity) {
        this.id = id;
        this.name = name;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ItemDto itemDto = (ItemDto) object;
        return Double.compare(itemPrice, itemDto.itemPrice) == 0 && itemQuantity == itemDto.itemQuantity && Objects.equals(id, itemDto.id) && Objects.equals(name, itemDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, itemPrice, itemQuantity);
    }
}
