package br.digitalhouse.marveltime.view.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import br.digitalhouse.marveltime.R;
import br.digitalhouse.marveltime.model.Favoritos;
import br.digitalhouse.marveltime.view.Interfaces.OnClickFavoritos;
import br.digitalhouse.marveltime.view.viewholder.ViewHolderFavoritos;

public class AdapterRecyclerFavoritos extends RecyclerView.Adapter<ViewHolderFavoritos> {
    private List<Favoritos> listaFavoritos;
    private OnClickFavoritos clickFavoritos;

    public AdapterRecyclerFavoritos(List<Favoritos> listaFavoritos,  OnClickFavoritos clickFavoritos) {
        this.listaFavoritos = listaFavoritos;
        this.clickFavoritos = clickFavoritos;
    }

    @NonNull
    @Override
    public ViewHolderFavoritos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_favoritos, parent, false);
        return new ViewHolderFavoritos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFavoritos viewHolder, int position) {
        Favoritos favoritos = listaFavoritos.get(position);
        viewHolder.bind(favoritos);
        viewHolder.imgSetaFavorito.setOnClickListener(v -> clickFavoritos.abrirFavoritoClickListener(favoritos));
        viewHolder.imageFavoritado.setOnClickListener(v -> clickFavoritos.abrirFavoritoClickListener(favoritos));
        viewHolder.desfavoritar.setOnClickListener(v -> clickFavoritos.removeFavoritoClickListener(favoritos));
    }

    public void removeItem(Favoritos favoritos){
        this.listaFavoritos.remove(favoritos);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaFavoritos.size();
    }

    public void atualizaLista(List<Favoritos> novaLista) {
        if (this.listaFavoritos.isEmpty()) {
            this.listaFavoritos = novaLista;
        } else {
            this.listaFavoritos.addAll(novaLista);
        }
        notifyDataSetChanged();
    }
}