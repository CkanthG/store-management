package com.meghana.store.service;

import com.meghana.store.ItemRepository;
import com.meghana.store.converter.DtoConverter;
import com.meghana.store.dto.ItemDto;
import com.meghana.store.entity.Item;
import com.meghana.store.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This Item Service has a business logic related to Items.
 */
@Service
public class ItemService {

    private ItemRepository itemRepository;
    DtoConverter<Item, ItemDto> dtoConverter = entity -> entity.toDto(entity);

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * This method will save item inside DB.
     * @param itemDto
     * @return String as a response
     */
    public String insertItem(ItemDto itemDto) {
        itemRepository.save(new Item(itemDto.getName(),itemDto.getItemPrice(), itemDto.getItemQuantity()));
        return itemDto.getName() + " inserted successfully";
    }

    /**
     * This method find an item inside DB by its id.
     * @param id
     * @return Item object
     * @throws EntityNotFoundException
     */
    public ItemDto getItemByItemId(Long id) throws EntityNotFoundException {
        return convertEntityToDto(
                dtoConverter,
                itemRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(id + " Entity Not Found in DB.")
                )
        );
    }

    /**
     * This method is to convertEntityToDto entity to dto using custom functional interface.
     * @param dtoConverter
     * @param item
     * @return ItemDto
     */
    public ItemDto convertEntityToDto(DtoConverter<Item, ItemDto> dtoConverter, Item item) {
        return dtoConverter.convert(item);
    }

    /**
     * This method find all items in DB.
     * @return
     */
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
}
