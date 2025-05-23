package com.spring.todo.controller;


import com.spring.todo.dto.ItemDetailsResponseDTO;
import com.spring.todo.dto.ItemRequestDTO;
import com.spring.todo.dto.ItemResponseDTO;
import com.spring.todo.dto.ItemUpdateDTO;
import com.spring.todo.entity.Item;
import com.spring.todo.service.ItemDetailsService;
import com.spring.todo.service.ItemService;
import com.spring.todo.utils.JwtUtil;
import com.spring.todo.utils.PageResponse;
import com.spring.todo.utils.Priority;
import com.spring.todo.utils.Status;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
@CrossOrigin(origins = "*")
public class ItemController {

    private final ItemService itemService;

    private final ItemDetailsService itemDetailsService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<ItemResponseDTO>addItem(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody ItemRequestDTO item){
        Long userId = jwtUtil.extractUserId(authHeader.substring(7));
        item.setUserId(userId);

        Item savedItem = itemService.mapAndSave(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.mapToResponse(savedItem));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> getItem(@PathVariable long id){
        Item item = itemService.getItemById(id);
        return ResponseEntity.ok(itemService.mapToResponse(item));
    }
    @GetMapping("/my-items")
    public ResponseEntity<PageResponse<ItemResponseDTO>> getMyItems(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Long userId = jwtUtil.extractUserId(authHeader.substring(7));
        size = Math.min(size, 50);
        Pageable pageable = PageRequest.of(page, size,
                Sort.by("itemDetails.createdAt").descending());
        return ResponseEntity.ok(itemService.getMyItems(userId, pageable));
    }

    @GetMapping
    public PageResponse<ItemResponseDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        size = Math.min(size, 50);
        Pageable pageable = PageRequest.of(page, size,
                Sort.by("itemDetails.createdAt").descending());
        return itemService.getAllItems(pageable);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> updateItem(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody ItemUpdateDTO item) {

        Long userId = jwtUtil.extractUserId(authHeader.substring(7));
        return ResponseEntity.ok(itemService.updateItem(id, item, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable long id) {

        Long userId = jwtUtil.extractUserId(authHeader.substring(7));
        itemService.deleteItemById(id, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemResponseDTO>> searchByTitle(@RequestParam String title){
        return ResponseEntity.ok(
                itemService.searchByTitle(title)
                        .stream()
                        .map(itemService::mapToResponse)
                        .toList()
        );
    }

    @GetMapping("/filter/status")
    public ResponseEntity<List<ItemResponseDTO>> filterByStatus(@RequestParam Status status){
        return ResponseEntity.ok(
                itemService.filterByStatus(status)
                        .stream()
                        .map(itemService::mapToResponse)
                        .toList()
        );
    }

    @GetMapping("/filter/priority")
    public ResponseEntity<List<ItemResponseDTO>> filterByPriority(@RequestParam Priority priority){
        return ResponseEntity.ok(
                itemService.filterByPriority(priority)
                        .stream()
                        .map(itemService::mapToResponse)
                        .toList()
        );
    }
    @GetMapping("/order/createdAt")
    public ResponseEntity<List<ItemResponseDTO>> orderByCreatedAt(){
        return ResponseEntity.ok(
                itemService.orderByCreatedAt()
                        .stream()
                        .map(itemService::mapToResponse)
                        .toList()
        );
    }

    @PutMapping("/details/{id}/status")
    public ResponseEntity<ItemDetailsResponseDTO> updateStatus(@PathVariable long id, @RequestParam Status status){
        return ResponseEntity.ok(itemDetailsService.updateStatus(id,status));

    }

    @PutMapping("/details/{id}/priority")
    public ResponseEntity<ItemDetailsResponseDTO> updatePriority(@PathVariable long id, @RequestParam Priority priority){
        return ResponseEntity.ok(itemDetailsService.updatePriority(id,priority));
    }
}
