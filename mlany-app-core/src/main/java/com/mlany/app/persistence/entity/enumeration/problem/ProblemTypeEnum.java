package com.mlany.app.persistence.entity.enumeration.problem;

import java.util.Arrays;
import java.util.Optional;

import lombok.Getter;

@Getter
public enum ProblemTypeEnum {
	MULTI_CLASSIFICATION("Multi-Classification"), CLASSIFICATION("Classification"), REGRESSION("Regression");

	private String label;

	private ProblemTypeEnum(String label) {
		this.label = label;
	}

	public static Optional<ProblemTypeEnum> findProblemTypeEnumByLabel(String label) {
		return Arrays.asList(ProblemTypeEnum.values()).stream()
				.filter(problemTypeEnum -> problemTypeEnum.getLabel().equals(label)).findFirst();
	}
}
