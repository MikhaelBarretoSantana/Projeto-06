package com.KL.projeto06.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UsuarioDTO {
    
    private String email;
    private String nome;
    private String password;
}
