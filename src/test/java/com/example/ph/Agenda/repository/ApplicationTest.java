package com.example.ph.Agenda.repository;

import com.example.ph.Agenda.model.Agenda;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ApplicationTest {

	@Autowired
    AgendaRepository agendaRepository;

    @Test
    public void salvarTest(){
        Agenda agenda = new Agenda();
        agenda.setTitulo("PRATICANDO MEU PRIMEIRO TEST");
        agenda.setDescricao("Até aqui, esta parecendo facil");

        var agendaSalva = agendaRepository.save(agenda);
        System.out.println("Agenda salva "+agendaSalva);
    }

    @Test
    public void atualizarTest(){

        var id = 5L;
        Optional<Agenda> verificarId = agendaRepository.findById(id);

        if (verificarId.isPresent()){
            System.out.println("O ID "+verificarId+" esta presente");
            Agenda agendaParaAtualizar = verificarId.get();
            agendaParaAtualizar.setTitulo("cs2");
            agendaParaAtualizar.setDescricao("Hoje eu vou jogar com BTL");


            var agendaSalva = agendaRepository.save(agendaParaAtualizar);
            System.out.println("agenda atualizado com sucesso");
        }
    }

    @Test
    public void deleteTest(){
        var id = 5L;
        Optional<Agenda> verificarId = agendaRepository.findById(id);

        if (verificarId.isPresent()){
            agendaRepository.deleteById(id);
        }else {
            System.out.println("id nao encontrado");
        }
    }

    @Test
    public void listarTest(){
        //QUANDO FOR PRA PRINTAR O BANCO DE DADOS TODO É USAR metodo '.findAll()'
        List<Agenda> listaAgenda = agendaRepository.findAll();
        listaAgenda.forEach(System.out::println);//forEach faz um FOR da lista printando.
    }

    @Test
    public void countTest(){
        //O PROPRIO REPOSITORY FORNECE UM METODO '.count()'
        System.out.println("Contagem de compromissos: "+ agendaRepository.count());
    }
}
