package com.mlany.app.ws.bean.mlany.problem;

import java.io.Serializable;
import java.util.List;

import com.mlany.app.ws.bean.AbstractBean;
import com.mlany.app.ws.bean.mlany.model.ModelTrainingBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class ProblemBean implements Serializable, AbstractBean {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String type;
	private List<Long> linkedDatasetIds;
	private List<ProblemSolvingBean> problemSolvings;
	private ModelTrainingBean deployedModelFromTraining;
	private String endPoint;

	@Override
	public String toString() {
		return "ProblemBean [id=" + id + ", name=" + name + ", type=" + type + ", deployedModelFromTraining="
				+ deployedModelFromTraining + ", endPoint=" + endPoint + "]";
	}
}