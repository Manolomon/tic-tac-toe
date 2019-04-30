package com.manolomon.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class Jugar_Gato extends AppCompatActivity {

    private String modoJuego;
    private String turno;
    private String ganador = "N";

    private Button btn_1;
    private Button btn_2;
    private Button btn_3;
    private Button btn_4;
    private Button btn_5;
    private Button btn_6;
    private Button btn_7;
    private Button btn_8;
    private Button btn_9;

    private String[] casillas;
    private int contMovimientos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugar_gato);

        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_4 = (Button) findViewById(R.id.btn_4);
        btn_5 = (Button) findViewById(R.id.btn_5);
        btn_6 = (Button) findViewById(R.id.btn_6);
        btn_7 = (Button) findViewById(R.id.btn_7);
        btn_8 = (Button) findViewById(R.id.btn_8);
        btn_9 = (Button) findViewById(R.id.btn_9);

        leerParametros();

        casillas = new String[] {"-","-","-","-","-","-","-","-","-"};

        if(turno.equals("O")){
            tiroOneplayer(null);
        }
    }

    public void movimiento(View v){
        if(modoJuego.equals("multiplayer")){
            switch (v.getId()){
                case R.id.btn_1: casillas[0] = tiroMultiplayer((Button) v); break;
                case R.id.btn_2: casillas[1] = tiroMultiplayer((Button) v); break;
                case R.id.btn_3: casillas[2] = tiroMultiplayer((Button) v); break;
                case R.id.btn_4: casillas[3] = tiroMultiplayer((Button) v); break;
                case R.id.btn_5: casillas[4] = tiroMultiplayer((Button) v); break;
                case R.id.btn_6: casillas[5] = tiroMultiplayer((Button) v); break;
                case R.id.btn_7: casillas[6] = tiroMultiplayer((Button) v); break;
                case R.id.btn_8: casillas[7] = tiroMultiplayer((Button) v); break;
                case R.id.btn_9: casillas[8] = tiroMultiplayer((Button) v); break;
            }
        }else {
            switch (v.getId()){
                case R.id.btn_1: casillas[0] = tiroOneplayer((Button) v); break;
                case R.id.btn_2: casillas[1] = tiroOneplayer((Button) v); break;
                case R.id.btn_3: casillas[2] = tiroOneplayer((Button) v); break;
                case R.id.btn_4: casillas[3] = tiroOneplayer((Button) v); break;
                case R.id.btn_5: casillas[4] = tiroOneplayer((Button) v); break;
                case R.id.btn_6: casillas[5] = tiroOneplayer((Button) v); break;
                case R.id.btn_7: casillas[6] = tiroOneplayer((Button) v); break;
                case R.id.btn_8: casillas[7] = tiroOneplayer((Button) v); break;
                case R.id.btn_9: casillas[8] = tiroOneplayer((Button) v); break;
            }
            if(ganador.equals("N")){
                tiroOneplayer(null);
            }
        }
        if(contMovimientos >= 4){
            validarGanador();
        }
    }

    private String tiroOneplayer(Button casilla){
        contMovimientos ++;
        String valor;
        if(turno.equals("X")){
            casilla.setText("X");
            casilla.setEnabled(false);
            valor = "X";

            turno = "O";
        }else{
            Button casillaCPU = tiroCPU();
            casillaCPU.setText("O");
            casillaCPU.setEnabled(false);
            valor = "O";

            turno = "X";
        }

        return valor;
    }
    private Button tiroCPU(){
        boolean flag = true;
        Random r = new Random();
        int numeroCasilla;
        Button btn = null;
        while(flag == true){
            numeroCasilla = r.nextInt(9);
            if(casillas[numeroCasilla].equals("-")){
                flag = false;
                switch (numeroCasilla){
                    case 0: btn = btn_1; casillas[0] = "O"; break;
                    case 1: btn = btn_2; casillas[1] = "O"; break;
                    case 2: btn = btn_3; casillas[2] = "O"; break;
                    case 3: btn = btn_4; casillas[3] = "O"; break;
                    case 4: btn = btn_5; casillas[4] = "O"; break;
                    case 5: btn = btn_6; casillas[5] = "O"; break;
                    case 6: btn = btn_7; casillas[6] = "O"; break;
                    case 7: btn = btn_8; casillas[7] = "O"; break;
                    case 8: btn = btn_9; casillas[8] = "O"; break;
                }
            }
        }
        return  btn;
    }

    private String tiroMultiplayer(Button casilla){
        contMovimientos ++;
        String valor;
        if(turno.equals("X")){
            casilla.setText("X");
            casilla.setEnabled(false);
            valor = "X";

            turno = "O";
        }else{
            casilla.setText("O");
            casilla.setEnabled(false);
            valor = "O";

            turno = "X";
        }

        return valor;
    }


    private void validarGanador(){
        if(casillas[0].equals(casillas[1]) && casillas[0].equals(casillas[2]) && casillas[0] != "-"){
            ganador = casillas[0];
        }else{
            if(casillas[3].equals(casillas[4]) && casillas[3].equals(casillas[5]) && casillas[3] != "-"){
                ganador = casillas[3];
            }else{
                if(casillas[6].equals(casillas[7]) && casillas[6].equals(casillas[8]) && casillas[6] != "-"){
                    ganador = casillas[6];
                }else{
                    if(casillas[0].equals(casillas[3]) && casillas[0].equals(casillas[6]) && casillas[0] != "-"){
                        ganador = casillas[0];
                    }else{
                        if(casillas[1].equals(casillas[4]) && casillas[1].equals(casillas[7]) && casillas[1] != "-"){
                            ganador = casillas[1];
                        }else{
                            if(casillas[2].equals(casillas[5]) && casillas[2].equals(casillas[8]) && casillas[2] != "-"){
                                ganador = casillas[2];
                            }else{
                                if(casillas[0].equals(casillas[4]) && casillas[0].equals(casillas[8]) && casillas[0] != "-"){
                                    ganador = casillas[0];
                                }else{
                                    if(casillas[2].equals(casillas[4]) && casillas[2].equals(casillas[6]) && casillas[2] != "-"){
                                        ganador = casillas[2];
                                    }else{
                                        ganador = "N";
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if(!ganador.equals("N")){
            bloquearTodos();
        }
    }

    private void bloquearTodos(){
        btn_1.setEnabled(false);
        btn_2.setEnabled(false);
        btn_3.setEnabled(false);
        btn_4.setEnabled(false);
        btn_5.setEnabled(false);
        btn_6.setEnabled(false);
        btn_7.setEnabled(false);
        btn_8.setEnabled(false);
        btn_9.setEnabled(false);
    }

    private void leerParametros(){
        Intent intent = getIntent();
        modoJuego = intent.getStringExtra("modo_juego");
        turno = intent.getStringExtra("jugador_inicial");
    }
}

