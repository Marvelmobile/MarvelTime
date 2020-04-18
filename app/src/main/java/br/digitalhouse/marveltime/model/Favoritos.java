package br.digitalhouse.marveltime.model;

public class Favoritos {
    private PersonagemResult personagemResult=null;
    private CardModel cardModelquestão=null;

    public Favoritos(PersonagemResult personagemResult) {
        this.personagemResult = personagemResult;
    }

    public Favoritos(CardModel cardModelquestão) {
        this.cardModelquestão = cardModelquestão;
    }

    public PersonagemResult getPersonagemResult() {
        return personagemResult;
    }

    public void setPersonagemResult(PersonagemResult personagemResult) {
        this.personagemResult = personagemResult;
    }

    public CardModel getCardModelquestão() {
        return cardModelquestão;
    }

    public void setCardModelquestão(CardModel cardModelquestão) {
        this.cardModelquestão = cardModelquestão;
    }
}
