package br.com.projetos.gerenciadorprojetos.entidades;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
public class Funcionario extends Pessoa {

    @Column(name = "data_admissao", nullable = false)
    @DateTimeFormat(iso = ISO.DATE)
    @NotNull
    @PastOrPresent
    private LocalDate dataAdmissao;

    @PastOrPresent
    @Column(name = "data_demissao")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate dataDemissao;

    @NotNull
    @Valid
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cargo_id_fk", nullable = false)
    private Cargo cargo;

    @ManyToMany(mappedBy = "equipe", fetch = FetchType.EAGER)
    private List<Projeto> projetos;

    //Campo para guardar a senha
    @Size(min=2, max = 255)
    @Column(nullable = false)
    private String senha;
    
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public LocalDate getDataDemissao() {
        return dataDemissao;
    }

    public void setDataDemissao(LocalDate dataDemissao) {
        this.dataDemissao = dataDemissao;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }

   
}
