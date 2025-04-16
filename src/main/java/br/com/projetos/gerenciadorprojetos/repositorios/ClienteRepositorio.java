package br.com.projetos.gerenciadorprojetos.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.projetos.gerenciadorprojetos.entidades.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

    @EntityGraph(attributePaths = "endereco")
    List<Cliente> findAll();


    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByCpf(String cpf);

    
    
}
