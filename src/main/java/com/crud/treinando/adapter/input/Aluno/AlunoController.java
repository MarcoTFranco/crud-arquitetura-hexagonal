package com.crud.treinando.adapter.input.Aluno;

import com.crud.treinando.adapter.output.Aluno.AlunoResponse;
import com.crud.treinando.application.Aluno.AlunoService;
import com.crud.treinando.domain.Aluno.Aluno;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(value = "/api/v1/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @PostMapping()
    public ResponseEntity<AlunoResponse> insert(@RequestBody @Valid AlunoRequest request) {
        Aluno aluno = request.toModel(alunoService);
        alunoService.insert(aluno);
        AlunoResponse alunoResponse = new AlunoResponse(aluno);
        return ResponseEntity.ok().body(alunoResponse);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AlunoResponse> findById(@PathVariable Long id) {
        AlunoResponse alunoResponse = alunoService.findById(id);
        return ResponseEntity.ok().body(alunoResponse);
    }

    @GetMapping()
    public ResponseEntity<List<AlunoResponse>> findAll() {
        List<AlunoResponse> list = alunoService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AlunoResponse> update(@PathVariable Long id, @RequestBody AlunoRequest request) {
        AlunoResponse alunoResponse = new AlunoResponse(alunoService.update(id, request));
        return ResponseEntity.ok().body(alunoResponse);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alunoService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
