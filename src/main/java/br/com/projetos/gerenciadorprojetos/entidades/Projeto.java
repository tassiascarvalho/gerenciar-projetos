package br.com.projetos.gerenciadorprojetos.entidades;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.format.annotation.NumberFormat.Style;
import java.util.Objects;

@Entity
public class Projeto extends Entidade {

    @NotNull
    @Size(min = 3, max = 255)
    @Column(nullable = false)
    private String nome;


    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @NotNull
    @PastOrPresent
    @Column(name = "data_inicio", nullable = false)
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate dataInicio;

    @NotNull
    @PastOrPresent
    @Column(name = "data_fim")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate dataFim;

    @Valid
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id_fk", nullable = false)
    private Cliente cliente;

    @Valid
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lider_id_fk", nullable = false)
    private Funcionario lider;

    @NotNull
    @Column(nullable = false)
    @NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
    private BigDecimal orcamento;

    @NotNull
    @Column(nullable = false)
    @NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
    private BigDecimal gastos;

    @ManyToMany
    @JoinTable(
        name = "projeto_funcionario",
        joinColumns = @JoinColumn(name = "projeto_id_fk"),
        inverseJoinColumns = @JoinColumn(name = "funcionario_id_fk")
    )
    private List<Funcionario> equipe;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getLider() {
        return lider;
    }

    public void setLider(Funcionario lider) {
        this.lider = lider;
    }

    public BigDecimal getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(BigDecimal orcamento) {
        this.orcamento = orcamento;
    }

    public BigDecimal getGastos() {
        return gastos;
    }

    public void setGastos(BigDecimal gastos) {
        this.gastos = gastos;
    }

    public List<Funcionario> getEquipe() {
        return equipe;
    }

    public void setEquipe(List<Funcionario> equipe) {
        this.equipe = equipe;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Projeto)) {
            return false;
        }
        Projeto projeto = (Projeto) o;
        return Objects.equals(nome, projeto.nome) && Objects.equals(descricao, projeto.descricao) && Objects.equals(dataInicio, projeto.dataInicio) && Objects.equals(dataFim, projeto.dataFim) && Objects.equals(cliente, projeto.cliente) && Objects.equals(lider, projeto.lider) && Objects.equals(orcamento, projeto.orcamento) && Objects.equals(gastos, projeto.gastos) && Objects.equals(equipe, projeto.equipe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, descricao, dataInicio, dataFim, cliente, lider, orcamento, gastos, equipe);
    }
    
    
}
