package com.example.moicaroline.ui.colecciones;

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

public class ColeccionesAdapter extends RecyclerView.Adapter<ColeccionesAdapter.ViewHolder> {

    private List<Coleccion> coleccions;
    private OnCategoriaClickListener listener;

    public interface OnCategoriaClickListener {
        void onCategoriaClick(Coleccion coleccion);
    }

    public ColeccionesAdapter(List<Coleccion> coleccions, OnCategoriaClickListener listener) {
        this.coleccions = coleccions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_categoria, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Coleccion coleccion = coleccions.get(position);
        holder.bind(coleccion, listener);
    }

    @Override
    public int getItemCount() {
        return coleccions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView categoriaImagen;
        private TextView categoriaNombre;
        private TextView categoriaDescripcion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoriaImagen = itemView.findViewById(R.id.imgCategoria);
            categoriaNombre = itemView.findViewById(R.id.categoria_nombre);
            categoriaDescripcion = itemView.findViewById(R.id.categoria_descripcion);
        }

        public void bind(Coleccion coleccion, OnCategoriaClickListener listener) {
            categoriaNombre.setText(coleccion.getNombre());
            categoriaDescripcion.setText(coleccion.getDescripcion());
            Glide.with(itemView.getContext())
                    .load(coleccion.getImagen())
                    .centerCrop()
                    .into(categoriaImagen);
            itemView.setOnClickListener(v -> listener.onCategoriaClick(coleccion));
        }
    }
}

