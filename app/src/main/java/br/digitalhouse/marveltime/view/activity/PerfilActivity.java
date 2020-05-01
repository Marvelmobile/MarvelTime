package br.digitalhouse.marveltime.view.activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import br.digitalhouse.marveltime.R;

public class PerfilActivity extends AppCompatActivity {
    private TextInputLayout tilEmail;
    private FirebaseUser user;
    private ImageView imageUsuario;
    private TextInputLayout tilNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        initView();
        loadUser();
    }

    private void loadUser(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            String nome = user.getDisplayName();
            tilEmail.getEditText().setText(email);
            tilNome.getEditText().setText(nome);
            Picasso.get().load(user.getPhotoUrl()).into(imageUsuario);
        }
    }

    public void initView(){
        tilEmail = findViewById(R.id.textEmail);
        imageUsuario = findViewById(R.id.imageviewUsuario);
        tilNome = findViewById(R.id.textNomeCompleto);
    }
}
