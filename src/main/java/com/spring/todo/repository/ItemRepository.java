package com.spring.todo.repository;


import com.spring.todo.dto.ItemResponseDTO;
import com.spring.todo.entity.Item;
import com.spring.todo.utils.Priority;
import com.spring.todo.utils.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {

    Page<ItemResponseDTO>getAllItems(Pageable pageable);;
    List<Item> findByTitleContainingIgnoreCase(String title);
    List<Item> findByItemDetails_Status(Status status);
    List<Item> findByItemDetails_Priority(Priority priority);
    Page<Item> findByUserId(Long userId,Pageable pageable);
    List<Item> findAllByOrderByItemDetails_CreatedAtDesc();

}
