package com.manolomon.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

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

    public void clasificacion(View v) {
        Intent intent = new Intent(this, Tabla_Clasificacion.class);
        startActivity(intent);
    }


    public void jugar(View v) {
        Intent i = new Intent(this, Jugar_Gato.class);
        String tipoJuego;
        boolean comienzaJ1;
        if (this.rbn_oneplayer.isChecked()) {
            tipoJuego = "Solo";
        } else if (rbn_multiplayer.isChecked()) {
            tipoJuego = "Multijugador";
        } else {
            Toast.makeText(this,
                    "Porfavor seleccione el modo de juego",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (this.rbn_firstX.isChecked()) {
            comienzaJ1 = true;
        } else if (this.rbn_firstO.isChecked()) {
            comienzaJ1 = false;
        } else {
            Toast.makeText(this,
                    "Porfavor seleccione su tipo de marca",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        i.putExtra("tipoJuego", tipoJuego);
        i.putExtra("comienzaJ1", comienzaJ1);
        startActivity(i);
    }
}
