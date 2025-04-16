package br.com.projetos.gerenciadorprojetos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.projetos.gerenciadorprojetos.entidades.Cargo;

public interface CargoRepositorio extends JpaRepository<Cargo, Long> {

}
