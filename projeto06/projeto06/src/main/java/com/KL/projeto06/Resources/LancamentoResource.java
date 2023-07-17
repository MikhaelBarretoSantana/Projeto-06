package com.KL.projeto06.Resources;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.KL.projeto06.DTO.AtualizaStatusDTO;
import com.KL.projeto06.DTO.LancamentoDTO;
import com.KL.projeto06.Exception.RegraNegocioException;
import com.KL.projeto06.Model.ModelEntity.Lancamentos;
import com.KL.projeto06.Model.ModelEntity.Usuario;
import com.KL.projeto06.Model.ModelEnum.StatusLancamento;
import com.KL.projeto06.Model.ModelEnum.TipoLancamento;
import com.KL.projeto06.Service.LancamentoService;
import com.KL.projeto06.Service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/lancamentos")
@RequiredArgsConstructor
public class LancamentoResource {

    private LancamentoService service;

    private UsuarioService usuarioService;

    public LancamentoResource(LancamentoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity buscar(
        @RequestParam(value = "descricao", required = false) String descricao,
        @RequestParam(value = "mes", required = false) Integer mes,
        @RequestParam(value = "ano", required = false) Integer ano,
        @RequestParam("usuario") Long idUsuario
    ) {
        Lancamentos lancamentoFiltro = new Lancamentos();
        lancamentoFiltro.setDescricao(descricao);
        lancamentoFiltro.setMes(mes);
        lancamentoFiltro.setAno(ano);

        Optional<Usuario> usuario = usuarioService.obterPorId(idUsuario);
        if(!usuario.isPresent()) {
            return ResponseEntity.badRequest().body("Não foi possível realizar a consulta. Usuario não encontrado para o Id informado.");
        } else {
            lancamentoFiltro.setUsuario(usuario.get());
        }

        List<Lancamentos> lancamentos =  service.buscar(lancamentoFiltro);
        
        return ResponseEntity.ok(lancamentos);
    }


    @PostMapping
    public ResponseEntity Salvar(@RequestBody LancamentoDTO dto) {
        try {
            Lancamentos entidade = converter(dto);
            entidade = service.salvar(entidade);
            return ResponseEntity.ok(entidade);
        } catch (RegraNegocioException e) {
            return new ResponseEntity(dto, HttpStatus.CREATED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LancamentoDTO dto) {
        return service.obterPorId(id).map(entity -> {
            try {
                Lancamentos lancamento = converter(dto);
                lancamento.setId(entity.getId());
                service.atualizar(lancamento);
                return ResponseEntity.ok(lancamento);
            } catch (RegraNegocioException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() -> new ResponseEntity<>("Lancamento não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
    }

    @PutMapping("{id}/atualiza-status")
    public ResponseEntity atualizarStatus(@PathVariable("id") Long id, @RequestBody AtualizaStatusDTO dto) {
        return service.obterPorId(id).map( entity -> {
            StatusLancamento statusSelecionado = StatusLancamento.valueOf(dto.getStatus());
            
            if (statusSelecionado == null) {
                return ResponseEntity.badRequest().body("Não foi possível atulizar o status de lançamento, envie um status válido.");
            } 

            try {
                entity.setStatus(statusSelecionado);
                service.atualizar(entity);
                return ResponseEntity.ok(entity);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }    
        }).orElseGet( () -> 
            new ResponseEntity("Lancamento não encontrado na base de dados.", HttpStatus.BAD_REQUEST)
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id) {
        return service.obterPorId(id).map( entidade -> {
            service.deletar(entidade);
            return new ResponseEntity(HttpStatus.NO_CONTENT );
        }).orElseGet( () -> 
            new ResponseEntity<>("Lançamento não encontrado na base de dados.", HttpStatus.BAD_REQUEST) );
    }

    private Lancamentos converter(LancamentoDTO dto) {
        Lancamentos lancamento = new Lancamentos();
        lancamento.setId(dto.getId());
        lancamento.setDescricao(dto.getDescricao());
        lancamento.setAno(dto.getAno());
        lancamento.setMes(dto.getMes());
        lancamento.setValor(dto.getValor());

        Usuario usuario = usuarioService
                .obterPorId(dto.getUsuario())
                .orElseThrow(() -> new RegraNegocioException("Usuario não encontrado para o id informado."));

        lancamento.setUsuario(usuario);
        if(dto.getTipo() != null) {
            lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
        }

        if(dto.getStatus() != null) {
            lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
        }

        return lancamento;
    }

}
