package br.unisinos.arquitetura.t4.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Pessoa extends PanacheEntity {

		private String nome;

		private String telefone;

		@ManyToMany(cascade = { CascadeType.ALL })
		@JoinTable(name = "Pessoa_Endereco", joinColumns = { @JoinColumn(name = "pessoa_id") }, inverseJoinColumns = {
						@JoinColumn(name = "endereco_id") })
		private Set<Endereco> endereco = new HashSet<>();

		public Long getId() {
				return this.id;
		}

		public String getNome() {
				return this.nome;
		}

		public void setNome(String nome) {
				this.nome = nome;
		}

		public Set<Endereco> getEndereco() {
				return this.endereco;
		}

		public void setEndereco(Set<Endereco> endereco) {
				this.endereco = endereco;
		}
}
