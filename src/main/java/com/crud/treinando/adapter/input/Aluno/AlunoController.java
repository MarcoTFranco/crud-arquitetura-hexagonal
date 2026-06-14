package com.crud.treinando.adapter.input.aluno;

import com.crud.treinando.adapter.output.aluno.AlunoResponse;
import com.crud.treinando.application.service.AlunoService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController()
@RequestMapping(value = "/api/v1/alunos")
public class AlunoController {

    private AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping()
    public ResponseEntity<AlunoResponse> insert(@RequestBody @Valid AlunoRequest request) {
        AlunoResponse alunoResponse = alunoService.insert(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(alunoResponse.getId())
                .toUri();
        return ResponseEntity.created(location).body(alunoResponse);
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
    public ResponseEntity<AlunoResponse> update(@PathVariable Long id, @RequestBody @Valid AlunoRequest request) {
        AlunoResponse alunoResponse = alunoService.update(id, request);
        return ResponseEntity.ok().body(alunoResponse);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alunoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
