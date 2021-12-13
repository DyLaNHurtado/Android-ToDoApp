package com.example.todoapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClick {

    private List<Nota> notas;
    private RecyclerView listado;
    private Adaptator adaptador;
    private LinearLayoutManager llm;
    private ConstraintLayout layout;
    private Toolbar toolbar;
    private Animation shake;
    private ImageView newNote;

    private JSonSerialicer serialicer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        listeners();
    }

    @Override
    protected void onPause() {
        super.onPause();
        guardarNotas();
    }

    @Override
    public void onClick(int posicion) {
        //Toast.makeText(this, ""+posicion, Toast.LENGTH_SHORT).show();
        ViewNote vn = new ViewNote(notas.get(posicion),posicion,leerModo(),false);
        vn.show(getSupportFragmentManager(),null);
    }

    @Override
    public void onLongClick(int posicion, ConstraintLayout layout) {

        borrarObjeto(posicion,layout).show();
    }


    private void initComponents(){

        listado = findViewById(R.id.reciclerViewNotas);
        llm = new LinearLayoutManager(this);
        listado.setLayoutManager(llm);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        layout = findViewById(R.id.layoutMain);
        newNote = findViewById(R.id.newNote);

        notas = leerFichero();
        shake = AnimationUtils.loadAnimation(this,R.anim.seleccion_prolongada);

        cambiarModo();
        setAdaptador();
    }


    public void newNote(Nota nota){
        notas.add(nota);
        setAdaptador();
    }


    public void saveNote(Nota nota, int position){

        notas.set(position,nota);
        setAdaptador();
    }


    public void notaSinTexto(){
        Toast.makeText(this, "No se puede guardar una nota sin contenido", Toast.LENGTH_SHORT).show();
    }


    private List<Nota> leerFichero(){
        serialicer = new JSonSerialicer("notas.json",this);
        return serialicer.load();
    }


    private void guardarNotas(){
        serialicer = new JSonSerialicer("notas.json",this);
        serialicer.save(notas);

        setAdaptador();
    }


    private void setAdaptador() {
        adaptador = new Adaptator(notas, this,leerModo());
        listado.setAdapter(adaptador);
    }

    private AlertDialog borrarObjeto(int posicion, ConstraintLayout layout) {

        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        layout.startAnimation(shake);

        alerta.setMessage("Se procedera a borrar la nota")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notas.remove(posicion);
                        layout.clearAnimation();
                        setAdaptador();
                    }
                })
                .setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        layout.clearAnimation();
                    }
                });

        alerta.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                layout.clearAnimation();
            }
        });
        return alerta.create();
    }


    private void cambiarModo() {

        if(leerModo()){
            layout.setBackgroundColor(Color.rgb(30,30,30));
            listado.setBackgroundColor(Color.rgb(30,30,30));
        }else{
            layout.setBackgroundColor(Color.rgb(240,240,240));
            listado.setBackgroundColor(Color.rgb(240,240,240));
        }
        setAdaptador();
    }


    private void listeners(){
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewNote vn = new ViewNote(null,-1,leerModo(),true);
                vn.show(getSupportFragmentManager(),null);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Menu menu = new Menu(leerModo());
                menu.show(getSupportFragmentManager(),null);
            }
        });
    }


    public void guardarModo(boolean modoOscuro){
        SharedPreferences preferencias = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putBoolean("oscuro", modoOscuro);
        editor.apply();
        cambiarModo();
    }


    private boolean leerModo(){
        SharedPreferences preferencias = getSharedPreferences("preferencias",Context.MODE_PRIVATE);
        return preferencias.getBoolean("oscuro",true);
    }


    public void intent(Object o){
        Intent intent = new Intent(this,MenuActivity.class);
        if(o.equals(true) || o.equals(false)){
            guardarModo((boolean)o);
        }else if(o.equals("about")){
            intent.putExtra("texto", "About");
            startActivity(intent);
        }else if(o.equals(("help"))){
            intent.putExtra("texto", "Help");
            startActivity(intent);
        }
    }
}