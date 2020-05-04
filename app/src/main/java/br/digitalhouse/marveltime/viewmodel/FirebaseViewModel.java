package br.digitalhouse.marveltime.viewmodel;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.model.Favoritos;
import br.digitalhouse.marveltime.util.Helper;
import static br.digitalhouse.marveltime.util.Constantes.CHAVE_IMG_PROFILE;
import static br.digitalhouse.marveltime.util.Helper.verificaConexaoComInternet;

public class FirebaseViewModel extends AndroidViewModel {
    private MutableLiveData<List<Favoritos>> mutableLiveDatafavorito = new MutableLiveData<>();
    private MutableLiveData<Favoritos> mutableLiveDatafavoritado = new MutableLiveData<>();
    private MutableLiveData<String> mutableLiveDataErro = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    public FirebaseViewModel(@NonNull Application application)  {
        super(application);
    }
    public LiveData<List<Favoritos>> liveDatafavorito = mutableLiveDatafavorito;
    public LiveData<Favoritos> favoritado = mutableLiveDatafavoritado;
    public LiveData<String> liveDataErro = mutableLiveDataErro;
    public LiveData<Boolean> getLoading = loading;
    private DatabaseReference reference = FirebaseDatabase.getInstance()
            .getReference(getApplication().getString(R.string.path_fav) + Helper.getIdUsuario(getApplication()));

    public void salvarNomeFirebase(String nome) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(nome)
                .build();

        FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Helper.notificacao(getApplication(), getApplication().getString(R.string.update_error));
                    }
                });
    }

    public void carregarFavoritoFirebase() {
        if (verificaConexaoComInternet(getApplication())){
            reference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<Favoritos> favoritos = new ArrayList<>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Favoritos result = child.getValue(Favoritos.class);
                        favoritos.add(result);
                    }
                    mutableLiveDatafavorito.setValue(favoritos);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } else {
            mutableLiveDataErro.setValue("Não foi possivel carregar os seus Favoritos! \nPor favor verifique sua conexão com a Internet.");
        }

    }

    public void deletarFavoritoFirebase(Favoritos favorito) {
        reference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (favorito.getPersonagemResult() != null && child.getValue(Favoritos.class).getPersonagemResult() != null) {
                        if (child.getValue(Favoritos.class).getPersonagemResult().getId().equals(favorito.getPersonagemResult().getId())){
                            child.getRef().removeValue();
                            mutableLiveDatafavoritado.setValue(favorito);
                        }
                    }else if (favorito.getCardModelquestao() != null && child.getValue(Favoritos.class).getCardModelquestao() != null) {
                        if (child.getValue(Favoritos.class).getCardModelquestao().getNome() == (favorito.getCardModelquestao().getNome())){
                            child.getRef().removeValue();
                            mutableLiveDatafavoritado.setValue(favorito);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void salvarFavoritoVerificado(DatabaseReference reference, Favoritos favorito) {
        String key = reference.push().getKey();
        reference.child(key).setValue(favorito);

        reference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Favoritos result1 = dataSnapshot.getValue(Favoritos.class);
                mutableLiveDatafavoritado.setValue(result1);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public void salvarFavoritoFirebase(Favoritos favorito) {
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
                    mutableLiveDataErro.setValue(getApplication().getString(R.string.erro_favoritar));
                } else {
                    salvarFavoritoVerificado(reference, favorito);
                }
            }
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public void salvarImagemFirebase(InputStream stream) {
        StorageReference storage = FirebaseStorage
                .getInstance()
                .getReference()
                .child(Helper.getIdUsuario(getApplication()) + CHAVE_IMG_PROFILE);

        loading.setValue(true);
        UploadTask uploadTask = storage.putStream(stream);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            loading.setValue(false);
            Helper.notificacao(getApplication(), getApplication().getString(R.string.update_sucss));
        }).addOnFailureListener(e -> {
            mutableLiveDataErro.setValue(e.getMessage());
            loading.setValue(false);
        });
    }
}