package br.com.projetos.gerenciadorprojetos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.projetos.gerenciadorprojetos.repositorios.ClienteRepositorio;
import br.com.projetos.gerenciadorprojetos.repositorios.FuncionarioRepositorio;
import br.com.projetos.gerenciadorprojetos.validadores.ClienteValidador;
import br.com.projetos.gerenciadorprojetos.validadores.FuncionarioValidador;
import br.com.projetos.gerenciadorprojetos.validadores.PessoaValidador;

@Configuration
public class ValidadorConfig {
    
    private final ClienteRepositorio clienteRepositorio;
    private final FuncionarioRepositorio funcionarioRepositorio;

    public ValidadorConfig(ClienteRepositorio clienteRepositorio, FuncionarioRepositorio funcionarioRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
        this.funcionarioRepositorio = funcionarioRepositorio;
    }

    // Cria métodos para ensinar o Spring a gerenciar classes não gerenciadas por ele, como bibliotecas externas e validadores

    @Bean // Responsável por ensinar o WebContainer, com injeção de dependência, como instanciar classes
    public ClienteValidador clienteValidador() {
        return new ClienteValidador(clienteRepositorio);
    }

    @Bean
    public FuncionarioValidador funcionarioValidador() {
        return new FuncionarioValidador(funcionarioRepositorio);
    }

    @Bean
    public PessoaValidador pessoaValidador() {
        return new PessoaValidador();
    }
}