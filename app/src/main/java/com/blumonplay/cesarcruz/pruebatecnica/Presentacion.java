package com.blumonplay.cesarcruz.pruebatecnica;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


public class Presentacion extends Fragment implements Animation.AnimationListener, View.OnTouchListener{
    private Animation moverArriba, moverAbajo;
    private boolean animacionActiva;
    View view;
    public PresentacionInterface antifaz;

    public Presentacion() {
    }
public interface PresentacionInterface{
        public void cerrarFragmento(Presentacion p);
}

    public static Presentacion newInstance(String param1, String param2) {
        Presentacion fragment = new Presentacion();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof PresentacionInterface){
            antifaz=(PresentacionInterface)context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    antifaz=null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moverArriba = AnimationUtils.loadAnimation(getContext(),R.anim.entrada_anim);
        moverArriba.setAnimationListener(this);
        moverAbajo = AnimationUtils.loadAnimation(getContext(),R.anim.salida_anim);
        moverAbajo.setAnimationListener(this);
        animacionActiva = false;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_presentacion, container, false);
        view.setOnTouchListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        IniciarAnimacion();
    }

    public void IniciarAnimacion(){
        Log.d("touch","iniciando animacion");

        view.startAnimation(moverArriba);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        animacionActiva = true;
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(animation==moverAbajo){
            antifaz.cerrarFragmento(this);
        }
        animacionActiva = false;
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
       if(!animacionActiva){view.startAnimation(moverAbajo);}
        return false;
    }

}
