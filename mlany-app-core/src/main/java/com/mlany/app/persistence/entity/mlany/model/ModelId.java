package com.mlany.app.persistence.entity.mlany.model;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ModelId implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private String family;
}
