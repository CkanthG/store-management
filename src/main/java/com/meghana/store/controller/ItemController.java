package com.meghana.store.controller;

import com.meghana.store.dto.ItemDto;
import com.meghana.store.exception.EntityNotFoundException;
import com.meghana.store.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Item Controller is used to do CRUD operations.
 */
@RestController
@RequestMapping("/items")
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Save Item into DB.
     * @param itemDto accept to insert into DB.
     * @return ResponseEntity<String>
     */
    @PostMapping
    public ResponseEntity<ItemDto> createItem(@RequestBody ItemDto itemDto) {
        return new ResponseEntity<>(itemService.insertItem(itemDto), HttpStatus.CREATED);
    }

    /**
     * Get Item By its ID
     * @param itemId
     * @return ResponseEntity<ItemDto>
     * @throws EntityNotFoundException
     */
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDto> getItemByItemId(@PathVariable Long itemId) throws EntityNotFoundException {
        return new ResponseEntity<>(itemService.getItemByItemId(itemId), HttpStatus.OK);
    }

    /**
     * Get All Items
     * @return ResponseEntity<List<ItemDto>>
     */
    @GetMapping
    public ResponseEntity<List<ItemDto>> getAllItems() {
        return new ResponseEntity<>(itemService.getAllItems(), HttpStatus.OK);
    }

    @GetMapping("/itemName")
    public ResponseEntity<List<ItemDto>> getItemsByName(@RequestParam String name) {
        return new ResponseEntity<>(itemService.getItemsByName(name), HttpStatus.OK);
    }
}
