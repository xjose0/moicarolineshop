package com.example.moicaroline.ui.carrito;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moicaroline.database.CarritoRepository;
import com.example.moicaroline.database.Producto_BD;
import com.example.moicaroline.R;

import java.util.List;

public class AdaptadorBolsa extends RecyclerView.Adapter<AdaptadorBolsa.ViewHolder> {

    private List<Producto_BD> productos;
    private Context context;
    private CarritoRepository carritoRepository;

    public AdaptadorBolsa(Context context, List<Producto_BD> productos, CarritoRepository carritoRepository) {
        this.context = context;
        this.productos = productos;
        this.carritoRepository = carritoRepository;
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

        holder.txtNombre.setText(producto.getNombre());
        holder.txtPrecio.setText("$" + String.valueOf(producto.getPrecio()));
        holder.txtTalla.setText("Talla: " + producto.getTalla());
        holder.txtCantidad.setText(String.valueOf(producto.getCantidad()));

        Glide.with(context)
                .load(producto.getImagen())
                .centerCrop()
                .into(holder.imgProducto);

        holder.btnEliminarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {
                    Producto_BD producto = productos.get(position);
                    carritoRepository.deleteProducto(producto);
                    Toast.makeText(context.getApplicationContext(), "Producto eliminado", Toast.LENGTH_SHORT).show();
                    productos.remove(position);
                    notifyItemRemoved(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public void setProductos(List<Producto_BD> productos) {
        this.productos = productos;
    }

    public List<Producto_BD> getProductos() {
        return productos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNombre;
        private TextView txtPrecio;
        private ImageView imgProducto;
        private ImageButton btnEliminarProducto;
        private TextView txtTalla;
        private TextView txtCantidad;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombreProducto);
            txtPrecio = itemView.findViewById(R.id.txtPrecioProducto);
            imgProducto = itemView.findViewById(R.id.imgProducto);
            btnEliminarProducto = itemView.findViewById(R.id.btnEliminarProducto);
            txtTalla = itemView.findViewById(R.id.txtTalla);
            txtCantidad = itemView.findViewById(R.id.txtCantidad);
        }
    }
}



