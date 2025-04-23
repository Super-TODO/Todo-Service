package com.spring.todo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private Long userId;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "item_details_id", referencedColumnName = "id")
    private ItemDetails itemDetails;

    public Item(String title, long userId, ItemDetails itemDetails) {
        this.title = title;
        this.userId = userId;
        this.itemDetails = itemDetails;
    }
}
