package com.example.ph.Agenda;

import com.example.ph.Agenda.model.Agenda;
import com.example.ph.Agenda.repository.AgendaRepository;
import com.example.ph.Agenda.service.AgendaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgendaServiceTest {

    @Mock
    private AgendaRepository agendaRepository;

    @InjectMocks
    private AgendaService agendaService;

    private Agenda agendaDeTeste;

   


    @BeforeEach
    void setUp() {
        agendaDeTeste = new Agenda();
        agendaDeTeste.setId(1L);
        agendaDeTeste.setTitulo("Reunião de Equipe");
        agendaDeTeste.setDescricao("Discutir novas tarefas.");
    }

    
    @Test
    void deveSalvarAgendaComSucesso() {
        when(agendaRepository.save(any(Agenda.class))).thenReturn(agendaDeTeste); 

        Agenda agendaSalva = agendaService.salvar(new Agenda()); 

        
        assertNotNull(agendaSalva);
        assertEquals("Reunião de Equipe", agendaSalva.getTitulo());
    }

    @Test
    void deveEncontrarAgendaQuandoIDExiste(){
        when(agendaRepository.findById(1L)).thenReturn(Optional.of(agendaDeTeste));

        Optional<Agenda> resultado = agendaService.chamarPorId(1L); 
        assertTrue(resultado.isPresent());
        assertEquals(1L,resultado.get().getId());
    }

    @Test
    void naoDeveEncontrarAgendaQuandoIDNaoExiste(){
        when(agendaRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Agenda> resultado = agendaService.chamarPorId(999L);
        assertFalse(resultado.isPresent());
    }

    @Test
    void deveRetornarTodasAsAgendas(){
        List<Agenda> agendas = List.of(agendaDeTeste, new Agenda());
        when(agendaRepository.findAll()).thenReturn(agendas);
        List<Agenda> resultado = agendaService.chamaPorTodos();

        assertFalse(resultado.isEmpty());
        assertEquals(2,resultado.size());
    }

    @Test
    void deveDeletarAgendasQuandoIDExiste(){
        when(agendaRepository.existsById(1L)).thenReturn(true);
        agendaService.deletarPorId(1L);
        verify(agendaRepository,times(1)).deleteById(1L);
    }

    @Test
    void naoDeveDeletarAgendasQuandoIDNãoExiste(){
        when(agendaRepository.existsById(10L)).thenReturn(false);

        agendaService.deletarPorId(10L);

        
        verify(agendaRepository,times(0)).deleteById(10L);
    }

    @Test
    void  deveAtualizarAgendaQuandoIDExiste(){

        Agenda agendaExistente = new Agenda();
        agendaExistente.setId(1L);
        agendaExistente.setTitulo("titulo antigo");
        agendaExistente.setDescricao("descricao antiga");

        Agenda agendaComDadosAtualizados = new Agenda();
        agendaComDadosAtualizados.setId(99L);
        agendaComDadosAtualizados.setTitulo("Titulo novo");
        agendaComDadosAtualizados.setDescricao("Descricao nova");

        when(agendaRepository.findById(1L)).thenReturn(Optional.of(agendaExistente));
        when(agendaRepository.save(any(Agenda.class))).thenReturn(agendaExistente);

        
        Optional<Agenda> resultado = agendaService.updatePorId(1L,agendaComDadosAtualizados);

        assertTrue(resultado.isPresent());
        verify(agendaRepository,times(1)).save(agendaExistente);
        assertEquals("Titulo novo", resultado.get().getTitulo());
        assertEquals("Descricao nova",resultado.get().getDescricao());

    }

    @Test
    void naoDeveAtualizarAgendaQuandoIDNaoExiste(){
        when(agendaRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Agenda> resultado = agendaService.updatePorId(99L, new Agenda());

        assertFalse(resultado.isPresent());
        verify(agendaRepository,never()).save(any(Agenda.class));
    }






































}
