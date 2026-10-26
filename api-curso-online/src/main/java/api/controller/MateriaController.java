package api.controller;

import api.dto.MateriaRequestDTO;
import api.dto.MateriaResponse;
import api.service.MateriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/materias")
@Tag(name = "Matérias", description = "Cadastro e remoção das matérias dos módulos")
public class MateriaController {

    private final MateriaService materiaService;

    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @PostMapping
    @Operation(summary = "Adiciona uma matéria a um módulo")
    public ResponseEntity<MateriaResponse> criar(@Valid @RequestBody MateriaRequestDTO request) {
        MateriaResponse resposta = materiaService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove uma matéria")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
        materiaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
