package br.digitalhouse.marveltime.view.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import br.digitalhouse.marveltime.model.CardModel;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.view.activity.RecebePerguntasQuizActivity;
import br.digitalhouse.marveltime.view.viewholder.ViewHolderQuiz;

import static br.digitalhouse.marveltime.util.Constantes.CHAVE_NOME;

public class AdapterRecyclerQuiz extends RecyclerView.Adapter<ViewHolderQuiz> {
    private ArrayList<CardModel> listaCards;
    private Context mContext;

    public AdapterRecyclerQuiz(Context mContext, ArrayList<CardModel> listaCards) {
        this.listaCards= listaCards;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolderQuiz onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_quiz, parent, false);
        return new ViewHolderQuiz(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderQuiz holder, final int position) {
        holder.image.setImageResource(listaCards.get(position).getImagem());
        holder.texto.setText(listaCards.get(position).getNome());
        holder.image.setOnClickListener(v -> {
              Intent intent= new Intent(mContext, RecebePerguntasQuizActivity.class);
            int position1= position;
            intent.putExtra(CHAVE_NOME,verifica(position));
            mContext.startActivity(intent);
        });

    }

    private String verifica(int n) {
        String nomeHeroi = null;
        if(n==0){nomeHeroi="HA";}
        if(n==1){nomeHeroi="TH";}
        if(n==2){nomeHeroi="HF";}
        if(n==3){nomeHeroi="CA";}
        return nomeHeroi;
    }

    @Override
    public int getItemCount() {
        return listaCards.size();
    }
}