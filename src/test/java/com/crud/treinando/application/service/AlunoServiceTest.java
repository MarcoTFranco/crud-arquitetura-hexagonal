package com.crud.treinando.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crud.treinando.application.exception.BusinessException;
import com.crud.treinando.application.exception.ResourceNotFoundException;
import com.crud.treinando.application.port.out.AlunoPersistencePort;
import com.crud.treinando.application.port.out.CursoPersistencePort;
import com.crud.treinando.domain.Aluno;
import com.crud.treinando.domain.Curso;


@ExtendWith(MockitoExtension.class)
public class AlunoServiceTest {

    @Mock
    private AlunoPersistencePort alunoPersistencePort;

    @Mock
    private CursoPersistencePort cursoPersistencePort;

    @InjectMocks
    private AlunoService alunoService;

    private Curso criarCursoDefault() {
        return new Curso("Engenharia de Software", "ENGSOFT", BigDecimal.valueOf(1000.00));
    }

    private Aluno criarAluno() {
        Curso curso = criarCursoDefault();
        return new Aluno("João", "12345", curso);
    }

    @Test
    public void insert_quandoAlunoECursoExiste_retornaAluno() {
        Curso curso = criarCursoDefault();
        when(cursoPersistencePort.findById(1L)).thenReturn(Optional.of(curso));
        when(alunoPersistencePort.existsByMatricula("12345")).thenReturn(false);
        Aluno resultado = alunoService.insert("João", "12345", 1L);
        assertEquals("João", resultado.getNome());
        verify(alunoPersistencePort).save(any(Aluno.class));
    }

    @Test
    public void insert_quandoCursoNaoExiste_lancaResourceNotFoundException() {
        when(cursoPersistencePort.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> alunoService.insert("João", "12345", 1L));
    }

    @Test
    public void insert_quandoAlunoTemMatriculaDuplicada_lancaBusinessException() {
        when(cursoPersistencePort.findById(1L)).thenReturn(Optional.of(criarCursoDefault()));
        when(alunoPersistencePort.existsByMatricula("12345")).thenReturn(true);
        assertThrows(BusinessException.class, () -> alunoService.insert("João", "12345", 1L));
    }

    @Test
    public void findById_quandoAlunoExiste_retornaAluno() {
        Aluno aluno = criarAluno();
        when(alunoPersistencePort.findById(1L)).thenReturn(Optional.of(aluno));
        Aluno resultado = alunoService.findById(1L);
        assertEquals("João", resultado.getNome());
    }

    @Test
    public void findById_quandoAlunoNaoExiste_lancaResourceNotFoundException() {
        when(alunoPersistencePort.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> alunoService.findById(99L));
    }

    @Test
    public void update_quandoAlunoNaoExiste_lancaResourceNotFoundException() {
        when(alunoPersistencePort.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> alunoService.update(1L, "12345", 1L));
    }

    @Test
    public void update_quandoCursoNaoExiste_lancaResourceNotFoundException() {
        when(alunoPersistencePort.findById(1L)).thenReturn(Optional.of(criarAluno()));
        when(cursoPersistencePort.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> alunoService.update(1L, "12345", 1L));
    }

    @Test
    public void update_quandoAlunoECursoExiste_retornaAluno() {
        Aluno aluno = criarAluno();
        when(alunoPersistencePort.findById(1L)).thenReturn(Optional.of(aluno));
        when(cursoPersistencePort.findById(1L)).thenReturn(Optional.of(new Curso("Sistemas de Informação",
                "SISINFO", BigDecimal.valueOf(1200.00))));
        Aluno resultado = alunoService.update(1L, "João Silva", 1L);
        assertEquals("João Silva", resultado.getNome());
        assertEquals("SISINFO", resultado.getCurso().getDescricao());
        verify(alunoPersistencePort).save(any(Aluno.class));
    }

    @Test
    public void delete_quandoAlunoNaoExiste_lancaResourceNotFoundException() {
        when(alunoPersistencePort.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> alunoService.delete(1L));
    }

    @Test
    public void delete_quandoAlunoExiste() {
        Aluno aluno = criarAluno();
        when(alunoPersistencePort.findById(1L)).thenReturn(Optional.of(aluno));
        alunoService.delete(1L);
        verify(alunoPersistencePort).delete(any(Aluno.class));
    }

}
