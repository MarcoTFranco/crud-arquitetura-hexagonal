package com.crud.treinando.adapter.input.curso;

import java.math.BigDecimal;

import com.crud.treinando.domain.Curso;

public class CursoResponse {

    private final Long id;

    private final String nome;

    private final String descricao;

    private final BigDecimal preco;

    public CursoResponse(Curso curso) {
        this.id = curso.getId();
        this.nome = curso.getNome();
        this.descricao = curso.getDescricao();
        this.preco = curso.getPreco();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }
}
