package com.example.moicaroline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrarseActivity extends AppCompatActivity {

    EditText txtContra, txtCorreo, txtRepetirContra;
    Button btnRegistrarse;
    private String correo, contra, repetirContra;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        setTitle("Registrarse");
        initComponents();

        mAuth = FirebaseAuth.getInstance();

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo = txtCorreo.getText().toString().trim();
                contra = txtContra.getText().toString().trim();
                repetirContra = txtRepetirContra.getText().toString().trim();
                
                if(correo.isEmpty() || contra.isEmpty() || repetirContra.isEmpty()){
                    Toast.makeText(RegistrarseActivity.this, "Datos inválidos", Toast.LENGTH_SHORT).show();
                } else if(!correo.isEmpty() && !contra.isEmpty() && !repetirContra.isEmpty()){
                    if(emailvalido(correo)){
                        if(contra.equals(repetirContra)){
                            if(repetirContra.length() > 6){
                                mAuth.createUserWithEmailAndPassword(correo, contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(RegistrarseActivity.this, "Se creó la cuenta correctamente", Toast.LENGTH_SHORT).show();
                                            Intent intentMain = new Intent(RegistrarseActivity.this, MainActivity.class);
                                            startActivity(intentMain);
                                            finish();
                                        } else{
                                            Toast.makeText(RegistrarseActivity.this, "La cuenta ya existe", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else{
                                Toast.makeText(RegistrarseActivity.this, "La contraseña debe ser mayor a 6 caracteres", Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            Toast.makeText(RegistrarseActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        Toast.makeText(RegistrarseActivity.this, "Correo inválido", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void initComponents() {
        txtCorreo = findViewById(R.id.txtCorreo);
        txtContra = findViewById(R.id.txtContra);
        txtRepetirContra = findViewById(R.id.txtRepetirContra);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
    }

    private boolean emailvalido(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}