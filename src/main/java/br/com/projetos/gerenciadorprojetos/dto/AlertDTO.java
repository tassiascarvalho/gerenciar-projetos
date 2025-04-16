package br.com.projetos.gerenciadorprojetos.dto;

//Classe para armazenar os tipos de alertas que a aplicação irá receber
public class AlertDTO {

    private String mensagem;

    private String classeCss;

    public AlertDTO(String mensagem, String classeCss) {
        this.mensagem = mensagem;
        this.classeCss = classeCss;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getClasseCss() {
        return classeCss;
    }

    public void setClasseCss(String classeCss) {
        this.classeCss = classeCss;
    }

    
    
}
