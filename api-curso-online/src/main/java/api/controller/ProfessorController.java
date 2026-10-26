package api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import api.dto.PageResponse;
import api.dto.ProfessorRequestDTO;
import api.dto.ProfessorResponseDTO;
import api.service.ProfessorService;


@RestController  
@RequestMapping ("/professores")
public class ProfessorController {
    
    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @PostMapping
    public ProfessorResponseDTO cadastrar(@Valid @RequestBody ProfessorRequestDTO request) {
        return this.professorService.cadastrar(request);
    }

    @GetMapping("/{id}")
    public ProfessorResponseDTO pegarPorId(@PathVariable Long id) {
        return this.professorService.pegarPorId(id);
    }

    @GetMapping
    public PageResponse<ProfessorResponseDTO> listar(
        @PageableDefault(page = 0, size = 20, sort = "id") Pageable pageable
    ) {
        return this.professorService.listar(pageable);
    }
    

}
