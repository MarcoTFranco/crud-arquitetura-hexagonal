package com.crud.treinando.application.port.in;

import java.math.BigDecimal;
import java.util.List;

import com.crud.treinando.domain.Curso;

public interface CursoUseCase {

    Curso insert(String nome, String descricao, BigDecimal preco);

    List<Curso> findAll();


}
