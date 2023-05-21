package com.example.moicaroline.ui.cuenta;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.moicaroline.databinding.FragmentCuentaBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class CuentaFragment extends Fragment {

    TextView correo, nombre;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    private FragmentCuentaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CuentaViewModel cuentaViewModel =
                new ViewModelProvider(this).get(CuentaViewModel.class);

        binding = FragmentCuentaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initComponents();

        if(currentUser.getDisplayName().isEmpty()){
            nombre.setText("");
        } else{
            nombre.setText(currentUser.getDisplayName());
        }
        correo.setText(currentUser.getEmail());

        nombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarAlertaNombre();
            }
        });

        correo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mostrarAlertaCorreo();
            }
        });

        //final TextView textView = binding.textGallery;
        //cuentaViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void mostrarAlertaNombre() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Ingresa el nuevo nombre");

        // Crea un EditText para que el usuario pueda ingresar su nuevo nombre
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(currentUser.getDisplayName());
        builder.setView(input);

        // Agrega los botones "Cancelar" y "Aceptar" a la alerta
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nuevoNombre = input.getText().toString().trim();
                if (!nuevoNombre.isEmpty()) {
                    // Actualiza el nombre del usuario en Firebase
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(nuevoNombre)
                            .build();
                    assert currentUser != null;
                    currentUser.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Actualiza el nombre en la vista
                                        nombre.setText(nuevoNombre);
                                    } else {
                                        // Muestra un mensaje de error si falla la actualización
                                        Toast.makeText(getContext(), "Error al actualizar el nombre", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Muestra la alerta
        builder.show();
    }


    private void initComponents() {
        correo = binding.tvEmail;
        nombre = binding.tvName;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}