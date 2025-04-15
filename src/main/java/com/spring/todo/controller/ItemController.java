package com.spring.todo.controller;


import com.spring.todo.dto.ItemDetailsResponseDTO;
import com.spring.todo.dto.ItemRequestDTO;
import com.spring.todo.dto.ItemResponseDTO;
import com.spring.todo.dto.ItemUpdateDTO;
import com.spring.todo.entity.Item;
import com.spring.todo.service.ItemDetailsService;
import com.spring.todo.service.ItemService;
import com.spring.todo.utils.Priority;
import com.spring.todo.utils.Status;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    private final ItemDetailsService itemDetailsService;
    @PostMapping
    public ResponseEntity<ItemResponseDTO>addItem(@Valid @RequestBody ItemRequestDTO item){

        Item savedItem = itemService.mapAndSave(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.mapToResponse(savedItem));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> getItem(@PathVariable long id){
        Item item = itemService.getItemById(id);
        return ResponseEntity.ok(itemService.mapToResponse(item));
    }

    @GetMapping
    public ResponseEntity<List<ItemResponseDTO>> getItems(){
        List<ItemResponseDTO> items = itemService.getAllItems()
                .stream()
                .map(itemService::mapToResponse)
                .toList();
        return ResponseEntity.ok(items);
    }

    @PutMapping
    public ResponseEntity<ItemResponseDTO> updateItem(@Valid @RequestBody ItemUpdateDTO item){
        return ResponseEntity.ok(itemService.updateItem(item));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteItem(@PathVariable long id){
        itemService.deleteItemById(id);
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ItemResponseDTO>> searchByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(
                itemService.searchByUserId(userId)
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
