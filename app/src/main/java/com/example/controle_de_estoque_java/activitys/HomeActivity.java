package com.example.controle_de_estoque_java.activitys;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;


import com.example.controle_de_estoque_java.R;
import com.example.controle_de_estoque_java.models.Produto;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class HomeActivity extends AppCompatActivity {
    private ListView listView;
    private SQLiteDatabase dados;
    private final String NOME_DATABASE = "usuarios";
    private ArrayList<String> resultado;
    private Button btExcluir, btNovo;
    private final int TIPO_CRIACAO = 0;
    private final int TIPO_MODIFICACAO = 1;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        linkage();


    }
    private void linkage(){
        listView = findViewById(R.id.listView);
        btNovo = findViewById(R.id.btNovo);
        btExcluir = findViewById(R.id.btExcluir);
        btNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("produto", new Produto(1L,"Batom",5));
                Intent intent =new Intent(getApplicationContext(), CadActivity.class);
                intent.putExtra("tipo", TIPO_CRIACAO);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        btExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler();
                btExcluir.setAlpha(.3f);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btExcluir.setAlpha(1.0f);
                    }
                }, 150);



            }
        });

    }




}