package com.tutorial.crud.repository;

import com.tutorial.crud.entity.RegistroParking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RegistroParkingRepository extends JpaRepository<RegistroParking, Integer> {


}
