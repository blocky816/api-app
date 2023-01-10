package com.tutorial.crud.repository;

import com.tutorial.crud.entity.RetoAcumulable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RetoAcumulableRepository extends JpaRepository<RetoAcumulable, UUID> {
}
