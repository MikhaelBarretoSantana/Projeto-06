package com.KL.projeto06.Resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KL.projeto06.DTO.LancamentoDTO;
import com.KL.projeto06.Service.LancamentoService;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoResource {
    
    private LancamentoService service; 

    public LancamentoResource(LancamentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity Salvar( @RequestBody LancamentoDTO dto) {
        
    }

}
