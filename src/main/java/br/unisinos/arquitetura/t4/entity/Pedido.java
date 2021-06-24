package br.unisinos.arquitetura.t4.entity;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Pedido extends PanacheEntity {

		@OneToOne()
		@JoinColumn(name = "pessoa_id", referencedColumnName = "id")
		private Pessoa pessoa;

		public Long getId() {
				return this.id;
		}

		public Pessoa getPessoa() {
				return this.pessoa;
		}

		public void setPessoa(Pessoa pessoa) {
				this.pessoa = pessoa;
		}
}
