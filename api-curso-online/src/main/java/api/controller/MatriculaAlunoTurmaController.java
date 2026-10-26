package api.controller;

import api.dto.AtualizarStatusMatriculaDTO;
import api.dto.MatricularAlunoRequestDTO;
import api.dto.MatricularAlunoResponseDTO;
import api.dto.MatriculaAlunoTurmaResponseDTO;
import api.dto.PageResponse;
import api.service.MatriculaAlunoTurmaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matriculas")
public class MatriculaAlunoTurmaController {

    private final MatriculaAlunoTurmaService matriculaService;

    public MatriculaAlunoTurmaController(MatriculaAlunoTurmaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MatricularAlunoResponseDTO cadastrar(@Valid @RequestBody MatricularAlunoRequestDTO request) {
        return this.matriculaService.cadastrar(request);
    }

    @GetMapping
    public PageResponse<MatriculaAlunoTurmaResponseDTO> listar(
            @PageableDefault(size = 20, sort = "id") Pageable pageable
    ) {
        return this.matriculaService.listar(pageable);
    }

    @GetMapping("/{id}")
    public MatriculaAlunoTurmaResponseDTO pegarPorId(@PathVariable Long id) {
        return this.matriculaService.pegarPorId(id);
    }

    @PatchMapping("/{id}/status")
    public MatriculaAlunoTurmaResponseDTO atualizarStatus(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarStatusMatriculaDTO request
    ) {
        return this.matriculaService.atualizarStatus(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        this.matriculaService.deletar(id);
    }
}