package com.example.ph.Agenda.service;


import com.example.ph.Agenda.model.Agenda;
import com.example.ph.Agenda.repository.AgendaRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgendaService {
    

    private final AgendaRepository agendaRepository;

    public AgendaService(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }


    public Optional<Agenda> chamarPorId(Long id){
        return agendaRepository.findById(id);

    }

    public List<Agenda> chamaPorTodos(){
        return agendaRepository.findAll();
    }


    public Agenda salvar(Agenda agenda){
        return agendaRepository.save(agenda);
    }

    public void deletarPorId(Long id){
        if (agendaRepository.existsById(id)){
            agendaRepository.deleteById(id);
        }else {
            System.out.println("ID NAO ENCONTRADO !");
        }
    }

    public Optional<Agenda> updatePorId(Long id, Agenda agenda){
        Optional<Agenda> agendaOptional = agendaRepository.findById(id);

        if (agendaOptional.isPresent()){
            Agenda agendaExistente = agendaOptional.get();
            agendaExistente.setTitulo(agenda.getTitulo());
            agendaExistente.setDescricao(agenda.getDescricao());

            agendaRepository.save(agendaExistente);
            return Optional.of(agendaExistente);
        }else {
            return Optional.empty();//se o ID nao for encontrado, entao devolve 'vazio'
        }




    }
}
