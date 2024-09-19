package com.meghana.store.controller;

import com.meghana.store.repository.ItemRepository;
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
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
class ItemControllerIT {

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
    void setUp() {
        baseUrl = "http://localhost:" + port + "/items";
        itemRepository.deleteAll();
    }

    @Test
    void createItem() {
        ItemDto itemDto = new ItemDto("1", "saree", 100.0, 1);
        ResponseEntity<ItemDto> response = restTemplate.postForEntity(baseUrl, itemDto, ItemDto.class);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        ItemDto responseBody = response.getBody();
        assert responseBody != null;
        Item item = itemRepository.findById(Long.valueOf(responseBody.getId())).get();
        ItemDto responseItemDto = new Item().toDto(item);
        Assertions.assertEquals(responseItemDto.getItemPrice(), itemDto.getItemPrice());
        Assertions.assertEquals(responseItemDto.getItemQuantity(), itemDto.getItemQuantity());
        Assertions.assertEquals(responseItemDto.getName(), itemDto.getName());
    }

    @Test
    void updateItem() {
        ItemDto itemDto = new ItemDto("1", "saree", 100.0, 1);
        Item savedItem = itemRepository.save(new ItemDto().toEntity(itemDto));
        itemDto.setId(savedItem.getId().toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<ItemDto> itemDtoHttpEntity = new HttpEntity<>(itemDto, headers);
        ResponseEntity<ItemDto> response = restTemplate.exchange(
                baseUrl+"?itemId="+itemDto.getId(),
                HttpMethod.PUT,
                itemDtoHttpEntity,
                ItemDto.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        ItemDto responseBody = response.getBody();
        assert responseBody != null;
        Item item = itemRepository.findById(Long.valueOf(responseBody.getId())).get();
        ItemDto responseItemDto = new Item().toDto(item);
        Assertions.assertEquals(responseItemDto.getItemPrice(), itemDto.getItemPrice());
        Assertions.assertEquals(responseItemDto.getItemQuantity(), itemDto.getItemQuantity());
        Assertions.assertEquals(responseItemDto.getName(), itemDto.getName());
    }

    @Test
    void getItemByItemId() {
        ItemDto itemDto = new ItemDto("1", "saree", 100.0, 1);
        ItemDto insertItem = itemService.insertItem(itemDto);
        ResponseEntity<ItemDto> response = restTemplate.getForEntity(baseUrl + "/" + insertItem.getId(), ItemDto.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(Objects.requireNonNull(response.getBody()), insertItem);
    }

    @Test
    void getItemsByName() {
        ItemDto itemDto = new ItemDto("1", "saree", 100.0, 1);
        itemService.insertItem(itemDto);
        ResponseEntity<List<ItemDto>> response = restTemplate.exchange(
                baseUrl+"/itemName?name="+itemDto.getName(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(Objects.requireNonNull(response.getBody()).size(), 1);
    }

    @Test
    void getItemByItemIdThrowsInternalServerErrorIfItemNotFound() {
        ResponseEntity<ItemDto> response = restTemplate.getForEntity(baseUrl + "/1", ItemDto.class);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void getAllItems() {
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
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody()).hasSize(2);
    }

    @Test
    void deleteItem() {
        ItemDto itemDto = new ItemDto("1", "saree", 100.0, 1);
        Item savedItem = itemRepository.save(new ItemDto().toEntity(itemDto));
        itemDto.setId(savedItem.getId().toString());
        ResponseEntity<Void> response = restTemplate.exchange(
                baseUrl+"/"+itemDto.getId(),
                HttpMethod.DELETE,
                null,
                Void.class);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
