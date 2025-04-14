package com.spring.todo.service;

import com.spring.todo.entity.ItemDetails;
import com.spring.todo.exception.NotFoundException;
import com.spring.todo.repository.ItemDetailsRepository;
import com.spring.todo.utils.Priority;
import com.spring.todo.utils.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemDetailsService {

    private final ItemDetailsRepository itemDetailsRepo;
    public ItemDetails updateStatus(long detailsId, Status status) {
        ItemDetails details = itemDetailsRepo.findById(detailsId)
                .orElseThrow(() -> new NotFoundException("Details Not Found"));
        details.setStatus(status);
        return itemDetailsRepo.save(details);
    }
    public ItemDetails updatePriority(Long id, Priority priority) {
        ItemDetails details = itemDetailsRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("ItemDetails not found"));
        details.setPriority(priority);
        return itemDetailsRepo.save(details);
    }
}
