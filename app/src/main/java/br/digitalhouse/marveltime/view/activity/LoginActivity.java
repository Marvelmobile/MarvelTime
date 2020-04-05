package br.digitalhouse.marveltime.view.activity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.util.Helper;

import static br.digitalhouse.marveltime.view.activity.CadastroActivity.CHAVE_EMAIL;

public class LoginActivity extends AppCompatActivity {

    private static final int Activity_UM_DOIS = 1;
    private TextInputLayout loginUsuario;
    private TextInputLayout loginSenha;
    private FloatingActionButton bntLogin;
    private TextView loginRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        linkCadastroUsuario();

        bntLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validaCampos()){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        });

        loginRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(new Intent(LoginActivity.this, CadastroActivity.class),1);

            }
        });
    }

    private boolean validaCampos(){
        String usuario = loginUsuario.getEditText().getText().toString();
        String senha = loginSenha.getEditText().getText().toString();

        if (Helper.isEmptyString(usuario) || Helper.isEmptyString(senha))
            notificacao( "Por favor, preencha todos os campos");
        else if (!Helper.usuarioValido(usuario) || !Helper.senhaValida(senha))
            notificacao( "Usuário ou senha não atendem as regras, por favor, tente novamente");
        else
            return true;

        return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Activity_UM_DOIS) {
            if (resultCode == RESULT_OK) {
                loginUsuario.getEditText().setText(data.getStringExtra(CHAVE_EMAIL));
            }
        }
    }

    private void linkCadastroUsuario() {
        String msgRegistroUsuario = "Ainda não é membro? [Registre-se aqui]";
        int i1 = msgRegistroUsuario.indexOf("[");
        int i2 = msgRegistroUsuario.indexOf("]");

        loginRegistro.setMovementMethod(LinkMovementMethod.getInstance());
        loginRegistro.setText(msgRegistroUsuario, TextView.BufferType.SPANNABLE);
        Spannable mySpannable = (Spannable)loginRegistro.getText();

        ClickableSpan myClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) { }
        };

        mySpannable.setSpan(myClickableSpan, i1, i2 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void initViews (){
        loginUsuario = findViewById(R.id.loginUsuario);
        loginSenha = findViewById(R.id.loginSenha);
        bntLogin = findViewById(R.id.btnLogin);
        loginRegistro = findViewById(R.id.loginRegistro);
    }

    protected void notificacao (String sMensagem){
        Context contexto = getApplicationContext();
        String textoNotificacao = sMensagem;
        int duracaoNotifacao = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(contexto, textoNotificacao, duracaoNotifacao);
        toast.show();
    }
}
