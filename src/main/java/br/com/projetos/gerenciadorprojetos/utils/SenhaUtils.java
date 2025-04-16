package br.com.projetos.gerenciadorprojetos.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SenhaUtils {
    
    public static String encode(String senha){
        //criptografia de senha
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(senha);
    }

    //Método para comparação de senha
    public static boolean matches(String senha, String hash){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.matches(senha, hash);

    }
}
