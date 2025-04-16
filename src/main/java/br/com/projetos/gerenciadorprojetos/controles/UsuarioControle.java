package br.com.projetos.gerenciadorprojetos.controles;

import java.security.Principal;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.projetos.gerenciadorprojetos.dao.AlterarSenhaDAO;
import br.com.projetos.gerenciadorprojetos.dto.AlertDTO;
import br.com.projetos.gerenciadorprojetos.entidades.Funcionario;
import br.com.projetos.gerenciadorprojetos.repositorios.FuncionarioRepositorio;
import br.com.projetos.gerenciadorprojetos.utils.SenhaUtils;

@Controller
public class UsuarioControle {
    
    private final FuncionarioRepositorio funcionarioRepositorio;

    public UsuarioControle(FuncionarioRepositorio funcionarioRepositorio) {
        this.funcionarioRepositorio = funcionarioRepositorio;
    }

    @GetMapping("/login")
    public String login(){
        return "usuario/login";
    }

    @GetMapping("/perfil")
    public ModelAndView perfil(Principal principal){//principal representa o usuario autentica o spring security
        ModelAndView modelAndView = new ModelAndView("usuario/perfil");

        Funcionario usuario = funcionarioRepositorio
            .findByEmail(principal.getName())
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));


        modelAndView.addObject("usuario", usuario);
        modelAndView.addObject("alterarSenhaForm", new AlterarSenhaDAO());

        return modelAndView;
    }

    @PostMapping("/alterar-senha")
    public String alterarSenha(AlterarSenhaDAO form, Principal principal, RedirectAttributes attrs){
        Funcionario usuario = funcionarioRepositorio
            .findByEmail(principal.getName())
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        //Alterar a senha e já criptografar com método hash
        if(SenhaUtils.matches(form.getSenhaAtual(), usuario.getSenha())){
            usuario.setSenha(SenhaUtils.encode(form.getNovaSenha()));
            funcionarioRepositorio.save(usuario);     
            
            attrs.addFlashAttribute("alert", new AlertDTO("Senha alterada com Sucesso", "alert-success"));

        }else{
            attrs.addFlashAttribute("alert", new AlertDTO("Senha atual incorreta", "alert-danger"));
        }

        return ("redirect:/perfil");

    }




}
