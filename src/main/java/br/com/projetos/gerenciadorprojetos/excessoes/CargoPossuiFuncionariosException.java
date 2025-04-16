package br.com.projetos.gerenciadorprojetos.excessoes;

public class CargoPossuiFuncionariosException extends RuntimeException{

    public CargoPossuiFuncionariosException(Long id){
        super(String.format("Cargo com ID %s possui funcionario(s) relcionados ", id));
    }
    
}
