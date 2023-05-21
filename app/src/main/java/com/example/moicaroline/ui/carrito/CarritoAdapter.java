package com.example.moicaroline.ui.carrito;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moicaroline.database.Producto_BD;
import com.example.moicaroline.R;

import java.util.List;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.ViewHolder> {
    private List<Producto_BD> productos;

    public void setProductos(List<Producto_BD> productos) {
        this.productos = productos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.producto_carrito, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Producto_BD producto = productos.get(position);
        // Configurar los datos del producto en el ViewHolder
        holder.txtNombreProducto.setText(producto.getNombre());
        holder.txtPrecioProducto.setText(String.valueOf(producto.getPrecio()));
    }

    @Override
    public int getItemCount() {
        return productos != null ? productos.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNombreProducto;
        private TextView txtPrecioProducto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreProducto = itemView.findViewById(R.id.txtNombreProducto);
            txtPrecioProducto = itemView.findViewById(R.id.txtPrecioProducto);
        }
    }
}
