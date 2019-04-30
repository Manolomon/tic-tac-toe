package com.manolomon.tictactoe;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    private RadioButton rbn_oneplayer;
    private RadioButton rbn_multiplayer;

    private RadioButton rbn_firstX;
    private RadioButton rbn_firstO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rbn_oneplayer = (RadioButton) findViewById(R.id.rbn_oneplayer);
        rbn_multiplayer = (RadioButton) findViewById(R.id.rbn_multiplayer);

        rbn_firstX = (RadioButton) findViewById(R.id.rbn_firstX);
        rbn_firstO = (RadioButton) findViewById(R.id.rbn_firstO);
    }

    public void clasificacion(View v){
        Intent intent = new Intent(this, Tabla_Clasificacion.class);
        startActivity(intent);
    }

    public void jugar(View v){
        String modoJuego;
        String jugadorInicial;
        if(rbn_oneplayer.isChecked()){
            modoJuego = "oneplayer";
            if(rbn_firstX.isChecked()){
                jugadorInicial = "X";
            }else{
                jugadorInicial = "O";
            }
        }else{
            modoJuego = "multiplayer";
            if(rbn_firstX.isChecked()){
                jugadorInicial = "X";
            }else{
                jugadorInicial = "O";
            }
        }
        Intent intent = new Intent(this, Jugar_Gato.class);
        intent.putExtra("modo_juego", modoJuego);
        intent.putExtra("jugador_inicial", jugadorInicial);
        startActivity(intent);
    }
}
