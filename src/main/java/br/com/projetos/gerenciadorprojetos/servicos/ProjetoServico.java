package br.com.projetos.gerenciadorprojetos.servicos;

import java.util.List;


import org.springframework.stereotype.Service;

import br.com.projetos.gerenciadorprojetos.entidades.Projeto;
import br.com.projetos.gerenciadorprojetos.excessoes.ProjetoNaoEncontradoException;
import br.com.projetos.gerenciadorprojetos.repositorios.ProjetoRepositorio;

@Service
public class ProjetoServico {
    private final ProjetoRepositorio projetoRepositorio;

    public ProjetoServico(ProjetoRepositorio projetoRepositorio) {
        this.projetoRepositorio = projetoRepositorio;
    }

    public List<Projeto> buscarTodos() {
        return projetoRepositorio.findAll();
    }

    public Projeto buscarPorID(Long id) {
        return projetoRepositorio.findById(id).orElseThrow(() -> new ProjetoNaoEncontradoException(id));
    }

    public Projeto cadastrar(Projeto projeto) {
        return projetoRepositorio.save(projeto);
    }

    public Projeto atualizar(Projeto projeto, Long id) {
        buscarPorID(id);
        return projetoRepositorio.save(projeto);
    }

    public void ExcluirPorId(Long id){
        Projeto projetoEncontrado = buscarPorID(id);

        projetoRepositorio.delete(projetoEncontrado);
    }

}
