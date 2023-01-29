package com.mlany.app.persistence.entity.mlany.dataset;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class DatasetColumnCorrelationId implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long firstColumn;

	private Long secondColumn;
}
