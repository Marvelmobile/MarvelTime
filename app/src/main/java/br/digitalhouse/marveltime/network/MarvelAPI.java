package br.digitalhouse.marveltime.network;
import br.digitalhouse.marveltime.model.PersonagemResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarvelAPI {
    @GET("characters?")
    Single<PersonagemResponse> getPersonagens(
            @Query ( "ts" ) String ts,
            @Query ( "hash" ) String hash,
            @Query ( "apikey" ) String apikey);
}
