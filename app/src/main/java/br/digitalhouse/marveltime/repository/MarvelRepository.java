package br.digitalhouse.marveltime.repository;

import android.content.Context;
import java.util.List;

import br.digitalhouse.marveltime.data.PersonagemDataBase;
import br.digitalhouse.marveltime.model.PersonagemResponse;
import br.digitalhouse.marveltime.model.PersonagemResult;
import br.digitalhouse.marveltime.network.RetrofitService;
import io.reactivex.Flowable;
import io.reactivex.Observable;

public class MarvelRepository {


    public Flowable<List<PersonagemResult>> retornaPersonagemBD(Context context) {
        return PersonagemDataBase.getDatabase(context).personagemDAO().recuperaPersonagemDoBD();
    }

    public void inserePersonagemResponseBd(List<PersonagemResult> personagemList, Context context){
        PersonagemDataBase.getDatabase(context).personagemDAO().insereListaBD(personagemList);
    }

    public void apagaOsDadosBD(PersonagemResponse personagemResponse, Context context){
        PersonagemDataBase.getDatabase(context).personagemDAO().apagaDadosBd(personagemResponse);
    }
}

//public class MarvelRepository {
//    String hash = Util.md5(TS + PRIVATE_KEY + PUBLIC_KEY );
//
//    public Observable<ComicsResponse> getComics(Integer offset){
//        return getApiService().getComics("magazine" , "comic" , true , "focDate" , TS, hash, PUBLIC_KEY, offset);
//    }
//
//    public Flowable<List<Comics>> retornaComicsBD(Context context) {
//        return MarvelDataBase.getDatabase(context).marvelDAO().recuperaComicsDoBD();
//    }
//
//    public void insereComicsBd(List<Comics> comicsList, Context context){
//        MarvelDataBase.getDatabase(context).marvelDAO().insereListaComicsBD(comicsList);
//    }
//
//    public void apagaOsDadosBD(ComicsResponse comicsResponse, Context context) {
//        MarvelDataBase.getDatabase(context).marvelDAO().apagaDadosBd(comicsResponse);
//    }
//}
//
//    public Observable<PersonagemResponse> retornaPersonagemApi(String nomePersonagem) {
//        return RetrofitService.getApiService().retornaPersonagemApi("1", nomePersonagem);
//    }


