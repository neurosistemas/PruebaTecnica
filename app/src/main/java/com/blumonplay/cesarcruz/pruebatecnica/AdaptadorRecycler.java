package com.blumonplay.cesarcruz.pruebatecnica;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorRecycler extends RecyclerView.Adapter<AdaptadorRecycler.ViewMolder> {
    ArrayList<String> listaPersonas;

    public AdaptadorRecycler(ArrayList<String> listaPersonas){this.listaPersonas=listaPersonas;}

    @NonNull
    @Override
    public ViewMolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_persona, parent,false);
        return new ViewMolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewMolder holder, int position) {
        holder.AsignarPersona(position,listaPersonas.get(position));
    }

    @Override
    public int getItemCount() {
        return listaPersonas.size();
    }

    public class ViewMolder extends RecyclerView.ViewHolder{
        public TextView nombrePersona;
        public TextView posicion;
        public ViewMolder(View itemView) {
            super(itemView);
            nombrePersona =itemView.findViewById(R.id.item_recycler_nombre);
            posicion =itemView.findViewById(R.id.item_recycler_posicion);

        }
        public void AsignarPersona(int pos, String s){
           posicion.setText(String.valueOf(pos+1));
            nombrePersona.setText(s);
        }
    }
}
