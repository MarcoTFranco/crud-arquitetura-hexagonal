package com.crud.treinando.adapter.input.Aluno;

import com.crud.treinando.application.Aluno.AlunoService;
import com.crud.treinando.domain.Aluno.Aluno;
import com.crud.treinando.domain.Curso.Curso;
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

    public Long getCurso() {
        return idCurso;
    }

    public Aluno toModel(AlunoService service) {
        @NotNull Curso curso = service.find(Curso.class, idCurso);
        return new Aluno(nome, matricula, curso);
    }


}
