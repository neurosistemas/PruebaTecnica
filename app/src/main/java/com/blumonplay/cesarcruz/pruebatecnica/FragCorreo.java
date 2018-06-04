package com.blumonplay.cesarcruz.pruebatecnica;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

public class FragCorreo extends Fragment implements View.OnClickListener {

    private FragCorreo.FragCorreoListener listener;
    private TextView tview_titulo;
    private TextView tview_contenido;
    private ConstraintLayout mainLayout;
    private EditText editCorreo;
    private EditText editTitulo;
    private Button button;
    public FragCorreo() {
        // Required empty public constructor
    }


    public static FragCorreo newInstance(String param1, String param2) {
        FragCorreo fragment = new FragCorreo();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_correo, container, false);
        tview_titulo = v.findViewById(R.id.correo_titulo);
        tview_contenido= v.findViewById(R.id.correo_contenido);
        mainLayout = v.findViewById(R.id.correo_main_layout);
        editCorreo = v.findViewById(R.id.correo_editDestinatario);
        editTitulo = v.findViewById(R.id.correo_editTituloCorreo);
        button = v.findViewById(R.id.correo_enviar_btn);
        button.setOnClickListener(this);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragCorreo.FragCorreoListener) {
            listener = (FragCorreo.FragCorreoListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }




    @Override
    public void onClick(View v) {
        String txtCorreo=editCorreo.getText().toString();
        String txtTitulo=editTitulo.getText().toString();

        if(EvaluarDatos(txtCorreo,txtTitulo)){

            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{txtCorreo});
            i.putExtra(Intent.EXTRA_SUBJECT, txtTitulo);
            i.putExtra(Intent.EXTRA_TEXT, listener.getDatos());
            try {
                startActivity(Intent.createChooser(i, getString(R.string.enviar_correo)));

                Snackbar.make(mainLayout, "Listo.", Snackbar.LENGTH_LONG).show();

            } catch (android.content.ActivityNotFoundException ex) {
                Snackbar.make(mainLayout, getString(R.string.error_cliente_no_instalado), Snackbar.LENGTH_SHORT).show();
            }
        }
    }



    private boolean EvaluarDatos(String correo, String titulo){
        boolean b = true;
        if(!correo.contains("@") || !correo.contains(".")|| correo.length()<=5){ b = false;
            Snackbar.make(mainLayout, "Anota un correo válido", Snackbar.LENGTH_LONG).show();
        }
        if(titulo.length()<=3){ b = false;
            Snackbar.make(mainLayout, "Anota un titulo de mínimo 3 letras", Snackbar.LENGTH_LONG).show();
        }
        return b;
    }

    public interface FragCorreoListener {
        void FragCorreoInteraction();
        String getDatos();
    }
}
