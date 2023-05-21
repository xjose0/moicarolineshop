package com.example.moicaroline.ui.categorias;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.moicaroline.DrawerActivity;
import com.example.moicaroline.R;
import com.example.moicaroline.databinding.FragmentCategoriasBinding;
import com.example.moicaroline.ui.categoriaview.CategoriaViewFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class CategoriasFragment extends Fragment {

    private FragmentCategoriasBinding binding;
    private CategoriasAdapter categoriasAdapter;
    private String coleccion;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_colecciones, container, false);

        // Obtener referencia al RecyclerView que muestra la lista de colecciones
        AtomicReference<RecyclerView> coleccionesRecyclerView = new AtomicReference<>(view.findViewById(R.id.recyclerCategorias));

        // Ocultar el RecyclerView
        coleccionesRecyclerView.get().setVisibility(View.GONE);

        binding = FragmentCategoriasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        progressBar = binding.progressBar;
        RecyclerView recyclerView = binding.recyclerCategoriasColeccion;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        TextView txtDescripcion = binding.txtDescripcion;
        ImageView imgCategoria = binding.imgCategoria;

        List<Categoria> categorias = new ArrayList<>();



        // Obtener la colección seleccionada de los argumentos del fragment
        if (getArguments() != null) {
            coleccion = getArguments().getString("coleccion");
        }

        if(coleccion.equals("Fleurs d'Antoinette")){
            imgCategoria.setImageResource(R.drawable.fleursdanto);
            txtDescripcion.setText(R.string.descripcionAntonieta);
        } else if (coleccion.equals("Batalla de las flores")) {
            imgCategoria.setImageResource(R.drawable.batalladelasflores);
            txtDescripcion.setText(R.string.descripcionBatalla);
        }

        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("categorias");
        Query query = databaseReference.orderByChild("coleccion").equalTo(coleccion);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot categoriaSnapshot : snapshot.getChildren()) {
                    Categoria categoria = categoriaSnapshot.getValue(Categoria.class);
                    categorias.add(categoria);
                }
                Collections.sort(categorias, new Comparator<Categoria>() {
                    @Override
                    public int compare(Categoria c1, Categoria c2) {
                        return c1.getNombre().compareToIgnoreCase(c2.getNombre());
                    }
                });
                categoriasAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });

        categoriasAdapter = new CategoriasAdapter(categorias, categoria -> {

            coleccionesRecyclerView.set(((DrawerActivity) getContext()).findViewById(R.id.recyclerCategorias));
            coleccionesRecyclerView.get().setVisibility(View.GONE);

            Bundle bundle = new Bundle();
            bundle.putString("nombre_categoria", categoria.getNombre());
            bundle.putString("imagen_categoria", categoria.getImagen());

            FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            CategoriaViewFragment categoriasViewFragment = new CategoriaViewFragment();
            categoriasViewFragment.setArguments(bundle);
            transaction.replace(R.id.frameLayout_categorias, categoriasViewFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
        recyclerView.setAdapter(categoriasAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
