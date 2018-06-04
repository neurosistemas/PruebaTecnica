package com.blumonplay.cesarcruz.pruebatecnica;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class FragQuizNumerico extends Fragment implements View.OnClickListener{
    public QuizNumericoListener listener;
    public TextView titulo;
    public EditText numero;
    public TextView suma;
    public Button agregarBtn;
    private ArrayList<Double> listaNumeros;
    private double contador;
    private boolean puedeGuardar;
    public FragQuizNumerico() {
        // Required empty public constructor
    }
    public static FragQuizNumerico newInstance(String param1, String param2) {
        FragQuizNumerico fragment = new FragQuizNumerico();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        listaNumeros=new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            View v = inflater.inflate(R.layout.fragment_quiz_numerico, container, false);
            titulo = v.findViewById(R.id.quiz_num_titulo);
            suma = v.findViewById(R.id.quiz_num_suma);
            agregarBtn = v.findViewById(R.id.quiz_num_agregar);
            numero = v.findViewById(R.id.quiz_num_edittext);
            titulo.setText(getString(R.string.quiz_numerico_1));
            agregarBtn.setText(getString(R.string.agregar));
            suma.setVisibility(View.INVISIBLE);
            puedeGuardar = false;
        agregarBtn.setOnClickListener(this);
        return v;
    }

    public void evaluarNumero(){
        String numTxt = numero.getText().toString();
        titulo.setText(CambiarTitulo(numTxt));

        if(numTxt.equals("0")){
            suma.setVisibility(View.VISIBLE);
            int sumaTotal =0;
            for(int i =0; i<listaNumeros.size(); i++){ sumaTotal+=listaNumeros.get(i); }
            suma.setText(String.valueOf(sumaTotal));
            numero.setText("");
            puedeGuardar = true;
            agregarBtn.setText(getString(R.string.guardar_json));
            numero.setVisibility(View.INVISIBLE);
            titulo.setText(getText(R.string.quiz_numerico_sumatotal));
        } else if(!numTxt.equals("")){
            listaNumeros.add(Double.parseDouble(numTxt));
            suma.setText("");
            numero.setText("");
        }
    }

    private void ResetearDatos(){
        suma.setText("0");
        suma.setVisibility(View.INVISIBLE);
        numero.setVisibility(View.VISIBLE);
        contador=0;
        numero.setText("");
        agregarBtn.setText(getString(R.string.agregar));
        puedeGuardar = false;
        titulo.setText(getString(R.string.quiz_numerico_1));
    }

    private String CambiarTitulo(String txt){
        String msg ="";
        if(txt.equals("")){
            msg = getString(R.string.quiz_numerico_alerta);
        }
        else {
            if(contador==0){msg = getString(R.string.quiz_numerico_1);}
            else{msg = getString(R.string.quiz_numerico_2);}
        }
        return  msg;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof QuizNumericoListener) {
            listener = (QuizNumericoListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
        ResetearDatos();
        Log.d("quizNumerico","onDetach");
    }

    @Override
    public void onClick(View v) {
        if(puedeGuardar){listener.guardarNumerosJSON(listaNumeros);
            listaNumeros=new ArrayList<>();
        }
        else{ evaluarNumero();}

    }


    public interface QuizNumericoListener {
        void interaccionNumericaListener();
        void guardarNumerosJSON(ArrayList<Double> numeros);
    }
}
