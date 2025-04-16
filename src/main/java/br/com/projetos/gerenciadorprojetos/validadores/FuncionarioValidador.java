package br.com.projetos.gerenciadorprojetos.validadores;

import java.util.Optional;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.projetos.gerenciadorprojetos.entidades.Funcionario;
import br.com.projetos.gerenciadorprojetos.repositorios.FuncionarioRepositorio;

public class FuncionarioValidador implements Validator{
    //Validação de Dados via Spring Validator

    
    private FuncionarioRepositorio funcionarioRepositorio;

    public FuncionarioValidador(FuncionarioRepositorio funcionarioRepositorio){
        this.funcionarioRepositorio = funcionarioRepositorio;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Funcionario.class.isAssignableFrom(clazz);
    }


    //LEMBRANDO QUE TEM QUE ASSOCIAR NA CONTROLLER


    @Override
    public void validate(Object target, Errors errors) {
        // Validação em Si

        Funcionario funcionario = (Funcionario) target;

        if(funcionario.getDataAdmissao() != null && funcionario.getDataDemissao()!=null && funcionario.getDataDemissao().isBefore(funcionario.getDataAdmissao())){
                errors.rejectValue("dataDemissao", "validacao.funcionario.dataAdmissao.posterior.dataDemissao");
            }
        

        //validar email
        Optional<Funcionario> funcionarioEncontrado = funcionarioRepositorio.findByEmail(funcionario.getEmail());

        if(funcionarioEncontrado.isPresent() && !funcionarioEncontrado.get().equals(funcionario)){
            errors.rejectValue("email", "validacao.funcionario.email.existente");
        }

        funcionarioEncontrado = funcionarioRepositorio.findByCpf(funcionario.getCpf());

        if(funcionarioEncontrado.isPresent() && !funcionarioEncontrado.get().equals(funcionario)){
            errors.rejectValue("cpf", "validacao.funcionario.cpf.existente");
        }
        
    }
    
}
