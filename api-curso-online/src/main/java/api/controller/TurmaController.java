package api.controller;

import api.dto.PageResponse;
import api.dto.TurmaRequestDTO;
import api.dto.TurmaResponseDTO;
import api.service.TurmaService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

    private final TurmaService turmaService;

    public TurmaController(TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    @PostMapping
    public TurmaResponseDTO cadastrar(@Valid @RequestBody TurmaRequestDTO request) {
        return this.turmaService.cadastrar(request);
    }

    @GetMapping
    public PageResponse<TurmaResponseDTO> listar(
        @PageableDefault(page = 0, size = 20, sort = "id") Pageable pageable
    ) {
        return this.turmaService.listar(pageable);
    }

    @GetMapping("/{id}")
    public TurmaResponseDTO pegarPorId(@PathVariable Long id) {
        return this.turmaService.pegarPorId(id);
    }

    @PatchMapping("/{id}")
    public TurmaResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody TurmaRequestDTO request) {
        return this.turmaService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        this.turmaService.deletar(id);
    }
}
