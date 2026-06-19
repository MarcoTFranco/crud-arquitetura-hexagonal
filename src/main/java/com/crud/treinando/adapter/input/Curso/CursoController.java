package com.crud.treinando.adapter.input.curso;

import com.crud.treinando.application.port.in.CursoUseCase;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
    ResponseEntity<Page<CursoResponse>> findAll(@PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<CursoResponse> page = cursoUseCase.findAll(pageable).map(CursoResponse::new);
        return ResponseEntity.ok().body(page);
    }

}
