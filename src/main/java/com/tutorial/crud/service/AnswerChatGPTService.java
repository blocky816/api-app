package com.tutorial.crud.service;

import com.tutorial.crud.entity.AnswerChatGPT;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.ClienteBascula;
import com.tutorial.crud.entity.FormularioRespuesta;
import com.tutorial.crud.repository.AnswerChatGPTRepository;
import com.tutorial.crud.repository.FormularioRespuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AnswerChatGPTService {
    @Autowired
    AnswerChatGPTRepository answerChatGPTRepository;
    @Autowired
    ClienteBasculaService clienteBasculaService;
    @Autowired
    FormularioRespuestaRepository formularioRespuestaRepository;
    @Autowired
    ClienteService clienteService;

    public AnswerChatGPT save(AnswerChatGPT answerChatGPT) {
        return answerChatGPTRepository.save(answerChatGPT);
    }

    public List<AnswerChatGPT> getDietInCurrentMonth(int customerID, LocalDateTime startDate, LocalDateTime endDate){
        Cliente customer = clienteService.findById(customerID);
        ClienteBascula clienteBascula = clienteBasculaService.getUltimoPesaje(customer.getIdCliente());
        if (clienteBascula == null) throw new RuntimeException("Cliente no tiene pesajes");
        List<FormularioRespuesta> formularioRespuestaList = formularioRespuestaRepository.findLastAnswersFormByCustomer(customer.getIdCliente(), 4);
        if (formularioRespuestaList.isEmpty() || formularioRespuestaList == null) throw new RuntimeException("Formulario vacio");
        List<AnswerChatGPT> answerChatGPTList = answerChatGPTRepository.findByCustomerAndCreatedAtBetweenOrderByCreatedAtDesc(customer, startDate, endDate);
        if(answerChatGPTList.isEmpty()) throw new RuntimeException("No hay dietas en el mes");
        return  answerChatGPTList;
    }
}
