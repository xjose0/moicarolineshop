package com.example.moicaroline.ui.categoriaview;

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

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder> {

    private List<Producto> productos;
    private OnItemClickListener listener;

    public ProductosAdapter(List<Producto> productos) {
        this.productos = productos;
    }

    public ProductosAdapter(List<Producto> productos, OnItemClickListener listener) {
        this.productos = productos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_productos, parent, false);
        return new ProductoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = productos.get(position);
        holder.bind(producto, listener);
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Producto producto);
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNombreProducto;
        private ImageView imageViewProducto;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreProducto = itemView.findViewById(R.id.nombre_producto);
            imageViewProducto = itemView.findViewById(R.id.imgProducto);
        }

        public void bind(final Producto producto, final OnItemClickListener listener) {
            txtNombreProducto.setText(producto.getNombre());
            Glide.with(itemView.getContext())
                    .load(producto.getImagen())
                    .into(imageViewProducto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(producto);
                }
            });
        }
    }
}


