package com.crud.treinando.application.service;

import com.crud.treinando.application.exception.ResourceNotFoundException;
import com.crud.treinando.application.port.in.AlunoUseCase;
import com.crud.treinando.application.port.out.AlunoPersistencePort;
import com.crud.treinando.application.port.out.CursoPersistencePort;
import com.crud.treinando.domain.Aluno;
import com.crud.treinando.domain.Curso;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService implements AlunoUseCase {

    private AlunoPersistencePort alunoPersistencePort;

    private CursoPersistencePort cursoPersistencePort;

    public AlunoService(AlunoPersistencePort alunoPersistencePort, CursoPersistencePort cursoPersistencePort) {
        this.alunoPersistencePort = alunoPersistencePort;
        this.cursoPersistencePort = cursoPersistencePort;
    }

    @Override
    public Aluno insert(String nome, String matricula, Long idCurso) {
        Curso curso = cursoPersistencePort.findById(idCurso)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Curso com id " + idCurso + " não encontrado."));
        Aluno aluno = new Aluno(nome, matricula, curso);
        alunoPersistencePort.save(aluno);
        return aluno;
    }

    @Override
    public Aluno findById(Long id) {
        return alunoPersistencePort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno com id " + id + " não encontrado."));
    }

    @Override
    public List<Aluno> findAll() {
        return alunoPersistencePort.findAll();
    }

    @Override
    public Aluno update(Long id, String nome, Long idCurso) {
        Aluno aluno = alunoPersistencePort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno com id " + id + " não encontrado."));
        Curso curso = cursoPersistencePort.findById(idCurso)
                .orElseThrow(() -> new ResourceNotFoundException("Curso com id " + idCurso + " não encontrado."));
        aluno.setNome(nome);
        aluno.setCurso(curso);
        alunoPersistencePort.save(aluno);
        return aluno;
    }

    @Override
    public void delete(Long id) {
        Aluno aluno = alunoPersistencePort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno com id " + id + " não encontrado."));
        alunoPersistencePort.delete(aluno);
    }
}
