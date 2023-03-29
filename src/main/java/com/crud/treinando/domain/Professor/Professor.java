package com.crud.treinando.domain.Professor;

import com.crud.treinando.domain.Curso.Curso;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_professor")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer idade;
    private String sexo;
    private String cep;
    @OneToMany(mappedBy = "professor")
    private List<Curso> cursos = new ArrayList<>();

    @Deprecated
    public Professor() {
    }

    public Professor(String nome, Integer idade, String sexo, String cep) {
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
        this.cep = cep;
    }

    public String getNome() {
        return nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public String getSexo() {
        return sexo;
    }

    public String getCep() {
        return cep;
    }

    public void addCurso(Curso curso) {
        this.cursos.add(curso);
    }
}
