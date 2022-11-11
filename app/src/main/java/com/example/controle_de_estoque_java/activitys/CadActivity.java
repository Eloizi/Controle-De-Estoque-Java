package com.example.controle_de_estoque_java.activitys;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.controle_de_estoque_java.R;
import com.example.controle_de_estoque_java.helpers.ProdutoDAO;
import com.example.controle_de_estoque_java.models.Produto;
import com.example.controle_de_estoque_java.my_codes.CustomKeyBoard;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class CadActivity extends AppCompatActivity {

    private TextInputEditText edtDescricao;
    private EditText edtQuantidade;
    private Button btCad;
    private boolean edicao;
    private ConstraintLayout consCad;
    private Produto currentProduto;
    private FloatingActionButton fab;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad);
        getSupportActionBar().hide();
        linkage();
        Bundle bundle = getIntent().getExtras();
       edicao = bundle.getInt("tipo") != 0;
       if(edicao){
           currentProduto =(Produto) bundle.getSerializable("produto");
           Log.i("Cox", "Modo edição Produto:"+currentProduto.getDescricao());
           incluirDados();
           btCad.setText("EDITAR");
       }else{
           Log.i("Cox", "Modo create");
           btCad.setText("CADASTRAR");
       }


    }
    /**
     * Método q ue inclui os dados nos EditText caso seja uma edição*/
    private void incluirDados(){
        edtDescricao.setText(currentProduto.getDescricao());
        edtQuantidade.setText(""+currentProduto.getQuantidade());
    }
/**Método que linka os itens com a view*/
    private void linkage(){
        consCad = findViewById(R.id.consCad);
        edtDescricao = findViewById(R.id.edtDescricao);
        edtQuantidade = findViewById(R.id.edtQuantidade);
        fab = findViewById(R.id.fab);
        btCad = findViewById(R.id.btCad);
        btCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtDescricao.getText().length()==0){
                    edtDescricao.setError("Não deixe o campo vazio");
                    edtDescricao.requestFocus();
                }else if(edtQuantidade.getText().length()==0){
                    edtQuantidade.setError("Não deixe o campo vazio");
                    edtQuantidade.requestFocus();

                }else if (edicao){
                    alterarProduto();
                }else{
                    salvarProduto();
                }

            }
        });
        consCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Fecha o teclado ao clicar na tela
                CustomKeyBoard.keyboardHidenn(getApplicationContext());
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CadActivity.this, "Cancelado", Toast.LENGTH_SHORT).show();
                finish();
                releaseInstance();
            }
        });

    }
    /**Método que salva o produto atraves da classe DAO*/
    private void salvarProduto(){
        if (isOk()) {
            Produto produto = new Produto(edtDescricao.getText().toString(),
            Integer.parseInt(edtQuantidade.getText().toString()));
            ProdutoDAO produtoDAO = new ProdutoDAO(getApplicationContext());
            produtoDAO.salvar(produto);
            Toast.makeText(CadActivity.this, "Cadastrado", Toast.LENGTH_SHORT).show();
        }
            finishAfterTransition();
            releaseInstance();
    }
    /**Método para alterar um produto*/
    private void alterarProduto(){
        if (isOk()){
            currentProduto.setDescricao(edtDescricao.getText().toString());
            currentProduto.setQuantidade(Integer.parseInt(edtQuantidade.getText().toString()));
            ProdutoDAO produtoDAO = new ProdutoDAO(getApplicationContext());
            produtoDAO.atualizar(currentProduto);
        }
        finishAfterTransition();
        releaseInstance();

    }
    /**Método que verifica se os campos estão operantes*/
    private boolean isOk(){
        if (edtDescricao.getText().length() ==0 || edtQuantidade.getText().length() ==0) {
            return false;
        }else{
            return true;
        }
    }


}















