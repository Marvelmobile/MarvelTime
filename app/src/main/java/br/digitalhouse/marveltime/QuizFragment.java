package br.digitalhouse.marveltime;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment {

    private FloatingActionButton bntVoltar;
    private FloatingActionButton bntProximo;
    private MaterialButton alternativaUm;
    private MaterialButton alternativaDois;
    private MaterialButton alternativaTres;
    private MaterialButton alternativaQuatro;


    public QuizFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        initViews(view);

        return view;
    }

    private void initViews(View view){
        bntVoltar = view.findViewById(R.id.btnVoltar);
        bntProximo = view.findViewById(R.id.btnProximo);
        alternativaUm = view.findViewById(R.id.alternativaUm);
        alternativaDois = view.findViewById(R.id.alternativaDois);
        alternativaTres = view.findViewById(R.id.alternativaTres);
        alternativaQuatro = view.findViewById(R.id.alternativaQuatro);
    }

}
