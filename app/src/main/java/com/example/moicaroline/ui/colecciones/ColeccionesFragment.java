package com.example.moicaroline.ui.colecciones;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moicaroline.DrawerActivity;
import com.example.moicaroline.R;
import com.example.moicaroline.databinding.FragmentColeccionesBinding;
import com.example.moicaroline.ui.categorias.CategoriasFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ColeccionesFragment extends Fragment {

    private FragmentColeccionesBinding binding;
    private ColeccionesAdapter coleccionesAdapter;
    private ProgressBar progressBar;
    //private View overlayView;
    private FrameLayout frameLayoutBanner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentColeccionesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerCategorias;
        progressBar = binding.progressBar;
        //overlayView = binding.overlayView;
        frameLayoutBanner = binding.framelayoutBanner;

        LinearLayoutManager layoutManager = null;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new LinearLayoutManager(getActivity());
        } else {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        }
        recyclerView.setLayoutManager(layoutManager);

        TextView textView = binding.txtBanner;

        Calendar fechaLimite = Calendar.getInstance();
        fechaLimite.set(2024, Calendar.DECEMBER, 10, 0, 0, 0);
        long fechaLimiteMillis = fechaLimite.getTimeInMillis();
        long tiempoRestanteMillis = fechaLimiteMillis - System.currentTimeMillis();

        ContadorTiempo contadorTiempo = new ContadorTiempo(tiempoRestanteMillis, 1000, textView);
        contadorTiempo.start();


        FrameLayout frameLayout = binding.frameLayoutCategorias;

        List<Coleccion> coleccions = new ArrayList<>();

        frameLayoutBanner.setVisibility(View.GONE);
        //overlayView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("colecciones");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot categoriaSnapshot : snapshot.getChildren()) {
                    Coleccion coleccion = categoriaSnapshot.getValue(Coleccion.class);
                    if (!coleccions.contains(coleccion)) {
                        coleccions.add(coleccion);
                    }
                }
                coleccionesAdapter.notifyDataSetChanged();
                //overlayView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                frameLayoutBanner.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });


        coleccionesAdapter = new ColeccionesAdapter(coleccions, coleccion -> {

            FrameLayout bannerLayout = ((DrawerActivity) getContext()).findViewById(R.id.framelayoutBanner);
            bannerLayout.setVisibility(View.GONE);

            // Obtener referencia al RecyclerView que muestra la lista de colecciones
            RecyclerView coleccionesRecyclerView = ((DrawerActivity) getContext()).findViewById(R.id.recyclerCategorias);

            // Ocultar el RecyclerView
            coleccionesRecyclerView.setVisibility(View.GONE);

            // Crea un nuevo Fragment de Categorías y agrégalo al contenedor
            CategoriasFragment categoriasFragment = new CategoriasFragment();
            Bundle args = new Bundle();
            args.putString("coleccion", coleccion.getNombre());
            categoriasFragment.setArguments(args);

            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frameLayout_categorias, categoriasFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        });
        recyclerView.setAdapter(coleccionesAdapter);

        return root;
    }

    @SuppressLint("DefaultLocale")
    private String obtenerTiempoRestante(long fechaLimiteMillis) {
        // Calcula la diferencia de tiempo entre la fecha actual y la fecha límite
        long tiempoRestanteMillis = fechaLimiteMillis - System.currentTimeMillis();
        if (tiempoRestanteMillis < 0) {
            tiempoRestanteMillis = 0;
        }

        // Convierte la diferencia de tiempo en días, horas, minutos y segundos
        long dias = TimeUnit.MILLISECONDS.toDays(tiempoRestanteMillis);
        tiempoRestanteMillis -= TimeUnit.DAYS.toMillis(dias);
        long horas = TimeUnit.MILLISECONDS.toHours(tiempoRestanteMillis);
        tiempoRestanteMillis -= TimeUnit.HOURS.toMillis(horas);
        long minutos = TimeUnit.MILLISECONDS.toMinutes(tiempoRestanteMillis);
        tiempoRestanteMillis -= TimeUnit.MINUTES.toMillis(minutos);
        long segundos = TimeUnit.MILLISECONDS.toSeconds(tiempoRestanteMillis);

        // Formatea la diferencia de tiempo como una cadena
        return String.format("%02d:%02d:%02d:%02d", dias, horas, minutos, segundos);
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

