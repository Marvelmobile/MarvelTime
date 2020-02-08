package br.digitalhouse.marveltime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout loginUsuario;
    private TextInputLayout loginSenha;
    private FloatingActionButton bntLogin;
    private TextView loginRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        bntLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usuario = loginUsuario.getEditText().getText().toString();
                String senha = loginSenha.getEditText().getText().toString();

                if (usuarioVazio(usuario) || senhaVazia(senha))
                    notificacao();
                else if (!usuarioValido(usuario) || !senhaValida(senha))
                    notificacaoInformacaoIncorreta();
                else
                    notificacaoParaProximaTela();
            }
        });

        linkCadastroUsuario();

    }


    //INGHRIDY SANTOS 04/02/20 - BDEV04 - TEXTVIEW COM LINK
    private void linkCadastroUsuario() {
        String msgRegistroUsuario = "Ainda não é membro? [Registre-se aqui]";
        int i1 = msgRegistroUsuario.indexOf("[");
        int i2 = msgRegistroUsuario.indexOf("]");

        loginRegistro.setMovementMethod(LinkMovementMethod.getInstance());
        loginRegistro.setText(msgRegistroUsuario, TextView.BufferType.SPANNABLE);
        Spannable mySpannable = (Spannable)loginRegistro.getText();

        ClickableSpan myClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {

                notificacaoParaProximaTela(); }
        };
        mySpannable.setSpan(myClickableSpan, i1, i2 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void initViews (){
        loginUsuario = findViewById(R.id.loginUsuario);
        loginSenha = findViewById(R.id.loginSenha);
        bntLogin = findViewById(R.id.btnLogin);
        loginRegistro = findViewById(R.id.loginRegistro);
    }


    //INGHRIDY SANTOS  - 04/02/20 - BDEV02 - VALIDAÇÃO USUARIO
    private boolean usuarioValido (String usuario){
        if (usuario.contains("@") && usuario.contains(".com") )
            return true;
        else
            return false;
    }

    private boolean usuarioVazio (String usuario){
        if (usuario.isEmpty())
            return true;
        else
            return false;
    }


    //INGHRIDY SANTOS  - 04/02/20 - BDEV03 - VALIDAÇÃO SENHA
    public static boolean senhaValida(String senha) {
        if (senha.length() < 6)
            return false;

        boolean achouNumero = false;
        boolean achouMaiuscula = false;
        boolean achouMinuscula = false;
        boolean achouSimbolo = false;

        for (char c : senha.toCharArray()) {
            if (c >= '0' && c <= '9')
                achouNumero = true;
            else if (c >= 'A' && c <= 'Z')
                achouMaiuscula = true;
            else if (c >= 'a' && c <= 'z')
                achouMinuscula = true;
            else
                achouSimbolo = true;
        }
        return achouNumero && achouMaiuscula && achouMinuscula && achouSimbolo;
    }

    private boolean senhaVazia (String senha){
        if (senha.isEmpty())
            return true;
        else
            return false;
    }

    //INGHRIDY SANTOS  - 04/02/20 - BDEV02 - NOTIFICAÇÕES

    protected void notificacao (){
        Context contexto = getApplicationContext();
        String textoNotificacao = "Por favor, preencha todos os campos";
        int duracaoNotifacao = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(contexto, textoNotificacao, duracaoNotifacao);
        toast.show();
    }

    protected void notificacaoInformacaoIncorreta (){
        Context contexto = getApplicationContext();
        String textoNotificacao = "Usuário ou senha não atendem as regras, por favor, tente novamente";
        int duracaoNotifacao = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(contexto, textoNotificacao, duracaoNotifacao);
        toast.show();
    }

    protected void notificacaoParaProximaTela (){
        Context contexto = getApplicationContext();
        String textoNotificacao = "PARABÉNS, em instantes você será direcionado a próxima tela.";
        int duracaoNotifacao = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(contexto, textoNotificacao, duracaoNotifacao);
        toast.show();
    }


}
