package com.crud.treinando.adapter.input.curso;

import com.crud.treinando.application.service.CursoService;
import com.crud.treinando.domain.Curso;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/cursos")
public class CursoController {

    
    private CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Curso> insert(@RequestBody @Valid CursoRequest request) {
        Curso curso = request.toModel();
        service.insert(curso);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(curso.getId())
                .toUri();
        return ResponseEntity.created(location).body(curso);
    }

    @GetMapping
    ResponseEntity<List<Curso>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

}
