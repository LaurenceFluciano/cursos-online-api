package api.controller;

import api.dto.AtualizarTurmaRequestDTO;
import api.dto.PageResponse;
import api.dto.TurmaRequestDTO;
import api.dto.TurmaResponseDTO;
import api.service.TurmaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

    private final TurmaService turmaService;

    public TurmaController(TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TurmaResponseDTO cadastrar(@Valid @RequestBody TurmaRequestDTO request) {
        return this.turmaService.cadastrar(request);
    }

    @GetMapping
    public PageResponse<TurmaResponseDTO> listar(
        @PageableDefault(size = 20, sort = "id") Pageable pageable
    ) {
        return this.turmaService.listar(pageable);
    }

    @GetMapping("/{id}")
    public TurmaResponseDTO pegarPorId(@PathVariable Long id) {
        return this.turmaService.pegarPorId(id);
    }

    @PatchMapping("/{id}")
    public TurmaResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody AtualizarTurmaRequestDTO request) {
        return this.turmaService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        this.turmaService.deletar(id);
    }
}
