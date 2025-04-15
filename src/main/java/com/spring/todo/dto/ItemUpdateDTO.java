package com.spring.todo.dto;

import com.spring.todo.utils.Priority;
import com.spring.todo.utils.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemUpdateDTO {

        @NotNull(message = "Item id is required")
        private Long id;
        private String title;
        private Long userId;
        private String description;
        private Status status;
        private Priority priority;
}
