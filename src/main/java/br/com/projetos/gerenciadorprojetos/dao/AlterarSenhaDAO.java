package br.com.projetos.gerenciadorprojetos.dao;

public class AlterarSenhaDAO {
    //Classe para manipular apenas dados em movimento
    private String senhaAtual;

    private String novaSenha;

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    
    
}
