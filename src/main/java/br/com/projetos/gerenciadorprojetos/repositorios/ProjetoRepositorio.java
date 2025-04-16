package br.com.projetos.gerenciadorprojetos.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.projetos.gerenciadorprojetos.entidades.Cliente;
import br.com.projetos.gerenciadorprojetos.entidades.Funcionario;
import br.com.projetos.gerenciadorprojetos.entidades.Projeto;

public interface ProjetoRepositorio extends JpaRepository<Projeto, Long> {

    @EntityGraph(attributePaths = {"cliente", "lider"})
    List<Projeto> findAll();

    List<Projeto> findByCliente(Cliente cliente);

    List<Projeto> findByLider(Funcionario lider);
    
}
