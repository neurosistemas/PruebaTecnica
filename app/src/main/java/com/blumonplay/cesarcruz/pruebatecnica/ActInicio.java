package com.blumonplay.cesarcruz.pruebatecnica;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActInicio extends AppCompatActivity implements Presentacion.PresentacionInterface,
        FragQuizNumerico.QuizNumericoListener, FragQuizNombres.QuizNombresListener, FragJSON.FragJSONListener,
FragCorreo.FragCorreoListener{
    FragmentManager fm;
    private static final String  TAG_PRESENTACION ="presentacion", TAG_NUMERICO ="numerico",TAG_NOMBRES ="nombres",
           TAG_JSON ="JSON",TAG_CORREO ="correo",TAG_ARRAY_NOMBRES = "nombres",TAG_ARRAY_NUMEROS = "numeros",
            TAG_JOBJECT_NUMEROS = "Numero", TAG_JOBJECT_NOMBRES = "Nombre";
    Presentacion presentacion;
    FragQuizNombres fragQuizNombres;
    FragQuizNumerico quizNumerico;
    FragCorreo fragCorreo;
    FragJSON fragJSON;
    LinearLayout contenedor;
    ConstraintLayout layout;
    Menu menu;
    int fragmentPointer;
    boolean presentacionActiva;
    JSONObject jsonObject;
    BottomNavigationView navigation;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            if(!presentacionActiva){
            switch (item.getItemId()) {
                case R.id.navigation_numerica:
                    NavegarNumerico();
                    return true;
                case R.id.navigation_nombres:
                    NavegarNombres();
                    return true;
                case R.id.navigation_json:
                    NavegarJSON();
                    return true;
                case R.id.navigation_correo:
                    NavegarCorreo();
                    return true;

            }
            return false;
        }
        return false;
        }
    };
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        layout = findViewById(R.id.container);
        contenedor = findViewById(R.id.contenedorFragments);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        menu = navigation.getMenu();
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        presentacion =Presentacion.newInstance("","");
        fragQuizNombres = FragQuizNombres.newInstance("","");
        quizNumerico = FragQuizNumerico.newInstance("","");
        fragCorreo = FragCorreo.newInstance("","");
        fragJSON = FragJSON.newInstance("","");
        ft.add(R.id.container, presentacion,TAG_PRESENTACION);
        presentacionActiva = true;
        ft.commit();
        fragmentPointer=0;
        jsonObject = new JSONObject();
        JSONArray noms = new JSONArray();
        JSONArray pers = new JSONArray();
        try {
            jsonObject.put(TAG_ARRAY_NUMEROS,noms);
            jsonObject.put(TAG_ARRAY_NOMBRES,pers);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cerrarFragmento(Presentacion p) {
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(p);
        presentacionActiva = false;
        ft.add(R.id.contenedorFragments,quizNumerico,TAG_NUMERICO);
        ft.commit();
    }

    public void NavegarNumerico(){
        if(fm.findFragmentByTag(TAG_NUMERICO)==null && !presentacionActiva){
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.contenedorFragments,quizNumerico, TAG_NUMERICO);
            ft.commit();
            fragmentPointer=0;
        }
    }
    public void NavegarNombres(){
        if(fm.findFragmentByTag(TAG_NOMBRES)==null && !presentacionActiva){
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.contenedorFragments, fragQuizNombres, TAG_NOMBRES);
            ft.commit();
            fragmentPointer=1;
        }
    }
    public void NavegarJSON(){
        if(fm.findFragmentByTag(TAG_JSON)==null && !presentacionActiva){
            fragJSON.AnadirTextoJson(jsonObject.toString());
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.contenedorFragments, fragJSON, TAG_JSON);
            ft.commit();
            fragmentPointer=2;
        }
    }
    public void NavegarCorreo(){
        if(fm.findFragmentByTag(TAG_CORREO)==null && !presentacionActiva){
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.contenedorFragments, fragCorreo, TAG_CORREO);
            ft.commit();
            fragmentPointer=3;
        }
    }


    @Override
    public void FragCorreoInteraction() {

    }

    @Override
    public String getDatos() {
        return jsonObject.toString();
    }

    @Override
    public void onFragJSONInteraction() {

    }

    @Override
    public void interaccionQuizNombresListener() {

    }
    @Override
    public void guardarPersonasJSON(ArrayList<String> nombres) {
        JSONArray pers = null;
        try {
            pers = jsonObject.getJSONArray(TAG_ARRAY_NOMBRES);
            if(pers!=null){
                for (int i = 0; i < nombres.size(); i++) {
                JSONObject nJobject = new JSONObject();
                nJobject.put(TAG_JOBJECT_NOMBRES, nombres.get(i));
                    pers.put(nJobject);
                }
            } else{Log.d("JSON","No se encontró el JSON Array de Nombres");}
    } catch (JSONException e) {
            e.printStackTrace();
            Log.d("JSON","Error en JSON Array de Nombres");
        }
        mOnNavigationItemSelectedListener.onNavigationItemSelected(menu.getItem(2));
        navigation.setSelectedItemId(R.id.navigation_json);
    }
    @Override
    public void interaccionNumericaListener() {

    }
    @Override
    public void guardarNumerosJSON(ArrayList<Double> numeros) {
        JSONArray noms = null;
        try {
            noms = jsonObject.getJSONArray(TAG_ARRAY_NUMEROS);
            if(noms!=null){
                for(int i =0; i<numeros.size();i++){
                    JSONObject nJobject = new JSONObject();
                    nJobject.put(TAG_JOBJECT_NUMEROS,numeros.get(i));
                    noms.put(nJobject);
                }

            }else{
                Log.d("JSON","No se encontró el JSON Array de Números");}
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("JSON","Error en JSON Array de Números");
    }
        mOnNavigationItemSelectedListener.onNavigationItemSelected(menu.getItem(2));
        navigation.setSelectedItemId(R.id.navigation_json);
    }



}
