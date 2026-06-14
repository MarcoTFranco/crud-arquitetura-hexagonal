package com.crud.treinando.adapter.input.aluno;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AlunoRequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String matricula;

    @NotNull
    private Long idCurso;

    public AlunoRequest(@NotBlank String nome,
            @NotBlank String matricula,
            @NotNull Long idCurso) {
        this.nome = nome;
        this.matricula = matricula;
        this.idCurso = idCurso;
    }

    public String getNome() {
        return nome;
    }

    public Long getIdCurso() {
        return idCurso;
    }

    public String getMatricula() {
        return matricula;
    }
}
