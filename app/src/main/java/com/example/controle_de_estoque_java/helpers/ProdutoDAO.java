package com.example.controle_de_estoque_java.helpers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.controle_de_estoque_java.models.Produto;
import com.example.controle_de_estoque_java.my_codes.IModelDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsavel por fazer a interação do aplicativo com o banco de dados*/
public class ProdutoDAO implements IModelDAO<Produto> {
    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public ProdutoDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        this.escreve = dbHelper.getWritableDatabase();
        le = dbHelper.getReadableDatabase();

    }
    /**
     * Método que salva o objeto selecionado no SQLite
     * @param produto recebe o objeto que se deseja salvar
     * */
    @Override
    public boolean salvar(Produto produto) {
        try{
            ContentValues cv = new ContentValues();
            cv.put("descricao",produto.getDescricao());
            cv.put("quantidade",(produto.getQuantidade()));
            escreve.insert( DbHelper.TABELA_PRODUTOS,null,cv);
            Log.i("INFO COX","Produto "+produto.getDescricao()+" criado com Sucesso. " + cv);
            listar();
            return  true;
        }catch (Exception e) {
            Log.i("INFO COX","ERRO ao salvar produtos. COD: " + e.getMessage());
            return false;
        }
    }
    /**
     * Método que atualiza o objeto selecionado no SQLite
     * @param produto recebe o objeto que se deseja atualizar
     * */
    @Override
    public boolean atualizar(Produto produto) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("descricao", produto.getDescricao());
            cv.put("quantidade", produto.getQuantidade());
            String[] args = {produto.getId().toString()};
            escreve.update(DbHelper.TABELA_PRODUTOS, cv, "id=?", args);
            Log.i("INFO COX", "Produto " + produto.getDescricao() + " alterada com Sucesso.");
            return true;
        } catch (Exception e) {
            Log.i("INFO COX", "ERRO ao alterar produto. COD: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método que deleta o objeto selecionado no SQLite
     * @param produto recebe o objeto que se deseja deletar
     * */
    @Override
    public boolean deletar(Produto produto) {
        try {
            String[] args = {produto.getId().toString()};
            escreve.delete(DbHelper.TABELA_PRODUTOS, "id=?", args);
            Log.i("INFO COX", "Produto " + produto.getDescricao() + " deletado com Sucesso.");
            return true;
        } catch (Exception e) {
            Log.i("INFO COX", "ERRO ao deletar produto. COD: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método que retorna uma lista dos objetos armazenados no SQLite
     * @return lista com os objetos presentes no banco de dados
     * */
    @Override
    public List<Produto> listar() {
        try {
            List<Produto> productList = new ArrayList<>();
            String sql = "SELECT * FROM " + DbHelper.TABELA_PRODUTOS + " ;";
            Cursor cursor = le.rawQuery(sql, null);
            while (cursor.moveToNext()) {

                @SuppressLint("Range") Long id = cursor.getLong(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String descricao = cursor.getString(cursor.getColumnIndex("descricao"));
                @SuppressLint("Range") int quantidade = cursor.getInt(cursor.getColumnIndex("quantidade"));

                productList.add(new Produto(id, descricao, quantidade));
                Log.i("COX", "Listando: id=" + id + "| descricao= " + descricao + "| quantidade= " + quantidade);
            }
            return productList;
        }catch (Exception e) {
            Log.i("COX", "Erro ao listar produtos: " + e.getMessage());
            return null;
        }
    }
}
