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

@RestController//ENDPOINT PARA API
@RequestMapping("/agenda")// CAMINHO PARA CHEGAR NA API
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
            return ResponseEntity.ok(response);//ou ResponseEntity.ok().build()
        }else{
            return ResponseEntity.notFound().build();
        }
    }




//    @GetMapping("/{id}")
//    public ResponseEntity<Agenda> chamarId(@PathVariable("id") Long id) {
//        Optional<Agenda> agendaOptional = agendaService.chamarPorId(id);
//        if (agendaOptional.isPresent()) {
//            return ResponseEntity.ok(agendaOptional.get());//ou ResponseEntity.ok().build()
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }



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


//    @GetMapping
//    public ResponseEntity<List<Agenda>> chamarTodos(){
//        List<Agenda> agendas = agendaService.chamaPorTodos();
//        return ResponseEntity.ok(agendas);
//    }





    @PostMapping
    public ResponseEntity<AgendaResponseDTO> salvarAgenda(@RequestBody AgendaRequestDTO request){
        //1. converte o DTO de entrada para a entidade
        Agenda agenda = new Agenda();
        //SETTANDO O 'DTO' DE ENTRADA  = AgendaRequestDTO
        agenda.setTitulo(request.getTitulo());//ou seja, agenda.setTitulo vai setar o request.getTitulo();
        agenda.setDescricao(request.getDescricao()); //ou seja, setDescricao vai setar o request.getDescricao();

        //2. chama o serviço com a Entidade
        Agenda agendaSalva = agendaService.salvar(agenda);

        //3. DEPOIS QUE SETTOU O DTO, agora CONVERTE para o 'DTO' de saida = AgendaResponseDTO
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
        //1. converter o DTO de entrada para a sua Entidade agenda.
        Agenda agenda = new Agenda();
        agenda.setTitulo(request.getTitulo());
        agenda.setDescricao(request.getDescricao());


        Optional<Agenda> agendaOptional = agendaService.updatePorId(id,agenda);

        //3. verificar o resultado da resposta
        if (agendaOptional.isPresent()){
            Agenda agendaAtualizada = agendaOptional.get();
            //se a agenda for encontrada e atualizada, converter a Entidade agenda para o DTO de resposta.
            AgendaResponseDTO response = new AgendaResponseDTO();
            response.setId(agendaAtualizada.getId());
            response.setTitulo(agendaAtualizada.getTitulo());
            response.setDescricao(agendaAtualizada.getDescricao());


            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.notFound().build();
        }

    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Agenda> updateId(@PathVariable Long id,@RequestBody Agenda agenda){
//        Optional<Agenda> agendaOptional = agendaService.updatePorId(id,agenda);
//
//        if (agendaOptional.isPresent()){
//            return ResponseEntity.ok(agendaOptional.get());
//        }else {
//            return ResponseEntity.notFound().build();
//        }
//
//    }


}
