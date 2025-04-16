package br.com.projetos.gerenciadorprojetos.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.projetos.gerenciadorprojetos.entidades.Cargo;
import br.com.projetos.gerenciadorprojetos.entidades.Funcionario;

public interface FuncionarioRepositorio extends JpaRepository<Funcionario, Long> {

    @EntityGraph(attributePaths = {"endereco", "cargo"})
    List<Funcionario> findAll();

    @Query("select f from Funcionario f where f.cargo.nome = :cargoNome")
    List<Funcionario> buscarPorCargo(String cargoNome);

    @Query("select f from Funcionario f where f.cargo.nome <> :cargoNome")
    List<Funcionario> buscarPorCargoExceto(String cargoNome);

    List<Funcionario> findByCargoNome(String cargoNome);

    List<Funcionario> findByCargoNomeNot(String cargoNome);

    Optional<Funcionario> findByEmail(String email);

    Optional<Funcionario> findByCpf(String cpf);

    List<Funcionario> findByCargo(Cargo cargo);
    
}
