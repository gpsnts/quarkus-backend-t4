package br.unisinos.arquitetura.t4.entity;


import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Categoria extends PanacheEntity {

		private String nome;

		private String descicao;

		public Long getId() {
				return this.id;
		}

		public String getNome() {
				return this.nome;
		}

		public void setNome(String nome) {
				this.nome = nome;
		}

		public String getDescicao() {
				return this.descicao;
		}

		public void setDescicao(String descicao) {
				this.descicao = descicao;
		}
}
