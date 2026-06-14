package com.crud.treinando.adapter.input.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

import com.crud.treinando.domain.Curso;

public class CursoRequest {
    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @NotNull
    private BigDecimal preco;

    @Deprecated
    public CursoRequest() {
    }

    public CursoRequest(@NotBlank String nome,
                        @NotBlank String descricao,
                        @NotNull BigDecimal preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Curso toModel() {
        return new Curso(nome, descricao, preco);
    }
}
