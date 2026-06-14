package com.crud.treinando.adapter.output.aluno;

import com.crud.treinando.adapter.output.curso.CursoResponse;
import com.crud.treinando.domain.Aluno;

public class AlunoResponse {

    private final long id;
    private final String nome;
    private final String matricula;
    private final CursoResponse curso;

    public AlunoResponse(Aluno aluno) {
        this.id = aluno.getId();
        this.nome = aluno.getNome();
        this.matricula = aluno.getMatricula();
        this.curso = new CursoResponse(aluno.getCurso());
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public CursoResponse getCurso() {
        return curso;
    }
}
