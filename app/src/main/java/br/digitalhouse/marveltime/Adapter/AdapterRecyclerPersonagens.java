package br.digitalhouse.marveltime.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.digitalhouse.marveltime.Activitys.MainActivity;
import br.digitalhouse.marveltime.Activitys.PersonagensTelaActivity;
import br.digitalhouse.marveltime.Activitys.RecebePerguntasQuizActivity;
import br.digitalhouse.marveltime.Models.CardModel;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.Views.ViewHolderFavoritos;
import br.digitalhouse.marveltime.Views.ViewHolderPersonagens;

public class AdapterRecyclerPersonagens extends RecyclerView.Adapter<ViewHolderPersonagens> {

    private static final String TAG = "AdapterPersonagens";
    private ArrayList<CardModel> listaCards;
    private Context mContext;

    public AdapterRecyclerPersonagens(ArrayList<CardModel> listaCards, Context mContext) {
        this.listaCards = listaCards;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolderPersonagens onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_personagens, parent, false);
        ViewHolderPersonagens holder = new ViewHolderPersonagens(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPersonagens holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.image.setImageResource(listaCards.get(position).getImagem());
        holder.texto.setText(listaCards.get(position).getNome());
        holder.image.setOnClickListener(v -> {
            Log.d(TAG, "onClick: clicked");
            Intent intent= new Intent(mContext, PersonagensTelaActivity.class);
            intent.putExtra("IMAGEM",listaCards.get(position).getImagem());
            intent.putExtra("NOME",listaCards.get(position).getNome());

            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaCards.size();
    }
}
