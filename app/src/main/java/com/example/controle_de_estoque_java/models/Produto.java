package com.example.controle_de_estoque_java.models;

import java.io.Serializable;
/** Representa o produto*/
public class Produto implements Serializable {

    private Long id;//Auto gerado pelo SQLite
    private String descricao;
    private int quantidade;

    public Produto(String descricao, int quantidade) {
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    public Produto(Long id, String descricao, int quantidade) {
        this.id = id;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }
    /**
     * id do produto é gerado pelo auto increment do SQLite*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Descrição do produto ou nome*/
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    /**
     * Quantidade do produto*/
    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
