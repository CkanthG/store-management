package com.meghana.store.entity;

import com.meghana.store.dto.ItemDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private double itemPrice;
    private int itemQuantity;

    public Item(String name, double itemPrice, int itemQuantity) {
        this.name = name;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
    }

    public Item() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public ItemDto toDto(Item item) {
        return new ItemDto(item.id.toString(), item.getName(), item.getItemPrice(), item.getItemQuantity());
    }
}
