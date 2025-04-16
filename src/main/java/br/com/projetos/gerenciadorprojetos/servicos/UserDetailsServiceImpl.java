package br.com.projetos.gerenciadorprojetos.servicos;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.projetos.gerenciadorprojetos.entidades.Funcionario;
import br.com.projetos.gerenciadorprojetos.entidades.UserDetailsImpl;
import br.com.projetos.gerenciadorprojetos.repositorios.FuncionarioRepositorio;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    //Utilização de construtor para injetar depêndencias e não o Autowired
    private final FuncionarioRepositorio funcionarioRepositorio;

    public UserDetailsServiceImpl(FuncionarioRepositorio funcionarioRepositorio) {
        this.funcionarioRepositorio = funcionarioRepositorio;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // É o metodo que chama para encontrar o usuario na aplicação
        //Autenticação do Usuario
        Funcionario funcionario = funcionarioRepositorio.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));

        return new UserDetailsImpl(funcionario);
    }
    
}
