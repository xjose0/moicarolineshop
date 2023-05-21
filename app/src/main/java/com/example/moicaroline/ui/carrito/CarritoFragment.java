package com.example.moicaroline.ui.carrito;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moicaroline.database.CarritoRepository;
import com.example.moicaroline.database.Producto_BD;
import com.example.moicaroline.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarritoFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdaptadorBolsa adaptadorBolsa;
    private CarritoRepository carritoRepository;
    private TextView txtTotal;
    private double total = 0.0;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);

        recyclerView = view.findViewById(R.id.recyclerBolsa);
        Button btnRealizarCompra = view.findViewById(R.id.btnRealizarCompra);
        txtTotal = view.findViewById(R.id.txtTotal);

        // Inicializar el adaptador y el repositorio
        carritoRepository = new CarritoRepository(getContext());
        adaptadorBolsa = new AdaptadorBolsa(getContext(), new ArrayList<>(), carritoRepository);

        // Configurar el RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptadorBolsa);

        // Configurar el clic del botón "Realizar compra"
        btnRealizarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Realizar compra
            }
        });

        // Observar los cambios en los productos del carrito
        adaptadorBolsa.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                calcularTotal();
                if(recyclerView.getAdapter().getItemCount() > 0){
                    btnRealizarCompra.setVisibility(View.VISIBLE);
                } else{
                    btnRealizarCompra.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        String userId = firebaseUser.getUid();

        // Obtener los productos del carrito desde la base de datos local
        carritoRepository.getAllProductos(userId).observe(getViewLifecycleOwner(), new Observer<List<Producto_BD>>() {
            @Override
            public void onChanged(List<Producto_BD> productos) {
                // Agrupar los productos por nombre y contar la cantidad
                Map<String, Producto_BD> productosAgrupados = new HashMap<>();
                for (Producto_BD producto : productos) {
                    String nombre = producto.getNombre();
                    if (productosAgrupados.containsKey(nombre)) {
                        Producto_BD productoExistente = productosAgrupados.get(nombre);
                        productoExistente.setCantidad(productoExistente.getCantidad() + 1);
                    } else {
                        producto.setCantidad(1);
                        productosAgrupados.put(nombre, producto);
                    }
                }

                // Actualizar la lista de productos en el adaptador
                adaptadorBolsa.setProductos(new ArrayList<>(productosAgrupados.values()));
                adaptadorBolsa.notifyDataSetChanged();
                calcularTotal();
            }
        });
    }


    private void calcularTotal() {

        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String totalFormatted = decimalFormat.format(total);
        total = 0.0;

        for (Producto_BD producto : adaptadorBolsa.getProductos()) {
            double precioProducto = producto.getPrecio();
            int cantidad = producto.getCantidad();
            total += precioProducto * cantidad;
        }

        if (total > 0){
            txtTotal.setText("Total: $" + totalFormatted);
        } else{
            txtTotal.setText("");
        }

    }
}




