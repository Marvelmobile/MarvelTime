package br.digitalhouse.marveltime.view.activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import br.digitalhouse.marveltime.R;

public class PerfilActivity extends AppCompatActivity {

    private TextInputLayout tivEmail;
    private Button btnSalvarPerfil;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        initView();

        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            tivEmail.getEditText().setText(name);
        }
    }

    public void initView(){
        tivEmail = findViewById(R.id.textViewEmail);
        btnSalvarPerfil = findViewById(R.id.btn_salvar);
    }


    public void updateEmail() {
        user.updateEmail("user@example.com")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "User email address updated.");
                        }
                    }
                });
    }

    public void updatePassword() {
        String newPassword = "SOME-SECURE-PASSWORD";
        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "User password updated.");
                        }
                    }
                });
    }
}
