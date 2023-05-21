package com.example.moicaroline.ui.categorias;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moicaroline.R;

import java.util.List;

public class CategoriasAdapter extends RecyclerView.Adapter<CategoriasAdapter.ViewHolder> {

    private List<Categoria> categorias;
    private OnCategoriaClickListener listener;

    public interface OnCategoriaClickListener {
        void onCategoriaClick(Categoria categoria);
    }

    public CategoriasAdapter(List<Categoria> categorias, OnCategoriaClickListener listener) {
        this.categorias = categorias;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_categoria2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Categoria categoria = categorias.get(position);
        holder.bind(categoria, listener);
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView categoriaImagen;
        private TextView categoriaNombre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoriaImagen = itemView.findViewById(R.id.imgCategoria);
            categoriaNombre = itemView.findViewById(R.id.categoria_nombre);
        }

        public void bind(Categoria categoria, OnCategoriaClickListener listener) {
            categoriaNombre.setText(categoria.getNombre());
            categoriaNombre.setText(categoria.getNombre());
            Glide.with(itemView.getContext())
                    .load(categoria.getImagen())
                    .centerCrop()
                    .into(categoriaImagen);
            itemView.setOnClickListener(v -> listener.onCategoriaClick(categoria));
        }
    }
}

