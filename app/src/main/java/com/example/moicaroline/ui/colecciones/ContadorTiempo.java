package com.example.moicaroline.ui.colecciones;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class ContadorTiempo extends CountDownTimer {
    private long fechaLimiteMillis;
    private TextView textView;

    public ContadorTiempo(long millisInFuture, long countDownInterval, TextView textView) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
        fechaLimiteMillis = System.currentTimeMillis() + millisInFuture;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        fechaLimiteMillis = System.currentTimeMillis() + millisUntilFinished;
        textView.setText(obtenerTiempoRestante(fechaLimiteMillis));
    }

    @Override
    public void onFinish() {
        textView.setText("Ofertas limitadas caducadas");
    }

    private String obtenerTiempoRestante(long fechaLimiteMillis) {
        // Calcula la diferencia de tiempo entre la fecha actual y la fecha límite
        long tiempoRestanteMillis = fechaLimiteMillis - System.currentTimeMillis();
        if (tiempoRestanteMillis < 0) {
            tiempoRestanteMillis = 0;
        }

        // Convierte la diferencia de tiempo en meses, días, horas, minutos y segundos
        long dias = TimeUnit.MILLISECONDS.toDays(tiempoRestanteMillis) % 30;
        long horas = TimeUnit.MILLISECONDS.toHours(tiempoRestanteMillis) % 24;
        long minutos = TimeUnit.MILLISECONDS.toMinutes(tiempoRestanteMillis) % 60;
        long segundos = TimeUnit.MILLISECONDS.toSeconds(tiempoRestanteMillis) % 60;

        // Formatea la diferencia de tiempo como una cadena
        return String.format("¡Colecciones disponibles por tiempo limitado!\nAprovecha ofertas limitadas \n%02d:%02d:%02d:%02d", dias, horas, minutos, segundos);
    }
}
