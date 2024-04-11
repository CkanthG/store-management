package com.meghana.store.entity;

import com.meghana.store.dto.ItemDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public ItemDto toDto(Item item) {
        return new ItemDto(item.id.toString(), item.getName(), item.getItemPrice(), item.getItemQuantity());
    }
}
