package com.crud.treinando.adapter.input.curso;

import com.crud.treinando.adapter.output.curso.CursoResponse;
import com.crud.treinando.application.port.in.CursoUseCase;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/cursos")
public class CursoController {

    private CursoUseCase cursoUseCase;

    public CursoController(CursoUseCase cursoUseCase) {
        this.cursoUseCase = cursoUseCase;
    }

    @PostMapping
    public ResponseEntity<CursoResponse> insert(@RequestBody @Valid CursoRequest request) {
        CursoResponse cursoResponse = new CursoResponse(
                cursoUseCase.insert(request.getNome(), request.getDescricao(), request.getPreco()));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cursoResponse.getId())
                .toUri();
        return ResponseEntity.created(location).body(cursoResponse);
    }

    @GetMapping
    ResponseEntity<List<CursoResponse>> findAll() {
        return ResponseEntity.ok().body(cursoUseCase.findAll().stream().map(CursoResponse::new).toList());
    }

}
