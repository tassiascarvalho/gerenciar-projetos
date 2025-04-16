package br.com.projetos.gerenciadorprojetos.servicos;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.projetos.gerenciadorprojetos.entidades.Cliente;
import br.com.projetos.gerenciadorprojetos.excessoes.ClienteNaoEncontradoException;
import br.com.projetos.gerenciadorprojetos.excessoes.ClientePossuiProjetosException;
import br.com.projetos.gerenciadorprojetos.repositorios.ClienteRepositorio;
import br.com.projetos.gerenciadorprojetos.repositorios.ProjetoRepositorio;

@Service
public class ClienteServico {

    private final ClienteRepositorio clienteRepositorio;
    private final ProjetoRepositorio projetoRepositorio;
    
    public ClienteServico(ClienteRepositorio clienteRepositorio, ProjetoRepositorio projetoRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
        this.projetoRepositorio = projetoRepositorio;
    }
    
    public List<Cliente> buscarTodos(){
        return clienteRepositorio.findAll();
    }

    public Cliente buscarPorID(Long id){
        return clienteRepositorio.findById(id)
            .orElseThrow(() -> new ClienteNaoEncontradoException(id));
    }

    public Cliente cadastrar(Cliente cliente){
        return clienteRepositorio.save(cliente);
    }

    public Cliente atualizar(Cliente cliente, Long id){
        buscarPorID(id);
        return clienteRepositorio.save(cliente);
    }
    
    public void excluirPorid(Long id){
        Cliente clienteEncontrado =  buscarPorID(id);

        if(projetoRepositorio.findByCliente(clienteEncontrado).isEmpty()){
            clienteRepositorio.delete(clienteEncontrado);
        }else{
            throw new ClientePossuiProjetosException(id);
        }
    }
}
