package com.spring.todo.entity;


import com.spring.todo.utils.*;
import jakarta.persistence.*;
import jdk.jshell.Snippet;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "item_details")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@RequiredArgsConstructor
public class ItemDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;

    private Status status;

    private  Priority priority;

    private LocalDateTime createdAt;

}
