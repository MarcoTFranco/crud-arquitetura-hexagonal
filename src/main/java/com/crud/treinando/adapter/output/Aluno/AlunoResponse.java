package com.crud.treinando.adapter.output.Aluno;

import com.crud.treinando.adapter.output.Curso.CursoResponse;
import com.crud.treinando.domain.Aluno;

public class AlunoResponse {

    private final String nome;
    private final String matricula;
    private final CursoResponse curso;

    public AlunoResponse(Aluno aluno) {
        this.nome = aluno.getNome();
        this.matricula = aluno.getMatricula();
        this.curso = new CursoResponse(aluno.getCurso());
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
