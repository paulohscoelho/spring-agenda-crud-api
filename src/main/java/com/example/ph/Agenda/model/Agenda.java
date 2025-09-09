package com.example.ph.Agenda.model;



import jakarta.persistence.*;
import lombok.*;


@Table(name = "agenda")
@Data
// @NoArgsConstructor cria um construtor vazio, essencial para o Spring
// conseguir instanciar a classe ao receber o JSON.
@NoArgsConstructor
// @AllArgsConstructor cria um construtor com todos os campos.
@AllArgsConstructor
// A anotação @Entity indica que esta classe é uma entidade JPA
// e será mapeada para uma tabela no banco de dados.
@Entity
public class Agenda {

    // A anotação @Id indica que este campo é a chave primária.
    @Id
    // @GeneratedValue configura como o valor do ID será gerado.
    // IDENTITY indica que o banco de dados irá gerar o ID automaticamente.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descricao;

    // Você pode adicionar mais campos aqui conforme a necessidade.
}
