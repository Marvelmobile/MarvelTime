package br.digitalhouse.marveltime.view.Interfaces;

public interface HelperQuiz {
    void correto(int correto);
    void errado(int errado);
    void troca();
    void titulo(String titulo);
}