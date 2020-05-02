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
    private TextInputLayout tivEmail;
    private TextInputLayout tivNomeCompleto;
    private FirebaseUser user;
    private ImageView imageUsuario;

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
            tivEmail.getEditText().setText(email);
            tivNomeCompleto.getEditText().setText(user.getDisplayName());
            Picasso.get().load(user.getPhotoUrl()).into(imageUsuario);
        }
    }

    public void initView(){
        tivEmail = findViewById(R.id.textEmail);
        imageUsuario = findViewById(R.id.imageviewUsuario);
        tivNomeCompleto = findViewById(R.id.textNomeCompleto);
    }
}
