package br.com.projetos.gerenciadorprojetos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.projetos.gerenciadorprojetos.enums.Perfil;
import br.com.projetos.gerenciadorprojetos.servicos.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
   
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    // Se comentar esse código completo ele desabilita a segurança da aplicação.

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configura as rotas públicas e privadas da aplicação
        http.authorizeHttpRequests(authz -> authz
                // antMatchers libera rotas
                .antMatchers("/adminlte/**").permitAll() // obrigatoriamente antes do anyRequest
                .antMatchers("/img/**").permitAll() // obrigatoriamente antes do anyRequest
                .antMatchers("/js/**").permitAll() // obrigatoriamente antes do anyRequest
                .antMatchers("/plugins/**").permitAll() // obrigatoriamente antes do anyRequest
                // Dessa forma abaixo é só acessar as rotas se for do tipo Gerente
                // Controle de Rotas
                .antMatchers("/**/cadastrar").hasAuthority(Perfil.ADMIN.toString())
                .antMatchers("/**/editar").hasAuthority(Perfil.ADMIN.toString())
                .antMatchers("/**/excluir").hasAuthority(Perfil.ADMIN.toString())
                // anyRequest qualquer requisição tem que estar autenticada
                .anyRequest().authenticated());

        http.formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/clientes", true)
                // Passando o parâmetro do username fazendo ligação com a tela de login
                .usernameParameter("email")
                .permitAll());

        // Para aceitar requisição de logout via link que é GET
        http.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/login"));

        http.rememberMe(remember -> remember
                // .tokenValiditySeconds(tokenValiditySeconds) - Método para manter o usuário
                // logado por um determinado tempo
                .key("chaverememverMe") // Em produção essa chave deve ser complexa pois vai para o cache
        );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }
}