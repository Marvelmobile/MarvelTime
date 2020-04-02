package br.digitalhouse.marveltime.model.viewmodel;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.digitalhouse.marveltime.model.PersonagemResult;
import io.reactivex.disposables.CompositeDisposable;

public class PersonagemViewModel {

    private MutableLiveData<List<PersonagemResult>> personagemLista = new MutableLiveData<>();
    ///INGHRIDY COLOCAR LOADING
    private CompositeDisposable disposable = new CompositeDisposable();

}
