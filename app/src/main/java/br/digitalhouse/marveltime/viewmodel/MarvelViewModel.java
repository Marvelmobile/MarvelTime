package br.digitalhouse.marveltime.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
import br.digitalhouse.marveltime.model.PersonagemResult;
import br.digitalhouse.marveltime.repository.MarvelRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MarvelViewModel extends AndroidViewModel {
    private MutableLiveData<List<PersonagemResult>> personagemLista = new MutableLiveData<>();
    //TODO INGHRIDY COLOCAR LOADING
    private CompositeDisposable disposable = new CompositeDisposable();
    private MarvelRepository repository = new MarvelRepository();

    public MarvelViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<PersonagemResult>> getPersonagensLista (){
        return this.personagemLista;
    }

    public void getPersongens (){
        disposable.add(repository.getPersonagem().subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(personagemResponse ->
                        personagemLista.setValue(personagemResponse.getData().getResults()),
                        throwable -> {
                            Log.i("LOG", "erro" + throwable.getMessage());
                        } ));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
