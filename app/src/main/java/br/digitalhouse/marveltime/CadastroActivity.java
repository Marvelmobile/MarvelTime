package br.digitalhouse.marveltime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

public class CadastroActivity extends AppCompatActivity {

    private TextInputLayout cadastrarNome;
    private TextInputLayout cadastrarEmail;
    private TextInputLayout cadastrarSenha;
    private TextInputLayout cadastrarSenhaConfirmacao;
    private FloatingActionButton bntCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

    }

}
