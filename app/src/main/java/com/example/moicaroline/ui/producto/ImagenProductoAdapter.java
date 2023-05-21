package com.example.moicaroline.ui.producto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moicaroline.R;

import java.util.List;

public class ImagenProductoAdapter extends RecyclerView.Adapter<ImagenProductoAdapter.ImagenViewHolder> {

    private List<String> imagenes;

    public ImagenProductoAdapter(List<String> imagenes) {
        this.imagenes = imagenes;
    }

    @NonNull
    @Override
    public ImagenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_imagen_producto, parent, false);
        return new ImagenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagenViewHolder holder, int position) {
        String imagenUrl = imagenes.get(position);
        Glide.with(holder.itemView.getContext())
                .load(imagenUrl)
                .into(holder.imageViewProducto);
    }

    @Override
    public int getItemCount() {
        return imagenes.size();
    }

    public class ImagenViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewProducto;

        public ImagenViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProducto = itemView.findViewById(R.id.imageViewProducto);
        }
    }
}

