package com.crud.treinando.adapter.input.aluno;

import com.crud.treinando.application.port.in.AlunoUseCase;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController()
@RequestMapping(value = "/api/v1/alunos")
public class AlunoController {

    private AlunoUseCase alunoUseCase;

    public AlunoController(AlunoUseCase alunoUseCase) {
        this.alunoUseCase = alunoUseCase;
    }

    @PostMapping()
    public ResponseEntity<AlunoResponse> insert(@RequestBody @Valid AlunoRequest request) {
        AlunoResponse alunoResponse = new AlunoResponse(
                alunoUseCase.insert(request.getNome(), request.getMatricula(), request.getIdCurso()));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(alunoResponse.getId())
                .toUri();
        return ResponseEntity.created(location).body(alunoResponse);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AlunoResponse> findById(@PathVariable Long id) {
        AlunoResponse alunoResponse = new AlunoResponse(alunoUseCase.findById(id));
        return ResponseEntity.ok().body(alunoResponse);
    }

    @GetMapping()
    public ResponseEntity<Page<AlunoResponse>> findAll(@PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<AlunoResponse> page = alunoUseCase.findAll(pageable).map(AlunoResponse::new);
        return ResponseEntity.ok().body(page);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AlunoResponse> update(@PathVariable Long id, @RequestBody @Valid AlunoRequest request) {
        AlunoResponse alunoResponse = new AlunoResponse(
                alunoUseCase.update(id, request.getNome(), request.getIdCurso()));
        return ResponseEntity.ok().body(alunoResponse);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alunoUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

}
