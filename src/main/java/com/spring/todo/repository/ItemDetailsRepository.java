package com.spring.todo.repository;

import com.spring.todo.entity.ItemDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDetailsRepository extends JpaRepository<ItemDetails,Long> {


}
