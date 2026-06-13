package com.crud.treinando.adapter.output.Aluno;

import com.crud.treinando.domain.Aluno;
import com.crud.treinando.domain.Curso;

public class AlunoResponse {

    private final String nome;
    private final String matricula;
    private final Curso curso;

    public AlunoResponse(Aluno aluno) {
        this.nome = aluno.getNome();
        this.matricula = aluno.getMatricula();
        this.curso = aluno.getCurso();
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public Curso getCurso() {
        return curso;
    }
}
