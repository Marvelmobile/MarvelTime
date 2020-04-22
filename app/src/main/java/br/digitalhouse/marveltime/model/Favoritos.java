package br.digitalhouse.marveltime.model;

public class Favoritos {
    private PersonagemResult personagemResult;
    private CardModel cardModelquestao;


    public Favoritos() {
    }

    public Favoritos(CardModel cardModelquestao) {
        this.cardModelquestao = cardModelquestao;
    }

    public PersonagemResult getPersonagemResult() {
        return personagemResult;
    }

    public void setPersonagemResult(PersonagemResult personagemResult) {
        this.personagemResult = personagemResult;
    }

    public CardModel getCardModelquestao() {
        return cardModelquestao;
    }

    public void setCardModelquestao(CardModel cardModelquestao) {
        this.cardModelquestao = cardModelquestao;
    }
}