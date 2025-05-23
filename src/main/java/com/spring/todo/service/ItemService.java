package com.spring.todo.service;

import com.spring.todo.dto.ItemRequestDTO;
import com.spring.todo.dto.ItemResponseDTO;
import com.spring.todo.dto.ItemUpdateDTO;
import com.spring.todo.entity.Item;
import com.spring.todo.entity.ItemDetails;
import com.spring.todo.exception.NotFoundException;
import com.spring.todo.repository.ItemRepository;
import com.spring.todo.utils.PageResponse;
import com.spring.todo.utils.Priority;
import com.spring.todo.utils.Status;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    public ItemResponseDTO updateItem(Long id, ItemUpdateDTO dto, Long userIdFromToken) {
        Item existingItem = getItemById(id);

        if (!existingItem.getUserId().equals(userIdFromToken)) {
            throw new SecurityException("You are not authorized to update this item");
        }

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

    public Item getItemById(long id) {
        return itemRepository.findById(id).orElseThrow(() -> new NotFoundException("No Item Found With " + id + " id"));
    }

    public PageResponse<ItemResponseDTO> getAllItems(Pageable pageable) {
        Page<Item> page = itemRepository.findAll(pageable);
        List<ItemResponseDTO> content = page
                .map(this::mapToResponse)
                .getContent();

        return new PageResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public void deleteItemById(long id, Long userIdFromToken) {
        Item item = getItemById(id);
        if (item == null)
            throw new NotFoundException("Item with ID " + id + " not found!");

        if (!item.getUserId().equals(userIdFromToken))
            throw new SecurityException("You are not authorized to delete this item");

        itemRepository.deleteById(id);
    }

    public PageResponse<ItemResponseDTO> getMyItems(Long userId, Pageable pageable) {
        Page<Item> page = itemRepository.findByUserId(userId, pageable);
        List<ItemResponseDTO> content = page
                .map(this::mapToResponse)
                .getContent();

        return new PageResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public List<Item> searchByTitle(String title) {
        return itemRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Item> filterByStatus(Status status) {
        return itemRepository.findByItemDetails_Status(status);
    }

    public List<Item> filterByPriority(Priority priority) {
        return itemRepository.findByItemDetails_Priority(priority);
    }

    public List<Item> orderByCreatedAt() {
        return itemRepository.findAllByOrderByItemDetails_CreatedAtDesc();
    }

    public ItemResponseDTO mapToResponse(Item item) {
        return modelMapper.map(item, ItemResponseDTO.class);
    }

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
