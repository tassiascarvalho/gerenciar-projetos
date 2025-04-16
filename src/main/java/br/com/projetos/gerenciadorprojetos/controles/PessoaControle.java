package br.com.projetos.gerenciadorprojetos.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.com.projetos.gerenciadorprojetos.enums.UF;
import br.com.projetos.gerenciadorprojetos.validadores.PessoaValidador;

//Classe controleadvice para refatorar o código e centralizar 
//Quando queremos compartilhar um dado entre diferentes controllers da aplicação adicionar anotação ControllerAdvice
@ControllerAdvice(assignableTypes = {FuncionarioControle.class, ClienteControle.class})
 //Todas as classe passsam a ter acesso a esse Controle, mas pode especificar
public class PessoaControle {

    @Autowired
    private PessoaValidador pessoaValidador;

    //InitBinder - validação com anotações
    //no value são os objetos
    @InitBinder(value ={"funcionario", "cliente"})
    public void initBinder(final WebDataBinder binder){
        binder.addValidators(pessoaValidador);
    }

    @ModelAttribute("ufs") 
    //método para criar a disponibilização dos estados já que deve ser feito várias vezes no sistema
    //função para retornar os estados tanto em cliente quanto em funcionario
    public UF[] getUfs(){
        return UF.values();
    }
}
