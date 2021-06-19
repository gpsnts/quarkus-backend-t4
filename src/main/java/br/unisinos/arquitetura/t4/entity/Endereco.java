package br.unisinos.arquitetura.t4.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Endereco extends PanacheEntity {

    private String rua;

    private String numero;

    private String bairro;

    private String cidade;

    private String estado;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pessoa_id", referencedColumnName = "id")
    private Pessoa pessoa;

    public Long getId() {
        return this.id;
    }

    public String getRua() {
        return this.rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return this.bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return this.cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
