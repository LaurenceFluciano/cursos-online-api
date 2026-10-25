package api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.dto.AlunoRequestDTO;
import api.dto.AlunoResponseDTO;
import api.service.AlunoService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.PostMapping;



@RestController 
@RequestMapping("alunos/")
public class AlunoController {
    
    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    public AlunoResponseDTO cadastrar(@Valid @RequestBody AlunoRequestDTO request) {
        return this.alunoService.cadastrar(request);
    }
    

}
