package com.crud.treinando.application.Aluno;

import com.crud.treinando.adapter.input.Aluno.AlunoRequest;
import com.crud.treinando.adapter.output.Aluno.AlunoRepository;
import com.crud.treinando.adapter.output.Aluno.AlunoResponse;
import com.crud.treinando.domain.Aluno.Aluno;
import com.crud.treinando.domain.Curso.Curso;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository repository;

    @PersistenceContext
    private EntityManager manager;

    public void insert(Aluno aluno) {
        repository.save(aluno);
    }

    public <T> T find(Class<T> classe, Long id) {
        return manager.find(classe, id);
    }

    public AlunoResponse findById(Long id) {
        return new AlunoResponse(repository.findById(id).orElseThrow());
    }

    public List<AlunoResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(AlunoResponse::new)
                .toList();
    }

    public Aluno update(Long id, AlunoRequest request) {
        Aluno aluno = repository.findById(id).orElseThrow();
        updateData(request, aluno);
        repository.save(aluno);
        return aluno;
    }

    private void updateData(AlunoRequest request, Aluno aluno) {
        Curso curso = find(Curso.class, request.getCurso());
        Assert.isTrue(curso != null,
                "O curso deve existir para poder mudar para ele");
        aluno.setNome(request.getNome());
        aluno.setCurso(curso);
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (ObjectNotFoundException e) {
            e.getMessage();
        }
    }
}
