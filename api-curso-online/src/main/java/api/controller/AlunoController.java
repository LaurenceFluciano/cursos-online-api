package api.controller;

import api.dto.AlunoRequestDTO;
import api.dto.AlunoResponseDTO;
import api.dto.PageResponse;
import api.service.AlunoService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController 
@RequestMapping("/alunos")
public class AlunoController {
    
    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    public AlunoResponseDTO cadastrar(@Valid @RequestBody AlunoRequestDTO request) {
        return this.alunoService.cadastrar(request);
    }

    @GetMapping("/{id}")
    public AlunoResponseDTO pegarPorId(@PathVariable Long id) {
        return this.alunoService.pegarPorId(id);
    }

    @GetMapping
    public PageResponse<AlunoResponseDTO> listar(
        @PageableDefault(page = 0, size = 20, sort = "id") Pageable pageable
    ) {
        return this.alunoService.listar(pageable);
    }
    
    

}
