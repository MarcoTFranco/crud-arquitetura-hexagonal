package com.crud.treinando.application.port.in;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.crud.treinando.domain.Curso;

public interface CursoUseCase {

    Curso insert(String nome, String descricao, BigDecimal preco);

    Page<Curso> findAll(Pageable pageable);


}
