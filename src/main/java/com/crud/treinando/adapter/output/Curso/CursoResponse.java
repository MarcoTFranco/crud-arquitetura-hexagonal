package com.crud.treinando.adapter.output.Curso;

import java.math.BigDecimal;

import com.crud.treinando.domain.Curso;

public class CursoResponse {

    private String nome;

    private String descricao;

    private BigDecimal preco;

    public CursoResponse(Curso curso) {
        this.nome = curso.getNome();
        this.descricao = curso.getDescricao();
        this.preco = curso.getPreco();
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
