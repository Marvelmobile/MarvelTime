package br.digitalhouse.marveltime.model;
public class CardModel {
    private int Nome;
    private int Imagem;

    public CardModel() { }

    public int getNome() {
        return Nome;
    }

    public void setNome(int nome) {
        Nome = nome;
    }

    public int getImagem() {
        return Imagem;
    }

    public void setImagem(int imagem) {
        Imagem = imagem;
    }

    public CardModel(int imagem, int nome) {
        Nome = nome;
        Imagem = imagem;
    }
}