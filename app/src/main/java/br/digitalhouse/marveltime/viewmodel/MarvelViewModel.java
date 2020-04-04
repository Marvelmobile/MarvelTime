package br.digitalhouse.marveltime.viewmodel;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
import br.digitalhouse.marveltime.model.PersonagemResponse;
import br.digitalhouse.marveltime.model.PersonagemResult;
import br.digitalhouse.marveltime.repository.MarvelRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import static br.digitalhouse.marveltime.util.Util.verificaConexaoComInternet;

public class MarvelViewModel extends AndroidViewModel {
    private MutableLiveData<List<PersonagemResult>> listaPersonagemResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private MarvelRepository repository = new MarvelRepository();

    public MarvelViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<PersonagemResult>> getListaPersonagemResult() {
        return this.listaPersonagemResult;
    }

    public LiveData<Boolean> getLoading() {
        return this.loading;
    }

    private MutableLiveData<String> mutableLiveDataErro = new MutableLiveData<>();
    public LiveData<String> liveDataErro = mutableLiveDataErro;

    private void carregaDadosBD() {
        disposable.add(
                repository.retornaPersonagemBD(getApplication())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(subscription -> loading.setValue(true))
                        .doAfterTerminate(() -> loading.setValue(false))
                        .subscribe(albumList ->
                                        listaPersonagemResult.setValue(albumList),
                                throwable ->
                                        mutableLiveDataErro.setValue(throwable.getMessage() + "problema banco de dados"))
        );
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

