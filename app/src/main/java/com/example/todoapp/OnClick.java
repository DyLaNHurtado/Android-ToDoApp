package com.example.todoapp;


import androidx.constraintlayout.widget.ConstraintLayout;

public interface OnClick {


    public void onClick(int posicion);


    public void onLongClick(int posicion, ConstraintLayout layout);
}
