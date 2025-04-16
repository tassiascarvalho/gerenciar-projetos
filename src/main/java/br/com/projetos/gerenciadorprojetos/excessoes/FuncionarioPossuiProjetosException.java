package br.com.projetos.gerenciadorprojetos.excessoes;

public class FuncionarioPossuiProjetosException extends RuntimeException {
    public FuncionarioPossuiProjetosException(Long id){
        super(String.format(" Exception teste - Funcionario com ID %s é Lider de projetos(s) relacionados ", id));
    }
}
