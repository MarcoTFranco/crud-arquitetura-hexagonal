package com.crud.treinando.application.Aluno;

import com.crud.treinando.adapter.input.Aluno.AlunoRequest;
import com.crud.treinando.adapter.output.Aluno.AlunoRepository;
import com.crud.treinando.adapter.output.Aluno.AlunoResponse;
import com.crud.treinando.adapter.output.Curso.CursoRepository;
import com.crud.treinando.application.exception.ResourceNotFoundException;
import com.crud.treinando.domain.Aluno.Aluno;
import com.crud.treinando.domain.Curso.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public AlunoResponse insert(AlunoRequest alunoRequest) {
        Curso curso = cursoRepository.findById(alunoRequest.getCurso())
                .orElseThrow(() -> new ResourceNotFoundException("Curso com id " + alunoRequest.getCurso() + " não encontrado."));
        Aluno aluno = alunoRequest.toModel(curso);
        alunoRepository.save(aluno);
        return new AlunoResponse(aluno);
    }

    public AlunoResponse findById(Long id) {
        return new AlunoResponse(alunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno com id " + id + " não encontrado.")));
    }

    public List<AlunoResponse> findAll() {
        return alunoRepository.findAll()
                .stream()
                .map(AlunoResponse::new)
                .toList();
    }

    public AlunoResponse update(Long id, AlunoRequest request) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno com id " + id + " não encontrado."));
        updateData(request, aluno);
        alunoRepository.save(aluno);
        return new AlunoResponse(aluno);
    }

    private void updateData(AlunoRequest request, Aluno aluno) {
        Curso curso = cursoRepository.findById(request.getCurso())
                .orElseThrow(() -> new ResourceNotFoundException("Curso com id " + request.getCurso() + " não encontrado."));
        aluno.setNome(request.getNome());
        aluno.setCurso(curso);
    }

    public void delete(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno com id " + id + " não encontrado."));
        alunoRepository.delete(aluno);
    }
}
