package api.controller;

import api.dto.AtualizarCursoRequestDTO;
import api.dto.CursoCompletoResponseDTO;
import api.dto.CursoRequestDTO;
import api.dto.CursoResponseDTO;
import api.service.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cursos")
@Tag(name = "Cursos", description = "Cadastro e consulta do catálogo de cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @PostMapping
    @Operation(summary = "Cadastra um curso")
    public ResponseEntity<CursoResponseDTO> criar(@Valid @RequestBody CursoRequestDTO request) {
        CursoResponseDTO resposta = cursoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping
    @Operation(summary = "Lista os cursos")
    public List<CursoResponseDTO> listar() {
        return cursoService.listar();
    }

    @GetMapping("/{identificador}")
    @Operation(summary = "Detalha um curso por id ou slug")
    public CursoCompletoResponseDTO buscar(@PathVariable("identificador") String identificador) {
        return cursoService.buscarPorIdentificador(identificador);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados cadastrais de um curso")
    public CursoResponseDTO atualizar(@PathVariable("id") Long id,
                                      @Valid @RequestBody AtualizarCursoRequestDTO request) {
        return cursoService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um curso do catálogo")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
        cursoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
