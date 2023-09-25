package com.ojavali.doisrponto.usuarios;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "usuarios")
@Entity
public class User {
	@Id
	private int matricula;

	private String nome;

	private String senha;

	@Column(name = "categoria")
	private UserRole categoria;

	private String email;

	private String recoverPasswordToken;
}
