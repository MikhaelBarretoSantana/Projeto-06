package com.KL.projeto06.Resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KL.projeto06.DTO.UsuarioDTO;
import com.KL.projeto06.Exception.ErroAutenticacao;
import com.KL.projeto06.Model.ModelEntity.Usuario;
import com.KL.projeto06.Service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResources {
    
    private UsuarioService service;

    public UsuarioResources(UsuarioService service) {
        this.service = service;
    }
    
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

}
