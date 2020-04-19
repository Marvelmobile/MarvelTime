package br.digitalhouse.marveltime.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.model.Favoritos;
import br.digitalhouse.marveltime.view.activity.PersonagensTelaActivity;
import br.digitalhouse.marveltime.view.activity.RecebePerguntasQuizActivity;
import br.digitalhouse.marveltime.view.viewholder.ViewHolderFavoritos;
import static android.widget.Toast.LENGTH_SHORT;
import static br.digitalhouse.marveltime.util.Constantes.CHAVE_NOME;
import static br.digitalhouse.marveltime.util.Constantes.PERSONAGEM_KEY;

public class AdapterRecyclerFavoritos extends RecyclerView.Adapter<ViewHolderFavoritos> {
    private ArrayList<Favoritos> listaFavoritos;
    private Context mContext;

    public AdapterRecyclerFavoritos(ArrayList<Favoritos> listaFavoritos, Context mContext) {
        this.listaFavoritos = listaFavoritos;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolderFavoritos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_favoritos, parent, false);
        return new ViewHolderFavoritos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFavoritos holder, final int position) {
        if (listaFavoritos.get(position).getCardModelquestao() != null) {
            holder.image.setImageResource(listaFavoritos.get(position).getCardModelquestao().getImagem());
            holder.texto.setText(listaFavoritos.get(position).getCardModelquestao().getNome());
        }

        if (listaFavoritos.get(position).getPersonagemResult() != null) {
            String imageURL = listaFavoritos.get(position).getPersonagemResult().getThumbnail().getPath().replace("http://", "https://");
            Picasso.get().load(imageURL + "." + listaFavoritos.get(position).getPersonagemResult().getThumbnail().getExtension()).into(holder.image);
            holder.texto.setText(listaFavoritos.get(position).getPersonagemResult().getName());
        }

        holder.itemView.setOnClickListener(view -> {
            if (listaFavoritos.get(position).getCardModelquestao() != null) {
                Intent intent = new Intent(mContext, RecebePerguntasQuizActivity.class);
                intent.putExtra(CHAVE_NOME, verifica(listaFavoritos.get(position).getCardModelquestao().getNome()));
                mContext.startActivity(intent);
            }

            if (listaFavoritos.get(position).getPersonagemResult() != null) {
                Intent intent = new Intent(mContext, PersonagensTelaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(PERSONAGEM_KEY, (Parcelable) listaFavoritos.get(position));
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        holder.favoritado.setOnClickListener(view -> {
            Toast toast = Toast.makeText(mContext, holder.texto.getText().toString() + mContext.getString(R.string.msg_desfavoritado), LENGTH_SHORT);
            toast.show();
            removeAt(position);
        });
    }

    private String verifica(int n) {
        String nomeHeroi = null;
        if (n == R.string.quiz_homem_aranha) {
            nomeHeroi = "HA";
        }
        if (n == R.string.quiz_thor) {
            nomeHeroi = "TH";
        }
        if (n == R.string.quiz_homem_ferro) {
            nomeHeroi = "HF";
        }
        if (n == R.string.quiz_capitao) {
            nomeHeroi = "CA";
        }
        return nomeHeroi;
    }

    @Override
    public int getItemCount() {
        return listaFavoritos.size();
    }

    private void removeAt(int position) {
        listaFavoritos.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listaFavoritos.size());
    }
}