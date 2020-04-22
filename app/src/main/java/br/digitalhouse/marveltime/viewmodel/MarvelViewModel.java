package br.digitalhouse.marveltime.viewmodel;
import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import br.digitalhouse.marveltime.model.Favoritos;
import br.digitalhouse.marveltime.model.PersonagemResponse;
import br.digitalhouse.marveltime.model.PersonagemResult;
import br.digitalhouse.marveltime.repository.MarvelRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import static br.digitalhouse.marveltime.util.Helper.verificaConexaoComInternet;

public class MarvelViewModel extends AndroidViewModel {
    private MutableLiveData<List<PersonagemResult>> mutablePersonagemLista = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private MarvelRepository repository = new MarvelRepository();
    private MutableLiveData<String> mutableLiveDataErro = new MutableLiveData<>();
    public LiveData<String> liveDataErro = mutableLiveDataErro;
    public MarvelViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<List<PersonagemResult>> personagemLista = mutablePersonagemLista;
    public LiveData<Boolean> getLoading = loading;
    private MutableLiveData<Favoritos> favoriteAdded = new MutableLiveData<>();
    public LiveData<Favoritos> favoritado = favoriteAdded;

    public void getPersongens(Integer offset) {
        if (verificaConexaoComInternet(getApplication())){
            recuperaOsDadosApi(offset);
        } else {
            carregaDadosBD(offset);
            //TODO : Desse jeito funciona
            loading.setValue(false);
        }
    }

    private void recuperaOsDadosApi(Integer offset) {
        disposable.add(
                repository.getPersonagem(offset)
                        .map(this::verificaPersonagem)
                        .map(this::insereDadosBd)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable1 -> loading.setValue(true))
                        .doOnTerminate(() -> loading.setValue(false))
                        .subscribe(personagemResponse ->
                                        mutablePersonagemLista.setValue(personagemResponse.getData().getResults()),
                                throwable -> {
                                    Log.i("LOG", "erro : " + throwable.getMessage());
                                    mutableLiveDataErro.setValue("Erro ao buscar dados da API. \nVerifique se há conexao com a Internet!");
                                })
        );
    }

    private void carregaDadosBD(Integer offset) {
        disposable.add(
                repository.retornaPersonagemBD(getApplication(), offset)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable1 -> loading.setValue(true))
                        //TODO : Nao está funcinoando, fica em loading infinito
                        .doOnTerminate(() -> loading.setValue(false))
                        .subscribe(personagemResults ->
                                        mutablePersonagemLista.setValue(personagemResults),
                                throwable -> {
                                    Log.i("LOG", "erro : " + throwable.getMessage());
                                    mutableLiveDataErro.setValue("Problema ao carregar Personagens do banco de dados");
                                })


        );
    }

    private PersonagemResponse verificaPersonagem(PersonagemResponse response){
        List<PersonagemResult> novaLista = new ArrayList<>();
        for (PersonagemResult personagemResult : response.getData().getResults()) {
            if(!personagemResult.getThumbnail().getPath().contains("image_not_available")){
                novaLista.add(personagemResult);
            }
        }
        response.getData().getResults().clear();
        response.getData().setResults(novaLista);
        return response;
    }

    private PersonagemResponse insereDadosBd(PersonagemResponse personagemResponse) {
        repository.apagaOsDadosBD(personagemResponse, getApplication());
        repository.inserePersonagemResponseBd(personagemResponse.getData().getResults(), getApplication());
        return personagemResponse;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    public void salvarFavorito(Favoritos favorito) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("marvelTimeTeste" + "/favoritos");
        reference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean existe = false;

                for (DataSnapshot resultSnapshot : dataSnapshot.getChildren()) {
                    Favoritos firebaseResult = resultSnapshot.getValue(Favoritos.class);
                    if (firebaseResult != null){
                        if(firebaseResult.getPersonagemResult() != null && favorito.getPersonagemResult() != null){
                            if(firebaseResult.getPersonagemResult().getId().equals(favorito.getPersonagemResult().getId())){
                                existe = true;
                            }
                        }else if (firebaseResult.getCardModelquestao() != null && favorito.getCardModelquestao() != null){
                            if(firebaseResult.getCardModelquestao().getNome() == (favorito.getCardModelquestao().getNome())){
                                existe = true;
                            }
                        }
                    }
                }

                if (existe) {
                    mutableLiveDataErro.setValue("Item já favoritado!");
                } else {
                    salvarFavoritoVerificado(reference, favorito);
                }
            }
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    private void salvarFavoritoVerificado(DatabaseReference reference, Favoritos favorito) {
        String key = reference.push().getKey();
        reference.child(key).setValue(favorito);

        reference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Favoritos result1 = dataSnapshot.getValue(Favoritos.class);
                favoriteAdded.setValue(result1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}