package com.crud.treinando.adapter.input.Curso;

import com.crud.treinando.application.Curso.CursoService;
import com.crud.treinando.domain.Curso.Curso;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/cursos")
public class CursoController {

    @Autowired
    private CursoService service;

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
