package api.controller;

import api.dto.AtualizarModuloDTO;
import api.dto.ModuloRequestDTO;
import api.dto.ModuloResponse;
import api.service.ModuloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/modulos")
@Tag(name = "Módulos", description = "Cadastro e manutenção dos módulos dos cursos")
public class ModuloController {

    private final ModuloService moduloService;

    public ModuloController(ModuloService moduloService) {
        this.moduloService = moduloService;
    }

    @PostMapping
    @Operation(summary = "Adiciona um módulo a um curso")
    public ResponseEntity<ModuloResponse> criar(@Valid @RequestBody ModuloRequestDTO request) {
        ModuloResponse resposta = moduloService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de um módulo")
    public ModuloResponse atualizar(@PathVariable("id") Long id, @Valid @RequestBody AtualizarModuloDTO request) {
        return moduloService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um módulo")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
        moduloService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
