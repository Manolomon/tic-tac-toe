package com.manolomon.tictactoe;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.concurrent.locks.Lock;

public class Jugar_Gato extends AppCompatActivity {

    private String[] combinacionesGanadoras;
    private List<Integer> totalTiros;
    private String turno;
    private boolean comienzaJ1;
    private String tipoJuego;
    private boolean comprobado;
    private Button btn_supIzq;
    private Button btn_supMed;
    private Button btn_supDer;
    private Button btn_medIzq;
    private Button btn_medMed;
    private Button btn_medDer;
    private Button btn_infIzq;
    private Button btn_infMed;
    private Button btn_infDer;
    private TextView lbl_jugador;

    private List<Button> botones = new ArrayList<>();
    private String tirosJ1;
    private String tirosJ2;
    private Integer noMovimientos;
    private boolean gano;
    private boolean sinGanador;
    private boolean juegoTerminado;
    private Integer noJuegosSolo;
    private Integer noJuegosDos;
    private Integer noMinimoMov;
    private Integer noJuegosGanados;
    private Integer noJuegosSinGanador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugar_gato);

        this.lbl_jugador = (TextView) findViewById(R.id.lbl_jugador);

        this.botones.add(
                this.btn_supIzq = (Button) findViewById(R.id.btn_supIzq)
        );
        this.botones.add(
                this.btn_supMed = (Button) findViewById(R.id.btn_supMed)
        );
        this.botones.add(
                this.btn_supDer = (Button) findViewById(R.id.btn_supDer)
        );
        this.botones.add(
                this.btn_medIzq = (Button) findViewById(R.id.btn_medIzq)
        );
        this.botones.add(
                this.btn_medMed = (Button) findViewById(R.id.btn_medMed)
        );
        this.botones.add(
                this.btn_medDer = (Button) findViewById(R.id.btn_medDer)
        );
        this.botones.add(
                this.btn_infIzq = (Button) findViewById(R.id.btn_infIzq)
        );
        this.botones.add(
                this.btn_infMed = (Button) findViewById(R.id.btn_infMed)
        );
        this.botones.add(
                this.btn_infDer = (Button) findViewById(R.id.btn_infDer)
        );

        this.totalTiros = new ArrayList<>();

        this.tirosJ2 = "";
        this.tirosJ1 = "";
        this.tipoJuego = "";
        Log.v("TamanioJ2", tirosJ2.length() + "");
        Log.v("TamanioJ1", tirosJ1.length() + "");
        this.gano = false;
        this.sinGanador = false;
        this.juegoTerminado = false;
        this.noMovimientos = 0;
        Resources resources = getResources();
        this.combinacionesGanadoras = resources.getStringArray(R.array.combinaciones_ganadoras);
        Log.v("NUMEROCOMBINACIONES", "" + combinacionesGanadoras.length);
        this.cargarEstadisticas();
        this.cargarDatos();
    }

    private void cargarEstadisticas() {
        SharedPreferences sp = getSharedPreferences("ESTADISTICAS_GATO", Context.MODE_PRIVATE);
        this.noMinimoMov = Integer.parseInt(sp.getString("noMinMovimientos", "0"));
        this.noJuegosSolo = Integer.parseInt(sp.getString("noJuegosSolo", "0"));
        this.noJuegosDos = Integer.parseInt(sp.getString("noJuegosDos", "0"));
        this.noJuegosGanados = Integer.parseInt(sp.getString("noJuegosGanados", "0"));
        this.noJuegosSinGanador = Integer.parseInt(sp.getString("noJuegosSinGanador", "0"));

    }

    private void cargarDatos() {
        Intent intent = getIntent();
        this.tipoJuego = intent.getStringExtra("tipoJuego");
        this.comienzaJ1 = intent.getBooleanExtra("comienzaJ1", false);
        Log.v("Tipo de juego", "" + this.tipoJuego);
        this.turno = "J1";
        this.lbl_jugador.setText(turno.trim());
        if (this.tipoJuego.equals("Solo") && !this.comienzaJ1) {
            tiraMaquina();
        }
    }

    private void tiraMaquina() {
        Random rg = new Random(System.currentTimeMillis());
        boolean fullfilled = false;
        Log.v("TIROSJ1:", this.tirosJ1);
        Log.v("TIROSJ2:", this.tirosJ2);
        do {
            int proximoTiro = rg.nextInt(9) + 1;
            if (this.buscarEnTiros(proximoTiro)) {
                Button aux = this.botones.get(proximoTiro - 1);
                if (turno.equals("J1")) {
                    this.tirosJ1 += Integer.toString(proximoTiro) + ",";
                    this.totalTiros.add(proximoTiro);
                    aux.setText("X");
                    aux.setEnabled(false);
                } else {
                    this.tirosJ2 += Integer.toString(proximoTiro) + ",";
                    this.totalTiros.add(proximoTiro);
                    aux.setText("O");
                    aux.setEnabled(false);
                }
                fullfilled = true;
            }
        } while (!fullfilled);

        String tirosTotalJ1[] = this.tirosJ1.split(",");
        String tirosTotalJ2[] = this.tirosJ2.split(",");

        if (tirosTotalJ2.length >= 3 || tirosTotalJ1.length >= 3) {
            comprobado = comprobarTablero();

            if (juegoTerminado) {
                if (comprobado) {
                    this.sinGanador = false;
                    this.gano = false;
                    guardarEstadisticas();
                    AlertDialog mensaje = new AlertDialog.Builder(Jugar_Gato.this).create();
                    mensaje.setTitle("Derrota");
                    mensaje.setMessage(String.format("PERDISTE :("));
                    mensaje.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                    mensaje.setCancelable(false);
                    mensaje.setCanceledOnTouchOutside(false);
                    mensaje.show();
                } else if (this.sinGanador) {
                    guardarEstadisticas();
                    AlertDialog mensaje = new AlertDialog.Builder(Jugar_Gato.this).create();
                    mensaje.setTitle("Sin Ganador");
                    mensaje.setMessage(String.format("GATO GARABATO!!!!! :P"));
                    mensaje.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                    mensaje.setCancelable(false);
                    mensaje.setCanceledOnTouchOutside(false);
                    mensaje.show();
                }
            }
        }

        if (this.turno.equals("J1")) {
            this.turno = "J2";
        } else {
            this.turno = "J1";
        }
        this.lbl_jugador.setText(turno.trim());
        return;
    }

    private boolean comprobarTablero() {
        boolean res = true;
        String tirosTotalJ1[] = this.tirosJ1.split(",");
        String tirosTotalJ2[] = this.tirosJ2.split(",");

        Integer noCasillas = tirosTotalJ1.length + tirosTotalJ2.length;
        for (int x = 0; x < this.combinacionesGanadoras.length; x++) {
            res = true;
            String combinacionAux[] = this.combinacionesGanadoras[x].split(",");
            for (int y = 0; y < combinacionAux.length; y++) {
                if (this.turno.equals("J1")) {
                    if (!this.tirosJ1.contains(combinacionAux[y])) {
                        res = false;
                    }
                } else if (this.turno.equals("J2")) {
                    if (!this.tirosJ2.contains(combinacionAux[y])) {
                        res = false;
                    }
                }
            }

            if (res) {
                this.juegoTerminado = true;
                break;
            }
        }

        if (noCasillas == 9) {
            this.juegoTerminado = true;
            if (!res) {
                this.sinGanador = true;
                this.gano = false;
            }
        }
        return res;
    }


    public void tirar(View v) {
        switch (v.getId()) {
            case R.id.btn_supIzq:
                this.tirarJugador(this.btn_supIzq, 1);
                break;
            case R.id.btn_supMed:
                this.tirarJugador(this.btn_supMed, 2);
                break;
            case R.id.btn_supDer:
                this.tirarJugador(this.btn_supDer, 3);
                break;
            case R.id.btn_medIzq:
                this.tirarJugador(this.btn_medIzq, 4);
                break;
            case R.id.btn_medMed:
                this.tirarJugador(this.btn_medMed, 5);
                break;
            case R.id.btn_medDer:
                this.tirarJugador(this.btn_medDer, 6);
                break;
            case R.id.btn_infIzq:
                this.tirarJugador(this.btn_infIzq, 7);
                break;
            case R.id.btn_infMed:
                this.tirarJugador(this.btn_infMed, 8);
                break;
            case R.id.btn_infDer:
                this.tirarJugador(this.btn_infDer, 9);
                break;

        }

        this.noMovimientos += 1;

        String tirosTotalJ1[] = this.tirosJ1.split(",");
        String tirosTotalJ2[] = this.tirosJ2.split(",");
        //Log.v("Numero de tiros J1", tirosTotalJ1.length + "");
        //Log.v("Numero de tiros J2", tirosTotalJ2.length + "");
        if (tirosTotalJ2.length >= 3 || tirosTotalJ1.length >= 3) {
            comprobado = comprobarTablero();
            //Log.v("Juego terminado", juegoTerminado + "");
            //Log.v("Comprobado", this.comprobado + "");
            if (juegoTerminado) {
                if (comprobado) {
                    if (this.tipoJuego.equals("Solo")) {
                        this.sinGanador = false;
                        this.gano = true;
                        guardarEstadisticas();
                        AlertDialog mensaje = new AlertDialog.Builder(Jugar_Gato.this).create();
                        mensaje.setTitle("Victoria");
                        mensaje.setMessage(String.format("HAS GANADO!"));
                        mensaje.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                        mensaje.setCancelable(false);
                        mensaje.setCanceledOnTouchOutside(false);
                        mensaje.show();
                    } else {
                        this.sinGanador = false;
                        this.gano = false;
                        guardarEstadisticas();
                        AlertDialog mensaje = new AlertDialog.Builder(Jugar_Gato.this).create();
                        mensaje.setTitle("Victoria");
                        mensaje.setMessage(String.format("El ganador es %s", turno));
                        mensaje.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                        mensaje.setCancelable(false);
                        mensaje.setCanceledOnTouchOutside(false);
                        mensaje.show();
                    }
                } else if (this.sinGanador) {
                    guardarEstadisticas();
                    AlertDialog mensaje = new AlertDialog.Builder(Jugar_Gato.this).create();
                    mensaje.setTitle("Sin Ganador");
                    mensaje.setMessage(String.format("GATO GARABATO!!!!! :P"));
                    mensaje.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                    mensaje.setCancelable(false);
                    mensaje.setCanceledOnTouchOutside(false);
                    mensaje.show();
                }
            }
        }

        if (this.turno.equals("J1")) {
            this.turno = "J2";
        } else {
            this.turno = "J1";
        }
        this.lbl_jugador.setText(turno.trim());

        if (this.tipoJuego.equals("Solo")) {
            try{
                Thread.sleep(500);
            }catch(InterruptedException ex){
                Log.v("ERROR",""+ex.getStackTrace());
            }
            this.tiraMaquina();
        }

        return;
    }

    private boolean tirarJugador(Button aux, Integer tiro) {
        //Log.v("ID:", "" + aux.getId());
        if (!this.tirosJ1
                .contains(Integer.toString(tiro))) {
            if (!this.tirosJ2
                    .contains(Integer.toString(tiro))) {
                if (this.turno.equals("J1")) {
                    this.tirosJ1 += Integer.toString(tiro) + ",";
                    this.totalTiros.add(tiro);
                    aux.setText("X");
                    aux.setEnabled(false);
                } else {
                    this.tirosJ2 += Integer.toString(tiro) + ",";
                    this.totalTiros.add(tiro);
                    aux.setText("O");
                    aux.setEnabled(false);
                }
            }
        }
        return true;
    }

    private void guardarEstadisticas() {
        SharedPreferences sp = getSharedPreferences("ESTADISTICAS_GATO", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (this.sinGanador) {
            this.noJuegosSinGanador += 1;
        }
        if (this.gano) {
            this.noJuegosGanados += 1;
        }
        if (this.noMovimientos < this.noMinimoMov || this.noMinimoMov == 0) {
            this.noMinimoMov = this.noMovimientos;
        }
        if (this.tipoJuego.equals("Solo")) {
            this.noJuegosSolo += 1;
        } else {
            this.noJuegosDos += 1;
        }
        editor.putString("noMinMovimientos",
                this.noMinimoMov.toString());
        editor.putString("noJuegosSinGanador",
                this.noJuegosSinGanador.toString());
        editor.putString("noJuegosSolo",
                this.noJuegosSolo.toString());
        editor.putString("noJuegosDos",
                this.noJuegosDos.toString());
        editor.putString("noJuegosGanados",
                this.noJuegosGanados.toString());
        editor.commit();
    }

    private boolean buscarEnTiros(int ref) {
        for (Integer elemento : totalTiros) {
            if (elemento == ref) {
                return false;
            }
        }
        return true;
    }

}