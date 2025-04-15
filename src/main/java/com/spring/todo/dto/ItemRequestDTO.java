package com.spring.todo.dto;


import com.spring.todo.utils.Priority;
import com.spring.todo.utils.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDTO {
    
    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "User Id is required")
    private Long userId;

    private String description;

    @NotNull(message = "Status is required")
    private Status status;

    @NotNull(message = "Priority is required")
    private Priority priority;

}
