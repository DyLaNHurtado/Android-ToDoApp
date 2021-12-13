package com.example.todoapp;


import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adaptator extends RecyclerView.Adapter<Adaptator.ViewHolder> {

    private List<Nota> notas;
    private OnClick onClick;
    private boolean modoOscuro;

    public Adaptator (List<Nota> notas,OnClick onClick,boolean modoOScuro){

        this.notas=notas;
        this.onClick=onClick;
        this.modoOscuro=modoOScuro;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_preview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(notas.get(position).getTexto().length()<20){
            holder.texto.setText(notas.get(position).getTexto());
        }else{
            String texto = notas.get(position).getTexto().substring(0,19);
            holder.texto.setText(texto +"...");
        }

        if(modoOscuro){
            holder.texto.setTextColor(Color.WHITE);
            if(notas.get(position).importante){
                holder.layout.setBackgroundColor(Color.rgb(19,196,113));
                holder.texto.setTextColor(Color.WHITE);
            }
        }else{
            holder.texto.setTextColor(Color.BLACK);
            if(notas.get(position).importante){
                holder.layout.setBackgroundColor(Color.rgb(61,255,179));
                holder.texto.setTextColor(Color.BLACK);
            }
        }
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {

        private TextView texto;
        private ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            texto = itemView.findViewById(R.id.previewTitle);
            layout = itemView.findViewById(R.id.layaout);
        }

        @Override
        public void onClick(View v) {
            int posicion = getAdapterPosition();
            Log.i("info",""+posicion);
            onClick.onClick(posicion);
        }

        @Override
        public boolean onLongClick(View v) {
            int posicion = getAdapterPosition();
            onClick.onLongClick(posicion,layout);
            return false;
        }
    }
}