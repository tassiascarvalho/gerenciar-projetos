package br.com.projetos.gerenciadorprojetos.servicos;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.projetos.gerenciadorprojetos.entidades.Cargo;
import br.com.projetos.gerenciadorprojetos.excessoes.CargoNaoEncontradoException;
import br.com.projetos.gerenciadorprojetos.excessoes.CargoPossuiFuncionariosException;
import br.com.projetos.gerenciadorprojetos.repositorios.CargoRepositorio;
import br.com.projetos.gerenciadorprojetos.repositorios.FuncionarioRepositorio;

@Service
public class CargoServico {

    private final CargoRepositorio cargoRepositorio;
    private final FuncionarioRepositorio funcionarioRepositorio;

    public CargoServico(CargoRepositorio cargoRepositorio, FuncionarioRepositorio funcionarioRepositorio) {
        this.cargoRepositorio = cargoRepositorio;
        this.funcionarioRepositorio = funcionarioRepositorio;
    }

    public List<Cargo> buscarTodos(){
        return cargoRepositorio.findAll();
    }

    public Cargo buscarPorID(Long id){
        Cargo cargoEncontrado = cargoRepositorio.findById(id).orElseThrow(() -> new CargoNaoEncontradoException(id));

        return cargoEncontrado;
    }

    public Cargo cadastrar(Cargo cargo){
        return cargoRepositorio.save(cargo);
    }

    public Cargo atualizar(Cargo cargo, Long id){
        buscarPorID(id);
        return cargoRepositorio.save(cargo);
    }
    
    public void excluirPorid(Long id){
        Cargo cargoEncontrado =  buscarPorID(id);

        if(funcionarioRepositorio.findByCargo(cargoEncontrado).isEmpty()){
            cargoRepositorio.delete(cargoEncontrado);
        }else{
            throw new CargoPossuiFuncionariosException(id);
        }
    }


}
