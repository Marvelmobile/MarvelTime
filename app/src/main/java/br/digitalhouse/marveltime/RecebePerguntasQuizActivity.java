package br.digitalhouse.marveltime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

public class RecebePerguntasQuizActivity extends AppCompatActivity implements Selecionavel {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recebe_perguntas_quiz);

        replaceFragments(R.id.container, new PerguntasQuizFragment());

    }

    @Override
    public void selecionar(int id) {

        if(id == R.id.fragment_layout_quiz_outra){
            replaceFragments(R.id.container, new OutraPerguntaQuizFragment());
        }
        if (id == R.id.fragment_layout_quiz){
            replaceFragments(R.id.container, new PerguntasQuizFragment());
        }

    }

    private void replaceFragments(int container, Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(container, fragment)
                .commit();
    }


}
