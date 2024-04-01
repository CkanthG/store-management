package com.meghana.store.controller;

import com.meghana.store.ItemRepository;
import com.meghana.store.dto.ItemDto;
import com.meghana.store.entity.Item;
import com.meghana.store.service.ItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
public class ItemControllerIT {

    @Autowired
    ItemService itemService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ItemRepository itemRepository;

    @LocalServerPort
    private int port;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/items";
        itemRepository.deleteAll();
    }

    @Test
    public void createItem() {
        ItemDto itemDto = new ItemDto("1", "saree", 100.0, 1);
        ResponseEntity<ItemDto> response = restTemplate.postForEntity(baseUrl, itemDto, ItemDto.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        ItemDto responseBody = response.getBody();
        Item item = itemRepository.findById(Long.valueOf(responseBody.getId())).get();
        ItemDto responseItemDto = new Item().toDto(item);
        Assertions.assertEquals(responseItemDto.getItemPrice(), itemDto.getItemPrice());
        Assertions.assertEquals(responseItemDto.getItemQuantity(), itemDto.getItemQuantity());
        Assertions.assertEquals(responseItemDto.getName(), itemDto.getName());
    }

    @Test
    public void getItemByItemId() {
        ItemDto itemDto = new ItemDto("1", "saree", 100.0, 1);
        ItemDto insertItem = itemService.insertItem(itemDto);
        ResponseEntity<ItemDto> response = restTemplate.getForEntity(baseUrl + "/" + insertItem.getId(), ItemDto.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(Objects.requireNonNull(response.getBody()), insertItem);
    }

    @Test
    public void getItemsByName() {
        ItemDto itemDto = new ItemDto("1", "saree", 100.0, 1);
        itemService.insertItem(itemDto);
        ResponseEntity<List<ItemDto>> response = restTemplate.exchange(
                baseUrl+"/itemName?name="+itemDto.getName(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(Objects.requireNonNull(response.getBody()).size(), 1);
    }

    @Test
    public void getItemByItemIdThrowsInternalServerErrorIfItemNotFound() {
        ResponseEntity<ItemDto> response = restTemplate.getForEntity(baseUrl + "/1", ItemDto.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void getAllItems() {
        ItemDto itemDto = new ItemDto("1", "saree", 100.0, 1);
        ItemDto itemDto1 = new ItemDto("1", "saree", 100.0, 1);
        itemService.insertItem(itemDto);
        itemService.insertItem(itemDto1);
        ResponseEntity<List<ItemDto>> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
    }
}
