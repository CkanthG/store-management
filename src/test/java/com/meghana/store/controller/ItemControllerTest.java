package com.meghana.store.controller;

import com.meghana.store.dto.ItemDto;
import com.meghana.store.entity.Item;
import com.meghana.store.exception.EntityNotFoundException;
import com.meghana.store.service.ItemService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit Test cases to cover the code
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerTest {

    @Mock
    ItemService itemService;

    @Test
    public void getAllItems() {
        when(itemService.getAllItems()).thenReturn(List.of(
                new Item("saree", 100.0, 1)
        ));
        List<Item> allItems = itemService.getAllItems();
        Assertions.assertEquals(allItems.size(), 1);
    }

    @Test
    public void getItemByItemId() throws EntityNotFoundException {
        ItemDto item = new ItemDto("1", "saree", 100.0, 1);
        when(itemService.getItemByItemId(any())).thenReturn(item);
        Assertions.assertEquals(itemService.getItemByItemId(Long.valueOf(item.getId())).getName(), "saree");
    }

    @Test
    public void saveItem() {
        ItemDto item = new ItemDto("1", "saree", 100.0, 1);
        when(itemService.insertItem(any())).thenReturn("saree data inserted successfully");
        Assertions.assertEquals(itemService.insertItem(item), "saree data inserted successfully");
    }

    @Test
    public void getItemByItemIdThrowsEntityNotFoundException() throws EntityNotFoundException {
        when(itemService.getItemByItemId(any())).thenThrow(new EntityNotFoundException("Entity Not Found in DB."));
        Assertions.assertThrows(EntityNotFoundException.class, () -> { itemService.getItemByItemId(1L); });
    }

}
