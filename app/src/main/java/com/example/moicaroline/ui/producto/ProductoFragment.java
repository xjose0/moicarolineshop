package com.example.moicaroline.ui.producto;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moicaroline.database.CarritoRepository;
import com.example.moicaroline.database.Producto_BD;
import com.example.moicaroline.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ProductoFragment extends Fragment {
    private TextView txtNombreProducto;
    private TextView txtPrecioProducto;
    private TextView txtDescripcionProducto;
    private Button btnAgregarBolsa;
    //private List<Producto> bolsaDeCompras;
    private Spinner spinnerTallas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_producto, container, false);

        txtNombreProducto = view.findViewById(R.id.txtNombreProducto);
        txtPrecioProducto = view.findViewById(R.id.txtPrecioProducto);
        txtDescripcionProducto = view.findViewById(R.id.txtDescripcionProducto);
        btnAgregarBolsa = view.findViewById(R.id.btnAgregarBolsa);
        spinnerTallas = view.findViewById(R.id.spinnerTallas);
        //bolsaDeCompras = new ArrayList<>();

        Bundle bundle = getArguments();
        if (bundle != null) {
            String nombreProducto = bundle.getString("nombre_producto");
            String imagenProducto = bundle.getString("imagen_producto");
            String imagenGeometral = bundle.getString("imagen_geometral");
            String precioProducto = bundle.getString("precio_producto");
            String descripcionProducto = bundle.getString("descripcion_producto");

            descripcionProducto = descripcionProducto.replace("|", "\n");

            // Image Slider
            List<String> imagenes = new ArrayList<>();
            imagenes.add(imagenProducto);
            imagenes.add(imagenGeometral);

            txtNombreProducto.setText(nombreProducto);
            txtPrecioProducto.setText("$" + precioProducto);
            txtDescripcionProducto.setText(descripcionProducto);

            ViewPager2 viewPagerProducto = view.findViewById(R.id.viewPagerProducto);
            ImagenProductoAdapter adapter = new ImagenProductoAdapter(imagenes);
            viewPagerProducto.setAdapter(adapter);
        }

        btnAgregarBolsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    String userId = firebaseUser.getUid();

                    //Objeto Producto con los detalles del producto
                    Producto_BD producto = new Producto_BD();
                    producto.setNombre(bundle.getString("nombre_producto"));
                    producto.setPrecio(Double.parseDouble(bundle.getString("precio_producto")));
                    producto.setImagen(bundle.getString("imagen_producto"));
                    producto.setUserId(userId);
                    producto.setTalla(spinnerTallas.getSelectedItem().toString());

                    CarritoRepository carritoRepository = new CarritoRepository(requireContext());

                    // Insertar el producto en la base de datos a través del repositorio
                    carritoRepository.insertProducto(producto);

                    Toast.makeText(getContext(), "Producto agregado al carrito", Toast.LENGTH_SHORT).show();
                }
            }
        });




        return view;
    }

    /*public void setBolsaDeCompras(List<Producto> bolsaDeCompras) {
        this.bolsaDeCompras = bolsaDeCompras;
    } */
}

