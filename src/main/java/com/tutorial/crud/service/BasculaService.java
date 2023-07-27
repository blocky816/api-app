package com.tutorial.crud.service;

import com.tutorial.crud.repository.BasculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BasculaService {
    @Autowired
    BasculaRepository basculaRepository;
}
