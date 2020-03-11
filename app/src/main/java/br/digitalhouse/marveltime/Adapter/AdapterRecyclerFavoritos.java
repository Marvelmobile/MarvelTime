package br.digitalhouse.marveltime.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.digitalhouse.marveltime.Models.CardModel;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.Activitys.RecebePerguntasQuizActivity;
import br.digitalhouse.marveltime.Views.ViewHolderFavoritos;
import br.digitalhouse.marveltime.Views.ViewHolderQuiz;

import static android.widget.Toast.LENGTH_SHORT;

public class AdapterRecyclerFavoritos extends RecyclerView.Adapter<ViewHolderFavoritos> {
    private static final String TAG = "AdapterRecyclerQuiz";
    private ArrayList<CardModel> listaCards;
    private Context mContext;


    public AdapterRecyclerFavoritos(ArrayList<CardModel> listaCards, Context mContext) {
        this.listaCards = listaCards;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolderFavoritos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_favoritos, parent, false);
        ViewHolderFavoritos holder = new ViewHolderFavoritos(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFavoritos holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.image.setImageResource(listaCards.get(position).getImagem());
        holder.texto.setText(listaCards.get(position).getNome());

        holder.favoritado.setOnClickListener(view -> {
        Toast toast = Toast.makeText(mContext,  holder.texto.getText().toString() + " foi desfavoritado", LENGTH_SHORT);
        toast.show();
        removeAt(position);

        });
        holder.image.setOnClickListener(v -> {
            Log.d(TAG, "onClick: clicked");
            Intent intent= new Intent(mContext, RecebePerguntasQuizActivity.class);
            mContext.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return listaCards.size();
    }

    public void removeAt(int position) {
        listaCards.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listaCards.size());
    }
}



