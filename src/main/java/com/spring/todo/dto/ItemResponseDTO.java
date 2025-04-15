package com.spring.todo.dto;

import com.spring.todo.utils.Priority;
import com.spring.todo.utils.Status;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDTO {
    private long id;
    private String title;
    private long userId;
    private String description;
    private Status status;
    private Priority priority;
}
