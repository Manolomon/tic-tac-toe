package com.manolomon.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Tabla_Clasificacion extends AppCompatActivity {

    private Integer noJuegosSolo;
    private Integer noJuegosDos;
    private Integer noMinimoMov;
    private Integer noJuegosGanados;
    private Integer noJuegosSinGanador;
    private List<String> estadisticas;
    ListView stats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla_clasificacion);
        this.stats = (ListView) findViewById(R.id.notasLista);
        this.cargarEstadisticas();
        this.mostrarEstadisticas();
    }


    private void cargarEstadisticas() {
        this.estadisticas = new ArrayList<String>();
        SharedPreferences sp = getSharedPreferences("ESTADISTICAS_GATO", Context.MODE_PRIVATE);
        this.noMinimoMov = Integer.parseInt(sp.getString("noMinMovimientos", "0"));
        this.noJuegosSolo = Integer.parseInt(sp.getString("noJuegosSolo", "0"));
        this.noJuegosDos = Integer.parseInt(sp.getString("noJuegosDos", "0"));
        this.noJuegosGanados = Integer.parseInt(sp.getString("noJuegosGanados", "0"));
        this.noJuegosSinGanador = Integer.parseInt(sp.getString("noJuegosSinGanador", "0"));
        this.estadisticas.add(
                "No juegos Solo: "+this.noJuegosSolo
        );
        this.estadisticas.add(
                "No juegos 2 personas: "+this.noJuegosDos
        );
        this.estadisticas.add(
                "Record minimo movimientos: "+this.noMinimoMov
        );
        this.estadisticas.add(
                "No Juegos ganados: "+this.noJuegosGanados
        );
        this.estadisticas.add(
                "No juegos Sin Ganador: "+this.noJuegosSinGanador
        );
    }

    private void mostrarEstadisticas(){
        ArrayAdapter adapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,
                        estadisticas);
        this.stats.setAdapter(adapter);
    }

    public void regresarAMenu(View v){
        finish();
    }
}
