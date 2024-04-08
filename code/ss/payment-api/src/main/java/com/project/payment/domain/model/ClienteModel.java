package com.project.payment.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteModel {
	private Long id;
	private String name;
	private String email;
	private String phone;
}
