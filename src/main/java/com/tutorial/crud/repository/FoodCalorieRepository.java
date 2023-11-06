package com.tutorial.crud.repository;

import com.tutorial.crud.entity.FoodCalorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodCalorieRepository extends JpaRepository<FoodCalorie, Integer> {
    List<FoodCalorie> findAllByOrderById();
}
