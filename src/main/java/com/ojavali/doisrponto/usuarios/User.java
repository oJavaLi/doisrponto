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
	private Long matricula;

	private String nome;

	private String senha;

	private String email;

	@Column(name = "categoria")
	private UserRole role;
}
