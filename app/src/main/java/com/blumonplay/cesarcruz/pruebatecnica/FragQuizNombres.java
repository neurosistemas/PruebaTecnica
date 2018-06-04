package com.blumonplay.cesarcruz.pruebatecnica;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class FragQuizNombres extends Fragment implements View.OnClickListener{

    public QuizNombresListener listener;

    public TextView titulo;
    public EditText nombreEdit;
    public RecyclerView recyListaNombres;
    public Button agregarBtn;
    private ArrayList<String> listaPersonas;
    private int contador;
    AdaptadorRecycler adaptadorRecycler;
    ConstraintLayout mainLayout;
    private boolean puedeGuardar;

    public FragQuizNombres() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof QuizNombresListener) {
            listener = (QuizNombresListener)context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public static FragQuizNombres newInstance(String param1, String param2) {
        FragQuizNombres fragment = new FragQuizNombres();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        listaPersonas = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_quiz_nombres, container, false);
        titulo = v.findViewById(R.id.quiz_nom_titulo);
        mainLayout = v.findViewById(R.id.quiz_nom_mainLayout);
        recyListaNombres = v.findViewById(R.id.recycler_lista_personas);
        agregarBtn = v.findViewById(R.id.quiz_nom_agregar);
        nombreEdit = v.findViewById(R.id.quiz_nom_edittext);
        titulo.setText(getString(R.string.quiz_nombre_1));
        recyListaNombres.setVisibility(View.GONE);
        agregarBtn.setText(getText(R.string.agregar));
        recyListaNombres.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL,false));
        adaptadorRecycler = new AdaptadorRecycler(listaPersonas);
        recyListaNombres.setAdapter(adaptadorRecycler);
        puedeGuardar = false;
        agregarBtn.setOnClickListener(this);
        return v;
    }

    private void ResetearDatos(){
        recyListaNombres.setVisibility(View.GONE);
        listaPersonas = new ArrayList<>();
        adaptadorRecycler.notifyItemRangeRemoved(0,adaptadorRecycler.listaPersonas.size());
        agregarBtn.setText(getText(R.string.agregar));
        nombreEdit.setVisibility(View.VISIBLE);
        contador=0;
        nombreEdit.setText("");
        titulo.setText(getString(R.string.quiz_nombre_1));
        puedeGuardar = false;
    }



    @Override
    public void onClick(View v) {
        if(puedeGuardar){listener.guardarPersonasJSON(listaPersonas);
            listaPersonas= new ArrayList<>();
        }
        else{EvaluarTexto();}
    }

    private void EvaluarTexto() {
        String txt = nombreEdit.getText().toString();
        if(txt.equals("") || txt.length()<3){
          Snackbar bar =  Snackbar.make(mainLayout,getString(R.string.quiz_nombre_alerta),Snackbar.LENGTH_LONG);
            bar.show();
        }else if (!txt.toLowerCase().equals(getString(R.string.keyword).toLowerCase())){
            listaPersonas.add(txt);
            adaptadorRecycler.notifyItemInserted(listaPersonas.size());
            nombreEdit.setText("");
            contador++;
            if(contador==1){titulo.setText(getString(R.string.quiz_nombre_1));}
            else{titulo.setText(getString(R.string.quiz_nombre_2));}
        } else{
            if(listaPersonas.size()==0){
                Snackbar bar =  Snackbar.make(mainLayout,getString(R.string.quiz_nombre_alerta_faltan),Snackbar.LENGTH_LONG);
                bar.show();
            }else{
                recyListaNombres.setVisibility(View.VISIBLE);
                nombreEdit.setVisibility(View.INVISIBLE);
                agregarBtn.setText(getText(R.string.guardar_json));
                nombreEdit.setText("");
                titulo.setText(getText(R.string.quiz_nombre_lista));
                puedeGuardar = true;
            }

        }
    }

    public interface QuizNombresListener{
        void interaccionQuizNombresListener();
        void guardarPersonasJSON(ArrayList<String> nombres);
}
}
