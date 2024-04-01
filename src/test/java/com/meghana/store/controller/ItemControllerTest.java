package com.meghana.store.controller;

import com.meghana.store.ItemRepository;
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

import static org.mockito.Mockito.when;

/**
 * Unit Test cases to cover the code
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerTest {

    @Mock
    ItemService itemService;
    @Mock
    ItemRepository itemRepository;

    @Test
    public void getAllItems() {
        List<Item> list = List.of(
                new Item("saree", 100.0, 1)
        );
        when(itemService.getAllItems()).thenReturn(list);
        List<Item> allItems = itemService.getAllItems();
        Assertions.assertEquals(allItems.size(), 1);
    }

    @Test
    public void getItemByItemId() throws EntityNotFoundException {
        ItemDto item = new ItemDto("1", "saree", 100.0, 1);
        when(itemService.getItemByItemId(Long.valueOf(item.getId()))).thenReturn(item);
        ItemDto i = itemService.getItemByItemId(Long.valueOf(item.getId()));
        Assertions.assertEquals(i.getName(), "saree");
    }

    @Test
    public void saveItem() {
        ItemDto item = new ItemDto("1", "saree", 100.0, 1);
        when(itemService.insertItem(item)).thenReturn("success");
        String string = itemService.insertItem(item);
        Assertions.assertEquals(string, "success");
    }

}
