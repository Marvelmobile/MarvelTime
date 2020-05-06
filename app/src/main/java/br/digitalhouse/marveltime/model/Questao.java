package br.digitalhouse.marveltime.model;

public class Questao {
    private String nome, pergunta, alternativa1, alternativa2, alternativa3, alternativa4, resposta;

    public Questao(String nome, String pergunta, String alternativa1, String alternativa2, String alternativa3, String alternativa4, String resposta) {
        this.nome = nome;
        this.pergunta = pergunta;
        this.alternativa1 = alternativa1;
        this.alternativa2 = alternativa2;
        this.alternativa3 = alternativa3;
        this.alternativa4 = alternativa4;
        this.resposta = resposta;
    }

    public String getNome() {
        return nome;
    }

    public String getPergunta() {
        return pergunta;
    }

    public String getAlternativa1() {
        return alternativa1;
    }

    public String getAlternativa2() {
        return alternativa2;
    }

    public String getAlternativa3() {
        return alternativa3;
    }

    public String getAlternativa4() {
        return alternativa4;
    }

    public String getResposta() {
        return resposta;
    }
}