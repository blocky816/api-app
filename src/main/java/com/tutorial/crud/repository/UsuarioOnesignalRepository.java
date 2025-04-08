package com.tutorial.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.crud.entity.UsuarioOnesignal;

@Repository
public interface UsuarioOnesignalRepository extends JpaRepository<UsuarioOnesignal, Long> {
    List<UsuarioOnesignal> findByPushSubscriptionId(String pushSubscriptionToken);

}
