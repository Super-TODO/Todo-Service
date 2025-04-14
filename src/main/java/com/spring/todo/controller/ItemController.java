package com.spring.todo.controller;


import com.spring.todo.entity.Item;
import com.spring.todo.entity.ItemDetails;
import com.spring.todo.service.ItemDetailsService;
import com.spring.todo.service.ItemService;
import com.spring.todo.utils.Priority;
import com.spring.todo.utils.Status;
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
    public ResponseEntity<Item>addItem(@RequestBody Item item){
        Item savedItem = itemService.addItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable long id){
        return ResponseEntity.ok(itemService.getItemById(id));
    }
    @GetMapping
    public ResponseEntity<List<Item>>getItems(){
        return ResponseEntity.ok(itemService.getAllItems());
    }
   @PutMapping
    public ResponseEntity<Item>updateItem(@RequestBody Item item){
        return ResponseEntity.ok(itemService.updateItem(item));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteItem(@PathVariable long id){
        itemService.deleteItemById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("search")
    public ResponseEntity<List<Item>> searchByTitle(@RequestParam String title){
        return ResponseEntity.ok(itemService.searchByTitle(title));
    }
    @GetMapping("/filter/status")
    public ResponseEntity<List<Item>> filterByStatus(@RequestParam Status status){
        return ResponseEntity.ok(itemService.filterByStatus(status));
    }
    @GetMapping("/filter/priority")
    public ResponseEntity<List<Item>> filterByPriority(@RequestParam Priority priority){
        return ResponseEntity.ok(itemService.filterByPriority(priority));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Item>> searchByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(itemService.searchByUserId(userId));
    }

    @GetMapping("/order/createdAt")
    public ResponseEntity<List<Item>> orderByCreatedAt(){
        return ResponseEntity.ok(itemService.orderByCreatedAt());
    }

    @PutMapping("/details/{id}/status")
    public ResponseEntity<ItemDetails> updateStatus(@PathVariable long id, @RequestParam Status status){
        return ResponseEntity.ok(itemDetailsService.updateStatus(id,status));
    }

    @PutMapping("/details/{id}/priority")
    public ResponseEntity<ItemDetails> updatePriority(@PathVariable long id, @RequestParam Priority priority){
        return ResponseEntity.ok(itemDetailsService.updatePriority(id,priority));
    }
}
