package com.meghana.store.service;

import com.meghana.store.ItemRepository;
import com.meghana.store.dto.ItemDto;
import com.meghana.store.entity.Item;
import com.meghana.store.exception.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
public class ItemServiceIT {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemService itemService;

    @BeforeEach
    public void cleanUp() {
        itemRepository.deleteAll();
    }

    @Test
    public void insertItem() {
        ItemDto itemDto = new ItemDto("1", "item", 100.0, 1);
        ItemDto responseItemDto = itemService.insertItem(itemDto);
        Assertions.assertEquals(responseItemDto.getName(), "item");
    }

    @Test
    public void updateItem() throws EntityNotFoundException {
        ItemDto itemDto = new ItemDto("1", "item", 100.0, 1);
        ItemDto insertItem = itemService.insertItem(itemDto);
        itemDto.setName("updated item");
        ItemDto responseItemDto = itemService.updateItem(itemDto, Long.valueOf(insertItem.getId()));
        Assertions.assertEquals(responseItemDto.getName(), "updated item");
    }

    @Test
    public void getItemByItemIdEntityNotFoundException() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> itemService.getItemByItemId(1L));
    }

    @Test
    public void getItemByItemId() {
        ItemDto itemDto = new ItemDto("1", "item", 100.0, 1);
        ItemDto responseItemDto = itemService.insertItem(itemDto);
        Assertions.assertEquals(responseItemDto.getName(), "item");
    }

    @Test
    public void getItemsByName() {
        ItemDto itemDto = new ItemDto("1", "item", 100.0, 1);
        itemService.insertItem(itemDto);
        List<ItemDto> itemsByName = itemService.getItemsByName(itemDto.getName());
        Assertions.assertEquals(itemsByName.size(), 1);
    }

    @Test
    public void getItemsByNameGivesEmptyListIfThereIsNoItems() {
        List<ItemDto> itemsByName = itemService.getItemsByName("item");
        Assertions.assertEquals(itemsByName.size(), 0);
    }

    @Test
    public void getAllItems() {
        ItemDto itemDto = new ItemDto("1", "item", 100.0, 1);
        ItemDto itemDto1 = new ItemDto("2", "item1", 200.0, 2);
        itemService.insertItem(itemDto);
        itemService.insertItem(itemDto1);
        List<ItemDto> allItems = itemService.getAllItems();
        Assertions.assertEquals(allItems.size(), 2);
    }

    @Test
    public void deleteItem() throws EntityNotFoundException {
        ItemDto itemDto = new ItemDto("1", "item", 100.0, 1);
        ItemDto item = itemService.insertItem(itemDto);
        itemService.deleteItem(Long.valueOf(item.getId()));
        Assertions.assertThrows(EntityNotFoundException.class, () -> itemService.getItemByItemId(1L));
    }
}
