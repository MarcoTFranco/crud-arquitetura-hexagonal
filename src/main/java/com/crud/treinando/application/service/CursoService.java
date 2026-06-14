package com.crud.treinando.application.service;

import com.crud.treinando.application.port.in.CursoUseCase;
import com.crud.treinando.application.port.out.CursoPersistencePort;
import com.crud.treinando.domain.Curso;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CursoService implements CursoUseCase {

    private CursoPersistencePort repository;

    public CursoService(CursoPersistencePort repository) {
        this.repository = repository;
    }

    @Override
    public Curso insert(String nome, String descricao, BigDecimal preco) {
        Curso curso = new Curso(nome, descricao, preco);
        repository.save(curso);
        return curso;
    }

    @Override
    public List<Curso> findAll() {
        return repository.findAll();
    }
}
