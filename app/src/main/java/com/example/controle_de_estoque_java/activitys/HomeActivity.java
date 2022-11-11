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


import com.example.controle_de_estoque_java.MainActivity;
import com.example.controle_de_estoque_java.R;
import com.example.controle_de_estoque_java.helpers.ProdutoDAO;
import com.example.controle_de_estoque_java.models.Produto;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class HomeActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> resultado;
    private Button btNovo;
    private final int TIPO_CRIACAO = 0;
    private final int TIPO_MODIFICACAO = 1;
    private List<Produto> productList = new ArrayList<>();
    private boolean block = true;


    private void listardados(){
        ProdutoDAO produtoDAO = new ProdutoDAO(getApplicationContext());
        productList = produtoDAO.listar();

        ArrayList<String> resultado=new ArrayList<String>();
        for (int i = 0; i < productList.size(); i++) {
            String x = "Produto: " + productList.get(i).getDescricao() + " \n QTD: " + productList.get(i).getQuantidade();
            resultado.add(x);
        }
        ArrayAdapter meuAdaptador=new ArrayAdapter<String>(this, R.layout.lista_personalizada,android.R.id.text1,resultado);
        listView.setAdapter(meuAdaptador);
    }

    public HomeActivity() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        listardados();

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
        btNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), CadActivity.class);
                intent.putExtra("tipo", TIPO_CRIACAO);

                startActivity(intent);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (block) {
                    Produto produto = productList.get(i);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("produto", produto);
                    Intent intent = new Intent(getApplicationContext(), CadActivity.class);
                    intent.putExtra("tipo", TIPO_MODIFICACAO);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                        block = false;
                        Produto produto = productList.get(i);
                        AlertDialog.Builder aler = new AlertDialog.Builder(HomeActivity.this);
                        aler.setMessage("Deseja excluir o produto " + produto.getDescricao() + " ?");
                        aler.setTitle("Excluir itens.");
                        aler.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ProdutoDAO produtoDAO = new ProdutoDAO(getApplicationContext());
                                produtoDAO.deletar(produto);
                                listardados();
                                Toast.makeText(HomeActivity.this, "Deletado!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        aler.setNegativeButton("Não", null);
                        aler.setIcon(R.drawable.ic_delete);
                        aler.create();
                        aler.show();
                     Handler h = new Handler();


                    h.postDelayed(new Runnable() {
                        public void run() {
                            block = true;
                        }
                    }, 1000);



                    }catch(Exception e){
                        Log.i("INFO", "Erro: " + e.getMessage());
                    }


                return false;
            }
        });



    }


}