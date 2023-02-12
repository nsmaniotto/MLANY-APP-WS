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
public class ProblemSolvingBean implements Serializable, AbstractBean {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long problemId;
	private Long datasetId;
	private List<ProblemSolvingColumnBean> problemSolvingColumns;
	private String status;
	private List<ModelTrainingBean> modelTrainings;

	@Override
	public String toString() {
		return "ProblemSolvingBean [id=" + id + ", status=" + status + ", problemId=" + problemId + ", datasetId="
				+ datasetId + ", problemSolvingColumns=" + problemSolvingColumns + ", modelTrainings=" + modelTrainings
				+ "]";
	}
}