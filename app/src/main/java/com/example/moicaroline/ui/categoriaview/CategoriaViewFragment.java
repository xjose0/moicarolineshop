package com.example.moicaroline.ui.categoriaview;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moicaroline.R;
import com.example.moicaroline.ui.categorias.CategoriasAdapter;
import com.example.moicaroline.ui.producto.ProductoFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoriaViewFragment extends Fragment {

    private ImageView bannerImageView;
    private TextView txtCategoria;
    private RecyclerView rvProductos;
    private ProductosAdapter productosAdapter;
    private List<Producto> productos;
    private ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categoriaview, container, false);

        bannerImageView = view.findViewById(R.id.banner_image_view);
        txtCategoria = view.findViewById(R.id.txtCategoria);
        rvProductos = view.findViewById(R.id.rv_productos);
        progressBar = view.findViewById(R.id.progressBar);

        // Obtener los parámetros enviados desde CategoriasFragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            String nombreCategoria = bundle.getString("nombre_categoria");
            String imagenCategoria = bundle.getString("imagen_categoria");

            // Hacer la consulta a Firebase para obtener la URL del banner correspondiente a la categoría seleccionada
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("categorias");
            Query query = databaseRef.orderByChild("nombre").equalTo(nombreCategoria);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String bannerUrl = snapshot.child("banner").getValue(String.class);

                        txtCategoria.setText(nombreCategoria);
                        Glide.with(getContext())
                                .load(bannerUrl)
                                .into(bannerImageView);

                        progressBar.setVisibility(View.GONE);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("CategoriaViewFragment", "Error al obtener la URL del banner: " + databaseError.getMessage());
                }
            });

            progressBar.setVisibility(View.VISIBLE);
            // Obtener los productos de la categoría seleccionada
            DatabaseReference productosRef = FirebaseDatabase.getInstance().getReference("productos");
            Query productosQuery = productosRef.orderByChild("categoria").equalTo(nombreCategoria);
            productosQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    productos = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Producto producto = snapshot.getValue(Producto.class);
                        productos.add(producto);
                    }
                    productosAdapter = new ProductosAdapter(productos);
                    rvProductos.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    productosAdapter = new ProductosAdapter(productos, new ProductosAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Producto producto) {
                            // Crea un nuevo fragmento de ProductoViewFragment
                            ProductoFragment productoViewFragment = new ProductoFragment();

                            // Pasa los datos del producto al fragmento
                            Bundle bundle = new Bundle();
                            bundle.putString("nombre_producto", producto.getNombre());
                            bundle.putString("imagen_producto", producto.getImagen());
                            bundle.putString("precio_producto", producto.getPrecio());
                            bundle.putString("descripcion_producto", producto.getDescripcion());
                            bundle.putString("imagen_geometral", producto.getImagen_geometral());
                            productoViewFragment.setArguments(bundle);

                            // Abre el fragmento de ProductoViewFragment
                            FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.frameLayout_categorias, productoViewFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    });

                    rvProductos.setAdapter(productosAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("CategoriaViewFragment", "Error al obtener los productos: " + databaseError.getMessage());
                }
            });
        }

        return view;
    }
}

