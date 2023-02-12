package com.mlany.app.persistence.entity.enumeration.problem;

import java.util.Arrays;
import java.util.Optional;

import lombok.Getter;

@Getter
public enum ProblemSolvingTypeEnum {
	MULTI_CLASSIFICATION("Multi-Classification"), CLASSIFICATION("Classification"), REGRESSION("Regression");

	private String label;

	private ProblemSolvingTypeEnum(String label) {
		this.label = label;
	}

	public static Optional<ProblemSolvingTypeEnum> findProblemSolvingTypeEnumByLabel(String label) {
		return Arrays.asList(ProblemSolvingTypeEnum.values()).stream()
				.filter(problemSolvingTypeEnum -> problemSolvingTypeEnum.getLabel().equals(label)).findFirst();
	}
}
