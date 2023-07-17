package com.KL.projeto06.Resources;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KL.projeto06.DTO.UsuarioDTO;
import com.KL.projeto06.Exception.ErroAutenticacao;
import com.KL.projeto06.Model.ModelEntity.Usuario;
import com.KL.projeto06.Service.LancamentoService;
import com.KL.projeto06.Service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioResources {
    
    private final UsuarioService service;
    private final LancamentoService lancamentoService;
    
    @PostMapping("/autenticar")
    public ResponseEntity autenticar ( @RequestBody UsuarioDTO dto) {
        try {
            Usuario usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getPassword());
            return ResponseEntity.ok(usuarioAutenticado);
        } catch (ErroAutenticacao e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity salvar( @RequestBody UsuarioDTO dto ) {

        Usuario usuario = Usuario.builder()
            .nome(dto.getNome())
            .email(dto.getEmail())
            .password(dto.getPassword())
            .build();

        try {
            Usuario usuarioSalvo = service.salvarUsuario(usuario);

            return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{id}/saldo")
    public ResponseEntity obterSaldo(@PathVariable Long id) {
        BigDecimal saldo = lancamentoService.obterSaldoPorUsuario(id);

        Optional<Usuario> usuario = service.obterPorId(id); 
        
        if (!usuario.isPresent()) {
            new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(saldo);
    }

}
