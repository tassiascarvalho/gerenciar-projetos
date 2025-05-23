package br.com.projetos.gerenciadorprojetos.excessoes;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ClienteNaoEncontradoException extends EntityNotFoundException {
    public ClienteNaoEncontradoException(Long id){
        super(String.format("Cliente com o ID %d não encontrado", id));
    }
}
