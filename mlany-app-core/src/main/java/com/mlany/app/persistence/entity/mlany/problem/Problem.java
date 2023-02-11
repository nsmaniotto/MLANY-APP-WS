package com.mlany.app.persistence.entity.mlany.problem;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mlany.app.persistence.entity.Beanable;
import com.mlany.app.persistence.entity.Beanables;
import com.mlany.app.persistence.entity.mlany.MlanyEntity;
import com.mlany.app.persistence.entity.mlany.dataset.Dataset;
import com.mlany.app.persistence.entity.mlany.model.ModelTraining;
import com.mlany.app.ws.bean.mlany.problem.ProblemBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PROBLEM")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {})
public class Problem extends MlanyEntity implements Beanable<ProblemBean> {

	private static final long serialVersionUID = 1L;

	@Column(name = "NAME", length = 50, nullable = false)
	private String name;

	@Column(name = "TYPE", length = 50, nullable = true)
	private String type;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "linkedProblem", orphanRemoval = false)
	private List<Dataset> linkedDatasets = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "problem", orphanRemoval = true)
	private List<ProblemSolving> problemSolvings = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPLOYED_MODEL_FROM_TRAINING_ID", nullable = true)
	private ModelTraining deployedModelFromTraining;

	@Column(name = "END_POINT", length = 20, nullable = true)
	private String endPoint;

	@Override
	public ProblemBean toBean() {
		ProblemBean bean = new ProblemBean();
		bean.setId(getId());
		bean.setName(getName());
		bean.setType(getType());
		bean.setProblemSolvings(Beanables.toBeanList(getProblemSolvings()));
		bean.setDeployedModelFromTraining(
				getDeployedModelFromTraining() != null ? getDeployedModelFromTraining().toBean() : null);
		bean.setEndPoint(getEndPoint());
		return bean;
	}

}
