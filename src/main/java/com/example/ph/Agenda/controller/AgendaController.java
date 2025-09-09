package com.example.ph.Agenda.controller;
import com.example.ph.Agenda.dto.AgendaRequestDTO;
import com.example.ph.Agenda.dto.AgendaResponseDTO;
import com.example.ph.Agenda.model.Agenda;
import com.example.ph.Agenda.service.AgendaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/agenda")
public class AgendaController {

    public final AgendaService agendaService;


    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendaResponseDTO> chamarId(@PathVariable("id") Long id) {

        Optional<Agenda> agendaEncontrada = agendaService.chamarPorId(id);

        if (agendaEncontrada.isPresent()) {

            Agenda agenda = agendaEncontrada.get();

            AgendaResponseDTO response = new AgendaResponseDTO(
                    agenda.getId(),
                    agenda.getTitulo(),
                    agenda.getDescricao()
            );
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<AgendaResponseDTO>> chamarTodos(){
        List<Agenda> agendas = agendaService.chamaPorTodos();

        List<AgendaResponseDTO> response =  agendas.stream().map(agenda -> new AgendaResponseDTO(
                agenda.getId(),
                agenda.getTitulo(),
                agenda.getDescricao()
        )).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<AgendaResponseDTO> salvarAgenda(@RequestBody AgendaRequestDTO request){
        
        Agenda agenda = new Agenda();
        agenda.setTitulo(request.getTitulo());
        agenda.setDescricao(request.getDescricao());
        Agenda agendaSalva = agendaService.salvar(agenda);

        AgendaResponseDTO response = new AgendaResponseDTO();
        response.setId(agenda.getId());
        response.setTitulo(agenda.getTitulo());
        response.setDescricao(agenda.getDescricao());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Agenda> deletarId(@PathVariable Long id){
        agendaService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<AgendaResponseDTO> updateId(@PathVariable Long id,@RequestBody AgendaRequestDTO request){
        Agenda agenda = new Agenda();
        agenda.setTitulo(request.getTitulo());
        agenda.setDescricao(request.getDescricao());

        Optional<Agenda> agendaOptional = agendaService.updatePorId(id,agenda);

        if (agendaOptional.isPresent()){
            Agenda agendaAtualizada = agendaOptional.get();
            
            AgendaResponseDTO response = new AgendaResponseDTO();
            response.setId(agendaAtualizada.getId());
            response.setTitulo(agendaAtualizada.getTitulo());
            response.setDescricao(agendaAtualizada.getDescricao());


            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.notFound().build();
        }

    }

}
