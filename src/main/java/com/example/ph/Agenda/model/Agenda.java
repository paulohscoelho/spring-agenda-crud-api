package com.example.ph.Agenda.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "agenda")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Agenda {    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;

}
