package br.digitalhouse.marveltime.viewmodel;
import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
import java.util.List;
import br.digitalhouse.marveltime.R;
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

    public void getPersongens(Integer offset) {
        if (verificaConexaoComInternet(getApplication())){
            recuperaOsDadosApi(offset);
        } else {
            carregaDadosBD(offset);
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
                                    mutableLiveDataErro.setValue(getApplication().getString(R.string.erro_api));
                                })
        );
    }

    private void carregaDadosBD(Integer offset) {
        disposable.add(
                repository.retornaPersonagemBD(getApplication(), offset)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable1 -> loading.setValue(true))
                        .doOnTerminate(() -> loading.setValue(false))
                        .subscribe(personagemResults -> mutablePersonagemLista.setValue(personagemResults),
                                throwable -> {Log.i("LOG", "erro : " + throwable.getMessage());
                                    mutableLiveDataErro.setValue(getApplication().getString(R.string.erro_bd));
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
}