package com.example.todoapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MenuActivity extends AppCompatActivity {
    private TextView texto;
    private ImageView imagen;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        initComponents();

    }

    private void initComponents() {
        texto = findViewById(R.id.textSecond);
        imagen = findViewById(R.id.imageSecond);
        layout = findViewById(R.id.menuActivityLayout);

        if(getIntent().getStringExtra("texto").equals("Help")){
            texto.setText(R.string.howUse);
            imagen.setImageResource(R.drawable.how_to_use);
        }else{
            texto.setText(R.string.about);
            imagen.setImageResource(R.drawable.about_us);
        }

        if(modo()){
            layout.setBackgroundColor(Color.rgb(40,43,48));
            texto.setTextColor(Color.WHITE);
        }else{
            layout.setBackgroundColor(Color.rgb(240,240,240));
            texto.setTextColor(Color.BLACK);
        }
    }

    private boolean modo(){
        SharedPreferences preferencias = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        return preferencias.getBoolean("oscuro",true);
    }
}