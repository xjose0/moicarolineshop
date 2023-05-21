package com.example.moicaroline;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.FrameLayout;

import com.example.moicaroline.ui.carrito.CarritoFragment;
import com.example.moicaroline.ui.categorias.CategoriasFragment;
import com.example.moicaroline.ui.categoriaview.Producto;
import com.example.moicaroline.ui.colecciones.ColeccionesFragment;
import com.example.moicaroline.ui.producto.ProductoFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moicaroline.databinding.ActivityDrawerBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class DrawerActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDrawerBinding binding;
    private Fragment currentFragment;
    private ProductoFragment productoFragment;
    private CarritoFragment carritoFragment;
    private List<Producto> bolsaDeCompras = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isNetworkConnected()) {
            showNoInternetAlert();
        }


        // Mostrar el Fragment inicialmente visible
        currentFragment = new ColeccionesFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_categorias, currentFragment)
                .commit();

        binding = ActivityDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarDrawer.toolbar);





        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navigationView.getMenu().findItem(R.id.logoutFragment).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DrawerActivity.this, MainActivity.class));
                finish();
                return true;
            }
        });
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_drawer);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        binding.appBarDrawer.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.nav_slideshow);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_drawer);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /*public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (currentFragment instanceof CategoriasFragment) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout_categorias, new ColeccionesFragment())
                    .commit();
            currentFragment = new ColeccionesFragment();

            // Mostrar el RecyclerView correspondiente
            RecyclerView coleccionesRecyclerView = findViewById(R.id.recyclerCategorias);
            coleccionesRecyclerView.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (currentFragment instanceof CategoriasFragment) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout_categorias, new ColeccionesFragment())
                    .commit();
            currentFragment = new ColeccionesFragment();

            // Mostrar el RecyclerView correspondiente
            RecyclerView coleccionesRecyclerView = findViewById(R.id.recyclerCategorias);
            coleccionesRecyclerView.setVisibility(View.VISIBLE);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            // Si hay fragmentos en la pila de retroceso, regresar al fragmento anterior
            getSupportFragmentManager().popBackStack();

            // Mostrar el contenido del otro fragmento nuevamente
            findViewById(R.id.frameLayout_categorias).setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser usuario = mAuth.getCurrentUser();
        if (usuario == null){
            Intent intentMain = new Intent(DrawerActivity.this, MainActivity.class);
            startActivity(intentMain);
            finish();
        }
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    private void showNoInternetAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sin conexión a Internet");
        builder.setMessage("No se ha detectado una conexión a Internet. Por favor, comprueba tu conexión y vuelve a intentarlo.");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.show();
    }
}