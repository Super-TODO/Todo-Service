package com.spring.todo.service;

import com.spring.todo.entity.Item;
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

    public Item addItem(Item item){
        return itemRepository.save(item);
    }

    public Item updateItem(Item item){
        if (!itemRepository.existsById(item.getId())){
            throw new NotFoundException("Item With ID "+item.getId()+" Not Found!");
        }
        return itemRepository.save(item);
    }
    public Item getItemById(long id){
        return itemRepository.findById(id).orElseThrow(()-> new NotFoundException("No Item Found With "+id +" id"));
    }

    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    public void deleteItemById(long id){
        if (!itemRepository.existsById(id)) {
            throw new NotFoundException("Item with ID " + id + " not found!");
        } itemRepository.deleteById(id);
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
}
