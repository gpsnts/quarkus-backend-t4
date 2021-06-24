package br.unisinos.arquitetura.t4.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Estoque extends PanacheEntity {

    @OneToOne()
    @JoinColumn(name = "produto_id", referencedColumnName = "id")
    private Produto produto;

    private String sku;

    public Produto getProduto() {
        return this.produto;
    }

    public String getSku() {
        return this.sku;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Long getId() {
        return this.id;
    }
}
