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

    //https://gemini.google.com/share/05dee44200f0 codigo
    //AMANHA EU ESCREVO NO CADERNO O NOTPAD E ALGUMAS DICAS.


    @BeforeEach
    void setUp() {
        agendaDeTeste = new Agenda();
        agendaDeTeste.setId(1L);
        agendaDeTeste.setTitulo("Reunião de Equipe");
        agendaDeTeste.setDescricao("Discutir novas tarefas.");
    }

    // Teste para o método 'salvar()'.
    @Test
    void deveSalvarAgendaComSucesso() {//.save
        when(agendaRepository.save(any(Agenda.class))).thenReturn(agendaDeTeste);  //given

        Agenda agendaSalva = agendaService.salvar(new Agenda()); //when

        //asserts são usandos para verificar: Optional<Agenda>,List<Agenda> ou Agenda agendaSalva
        assertNotNull(agendaSalva);
        assertEquals("Reunião de Equipe", agendaSalva.getTitulo());// then
    }

    @Test
    void deveEncontrarAgendaQuandoIDExiste(){//findById()
        when(agendaRepository.findById(1L)).thenReturn(Optional.of(agendaDeTeste));//given

        Optional<Agenda> resultado = agendaService.chamarPorId(1L); //when

        assertTrue(resultado.isPresent());//Then
        assertEquals(1L,resultado.get().getId());
    }

    @Test
    void naoDeveEncontrarAgendaQuandoIDNaoExiste(){//findById()
        when(agendaRepository.findById(999L)).thenReturn(Optional.empty());//Given cenário

        Optional<Agenda> resultado = agendaService.chamarPorId(999L);//When ação

        //asserts são usandos para verificar: Optional<Agenda>,List<Agenda> ou Agenda agendaSalva
//        assertTrue(resultado.isPresent());//Then verificação
        assertFalse(resultado.isPresent());//Then verificação

    }

    @Test
    void deveRetornarTodasAsAgendas(){//findAll()
        List<Agenda> agendas = List.of(agendaDeTeste, new Agenda());
        when(agendaRepository.findAll()).thenReturn(agendas);//Given cenario

        List<Agenda> resultado = agendaService.chamaPorTodos();//When ação

        //asserts são usandos para verificar: Optional<Agenda>,List<Agenda> ou Agenda agendaSalva
        assertFalse(resultado.isEmpty());//Then verificação
        assertEquals(2,resultado.size());//Os dois itens é 'agendaDeTeste' e 'new Agenda()'//Then verificação

    }

    @Test
    void deveDeletarAgendasQuandoIDExiste(){//deletar
        when(agendaRepository.existsById(1L)).thenReturn(true);//Given

        agendaService.deletarPorId(1L);//When

        //o ID realmente existe  e deve deletar
        verify(agendaRepository,times(1)).deleteById(1L);
    }

    @Test
    void naoDeveDeletarAgendasQuandoIDNãoExiste(){//deletar
        when(agendaRepository.existsById(10L)).thenReturn(false);

        agendaService.deletarPorId(10L);

        //o ID NÃO existe entao NÃO deve deletar
        verify(agendaRepository,times(0)).deleteById(10L);
    }

    @Test
    void  deveAtualizarAgendaQuandoIDExiste(){//atualizar
        //continuar nesse de atualizar

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

        //public Optional<Agenda>                  updatePorId(Long id, Agenda agenda){//classe AgendaService
        Optional<Agenda> resultado = agendaService.updatePorId(1L,agendaComDadosAtualizados);

        assertTrue(resultado.isPresent());
        verify(agendaRepository,times(1)).save(agendaExistente);
        assertEquals("Titulo novo", resultado.get().getTitulo());
        assertEquals("Descricao nova",resultado.get().getDescricao());

    }

    @Test
    void naoDeveAtualizarAgendaQuandoIDNaoExiste(){
        // Simulamos que a busca por ID retorna um Optional vazio.
        when(agendaRepository.findById(99L)).thenReturn(Optional.empty());//given

        Optional<Agenda> resultado = agendaService.updatePorId(99L, new Agenda());//when

        //then(verificao)
        assertFalse(resultado.isPresent());
        verify(agendaRepository,never()).save(any(Agenda.class));
    }






































}
