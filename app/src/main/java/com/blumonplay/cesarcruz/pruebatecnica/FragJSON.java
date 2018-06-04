package com.blumonplay.cesarcruz.pruebatecnica;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;


public class FragJSON extends Fragment {

    private FragJSONListener listener;
    private TextView tview_titulo;
    private TextView tview_contenido;
    private LinearLayoutCompat mainLayout;
    private String textoContenido;
    public FragJSON() {
        // Required empty public constructor
    }


    public static FragJSON newInstance(String param1, String param2) {
        FragJSON fragment = new FragJSON();
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
        View v = inflater.inflate(R.layout.fragment_json, container, false);
        tview_titulo = v.findViewById(R.id.json_titulo);
        tview_contenido= v.findViewById(R.id.json_contenido);
        mainLayout = v.findViewById(R.id.json_main_layout);
        tview_contenido.setText(textoContenido);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragJSONListener) {
            listener = (FragJSONListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void AnadirTextoJson(String texto){
        textoContenido= texto;
    }


    public interface FragJSONListener {
        void onFragJSONInteraction();
    }
}
