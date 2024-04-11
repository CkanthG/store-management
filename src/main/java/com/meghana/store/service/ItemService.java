package com.meghana.store.service;

import com.meghana.store.ItemRepository;
import com.meghana.store.converter.DtoConverter;
import com.meghana.store.converter.EntityConverter;
import com.meghana.store.dto.ItemDto;
import com.meghana.store.entity.Item;
import com.meghana.store.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This Item Service has a business logic related to Items.
 */
@Service
@Slf4j
public class ItemService {

    private ItemRepository itemRepository;
    DtoConverter<Item, ItemDto> dtoConverter = entity -> entity.toDto(entity);
    EntityConverter<ItemDto, Item> entityConverter = dto -> dto.toEntity(dto);

    // constructor injection
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * This method will save item inside DB.
     *
     * @param itemDto
     * @return ItemDto
     */
    public ItemDto insertItem(ItemDto itemDto) {
        log.info("ItemService:insertItem executed");
        return convertEntityToDto(
                dtoConverter,
                itemRepository.save(new Item(itemDto.getName(), itemDto.getItemPrice(), itemDto.getItemQuantity()))
        );
    }


    /**
     * This method will update item inside DB.
     *
     * @param itemDto
     * @return Item
     */
    public ItemDto updateItem(ItemDto itemDto, Long itemId) throws EntityNotFoundException {
        log.info("ItemService:updateItem executed");
        itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item with ID " + itemId + " not found in the database"));
        itemDto.setId(itemId.toString());
        return convertEntityToDto(
                dtoConverter,
                itemRepository.save(convertDtoToEntity(entityConverter, itemDto))
        );
    }

    /**
     * This method find an item inside DB by its id.
     * @param id
     * @return Item object
     * @throws EntityNotFoundException
     */
    public ItemDto getItemByItemId(Long id) throws EntityNotFoundException {
        log.info("ItemService:getItemByItemId executed");
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
     * This method is to convert dto to entity
     * @param entityConverter
     * @param itemDto
     * @return item
     */
    public Item convertDtoToEntity(EntityConverter<ItemDto, Item> entityConverter, ItemDto itemDto) {
        return entityConverter.convert(itemDto);
    }

    /**
     * This method find all items in DB.
     * @return list of items
     */
    public List<ItemDto> getAllItems() {
        log.info("ItemService:getAllItems executed");
        List<Item> itemList = itemRepository.findAll();
        final List<ItemDto> finalList = new ArrayList<>();
        itemList.forEach(
          it -> finalList.add(convertEntityToDto(dtoConverter, it))
        );
        log.info("ItemService:getAllItems ended");
        return finalList;
    }

    /**
     * This method is to get all items by its name
     * @param name
     * @return list of items
     */
    public List<ItemDto> getItemsByName(String name) {
        log.info("ItemService:getItemsByName executed");
        List<Item> itemList = itemRepository.findAll();
        final List<ItemDto> finalList = new ArrayList<>();
        itemList.forEach(
                it -> finalList.add(convertEntityToDto(dtoConverter, it))
        );
        if (finalList.isEmpty())
            return List.of();
        return finalList.stream().filter(
                it -> it.getName().equals(name)
        ).collect(Collectors.toList());
    }

    /**
     * This method is to delete the item inside the DB.
     * @param itemId
     * @throws EntityNotFoundException
     */
    public void deleteItem(Long itemId) throws EntityNotFoundException {
        log.info("ItemService:deleteItem executed");
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item with ID " + itemId + " not found in the database"));
        itemRepository.delete(item);
        log.info("ItemService:deleteItem ended");
    }
}
