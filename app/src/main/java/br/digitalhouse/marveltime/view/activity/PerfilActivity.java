package br.digitalhouse.marveltime.view.activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import br.digitalhouse.marveltime.R;

public class PerfilActivity extends AppCompatActivity {
    private TextInputLayout tivEmail;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        getWindow().setBackgroundDrawableResource(R.drawable.background_menu_principal);
        initView();
        loadEmail();
    }

    private void loadEmail(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            tivEmail.getEditText().setText(email);
        }
    }

    public void initView(){
        tivEmail = findViewById(R.id.textEmail);
    }
}
