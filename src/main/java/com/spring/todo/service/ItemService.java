package com.spring.todo.service;

import com.spring.todo.dto.ItemRequestDTO;
import com.spring.todo.dto.ItemResponseDTO;
import com.spring.todo.dto.ItemUpdateDTO;
import com.spring.todo.entity.Item;
import com.spring.todo.entity.ItemDetails;
import com.spring.todo.exception.NotFoundException;
import com.spring.todo.repository.ItemRepository;
import com.spring.todo.utils.Priority;
import com.spring.todo.utils.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

        public ItemResponseDTO updateItem(ItemUpdateDTO dto, Long userIdFromToken){
            Item existingItem = getItemById(dto.getId());

            if (!existingItem.getUserId().equals(userIdFromToken)) {
                throw new SecurityException("You are not authorized to update this item");
            }

            // ✅ Proceed with update
            if (dto.getTitle() != null) {
                existingItem.setTitle(dto.getTitle());
            }

            ItemDetails details = existingItem.getItemDetails();
            if (details == null) {
                details = new ItemDetails();
                existingItem.setItemDetails(details);
            }

            if (dto.getDescription() != null) {
                details.setDescription(dto.getDescription());
            }

            if (dto.getStatus() != null) {
                details.setStatus(dto.getStatus());
            }

            if (dto.getPriority() != null) {
                details.setPriority(dto.getPriority());
            }

            Item saved = itemRepository.save(existingItem);
            return mapToResponse(saved);
        }

    public Item getItemById(long id){
        return itemRepository.findById(id).orElseThrow(()-> new NotFoundException("No Item Found With "+id +" id"));
    }

    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    public void deleteItemById(long id,Long userIdFromToken) {
        Item item = getItemById(id);
        if (item == null)
            throw new NotFoundException("Item with ID " + id + " not found!");

        if (!item.getUserId().equals(userIdFromToken))
                throw new SecurityException("You are not authorized to delete this item");

            itemRepository.deleteById(id);
        }


            public List<Item> searchByTitle(String title){
        return itemRepository.findByTitleContainingIgnoreCase(title);
    }
    public List<Item> filterByStatus(Status status){
        return itemRepository.findByItemDetails_Status(status);
    }
    public List<Item> filterByPriority(Priority priority){
        return itemRepository.findByItemDetails_Priority(priority);
    }
    public List<Item> searchByUserId(Long userId){
        return itemRepository.findByUserId(userId);
    }
    public List<Item> orderByCreatedAt(){
        return itemRepository.findAllByOrderByItemDetails_CreatedAtDesc();
    }


    // This method maps an Item entity to an ItemResponseDTO.
    public ItemResponseDTO mapToResponse(Item item) {
        ItemResponseDTO dto = new ItemResponseDTO();
        dto.setId(item.getId());
        dto.setTitle(item.getTitle());
        dto.setUserId(item.getUserId());

        if (item.getItemDetails() != null) {
            dto.setDescription(item.getItemDetails().getDescription());
            dto.setStatus(item.getItemDetails().getStatus());
            dto.setPriority(item.getItemDetails().getPriority());
        }

        return dto;
    }


    // This method maps the ItemRequestDTO to an Item entity and saves it to the database.
    public Item mapAndSave(ItemRequestDTO dto) {
        ItemDetails details = new ItemDetails();
        details.setStatus(dto.getStatus());
        details.setPriority(dto.getPriority());
        details.setDescription(dto.getDescription());

        Item item = new Item();
        item.setTitle(dto.getTitle());
        item.setUserId(dto.getUserId());
        item.setItemDetails(details);

        return itemRepository.save(item);
    }
}
