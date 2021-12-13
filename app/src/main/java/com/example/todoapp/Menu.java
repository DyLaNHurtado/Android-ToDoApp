package com.example.todoapp;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

public class Menu extends DialogFragment {

    private ImageView oscuroImage, aboutImage, helpImage;
    private TextView oscuroText, aboutText, helpText;
    private ConstraintLayout layoutMenu;
    private boolean oscuro;

    public Menu(boolean oscuro) {
        this.oscuro = oscuro;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater layout = requireActivity().getLayoutInflater();
        View dialogView = layout.inflate(R.layout.menu, null);

        oscuroImage = dialogView.findViewById(R.id.modoOscuroIcon);
        aboutImage = dialogView.findViewById(R.id.aboutUsIcon);
        helpImage = dialogView.findViewById(R.id.howUseIcon);
        oscuroText = dialogView.findViewById(R.id.modoOscuroText);
        aboutText = dialogView.findViewById(R.id.aboutUsText);
        helpText = dialogView.findViewById(R.id.howUseText);
        layoutMenu = dialogView.findViewById(R.id.layoutMenu);

        cambiarColores();
        onClick();

        builder.setView(dialogView);

        return builder.show();
    }

    private void onClick() {
        MainActivity main = (MainActivity) getActivity();
        oscuroText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oscuro(main);
            }
        });
        oscuroImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oscuro(main);
            }
        });

        helpImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texto("help", main);
            }
        });

        helpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texto("help", main);
            }
        });

        aboutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texto("about", main);
            }
        });

        aboutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texto("about", main);
            }
        });

    }

    private void cambiarColores() {
        if (oscuro) {
            oscuroText.setText("Modo claro");
            layoutMenu.setBackgroundColor(Color.rgb(40, 43, 48));
            oscuroText.setTextColor(Color.WHITE);
            aboutText.setTextColor(Color.WHITE);
            helpText.setTextColor(Color.WHITE);
        } else {
            oscuroText.setText("Modo oscuro");
            layoutMenu.setBackgroundColor(Color.rgb(240, 240, 240));
            oscuroText.setTextColor(Color.BLACK);
            aboutText.setTextColor(Color.BLACK);
            helpText.setTextColor(Color.BLACK);
        }
    }

    private void oscuro(MainActivity main) {
        oscuro = !oscuro;
        cambiarColores();
        main.guardarModo(oscuro);
        main.intent(oscuro);
    }

    private void texto(String clave, MainActivity main) {
        main.intent(clave);
        dismiss();
    }

}
