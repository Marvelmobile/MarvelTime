package br.digitalhouse.marveltime.view.activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import java.util.Objects;
import br.digitalhouse.marveltime.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilActivity extends AppCompatActivity {
    private TextInputLayout tivEmail, tivNome;
    private FirebaseUser user;
    private CircleImageView imagemUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        initView();
        loadEmail();
    }

    private void loadEmail(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String nomegoogle= user.getDisplayName();
            String email = user.getEmail();
            Picasso.get().load(user.getPhotoUrl()).into(imagemUsuario);
            Objects.requireNonNull(tivEmail.getEditText()).setText(email);
            Objects.requireNonNull(tivNome.getEditText()).setText(nomegoogle);
        }
    }

    public void initView(){
        tivEmail = findViewById(R.id.textEmail);
        tivNome= findViewById(R.id.textNomeCompleto);
        imagemUsuario= findViewById(R.id.imageviewUsuario);
    }
}
