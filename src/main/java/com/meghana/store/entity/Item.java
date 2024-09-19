package com.meghana.store.entity;

import com.meghana.store.dto.ItemDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private double itemPrice;
    private int itemQuantity;

    public ItemDto toDto(Item item) {
        return new ItemDto(item.id.toString(), item.getName(), item.getItemPrice(), item.getItemQuantity());
    }
}
