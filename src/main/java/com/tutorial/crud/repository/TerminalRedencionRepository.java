package com.tutorial.crud.repository;

import com.tutorial.crud.entity.TerminalRedencion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TerminalRedencionRepository extends JpaRepository<TerminalRedencion, Integer> {

}
