package br.digitalhouse.marveltime.view.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import br.digitalhouse.marveltime.view.activity.PersonagensTelaActivity;
import br.digitalhouse.marveltime.model.CardModel;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.view.viewholder.ViewHolderPersonagens;

public class AdapterRecyclerPersonagens extends RecyclerView.Adapter<ViewHolderPersonagens> {
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
        holder.image.setImageResource(listaCards.get(position).getImagem());
        holder.texto.setText(listaCards.get(position).getNome());
        holder.image.setOnClickListener(v -> {
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
