package br.com.projetos.gerenciadorprojetos.validadores;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.projetos.gerenciadorprojetos.entidades.Pessoa;

public class PessoaValidador implements Validator {




    @Override
    public boolean supports(Class<?> clazz) {
        return Pessoa.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Pessoa pessoa = (Pessoa) target;

        if(pessoa.getDataNascimento() != null){
            int idade = Period.between(pessoa.getDataNascimento(), LocalDate.now()).getYears() ;

            if(idade < 18 || idade > 100){
                errors.rejectValue("dataNascimento", "validacao.pessoa.idade.menor18.maior100");
            }
            //Period classe com métodos para calculo de data
        }
        
    }
    
}
