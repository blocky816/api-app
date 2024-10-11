package com.tutorial.crud.repository;

import com.tutorial.crud.entity.Club;
import com.tutorial.crud.entity.Torniquete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TorniqueteRepository extends JpaRepository<Torniquete, Integer> {
    public Torniquete findByClub(Club club);
}
