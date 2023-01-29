package com.mlany.app.persistence.entity.mlany.problem;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.mlany.app.persistence.entity.Beanable;
import com.mlany.app.persistence.entity.Beanables;
import com.mlany.app.persistence.entity.mlany.MlanyEntity;
import com.mlany.app.persistence.entity.mlany.dataset.Dataset;
import com.mlany.app.persistence.entity.mlany.model.ModelTraining;
import com.mlany.app.ws.bean.mlany.common.ColumnRawBean;
import com.mlany.app.ws.bean.mlany.problem.ProblemSolvingBean;
import com.mlany.app.ws.bean.mlany.problem.ProblemSolvingRawBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PROBLEM_SOLVING")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {})
public class ProblemSolving extends MlanyEntity implements Beanable<ProblemSolvingBean> {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PROBLEM_ID", nullable = true)
	private Problem problem;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DATASET_ID", nullable = true)
	private Dataset dataset;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "problemSolving", orphanRemoval = true)
	private List<ProblemSolvingColumn> problemSolvingColumns = new ArrayList<>();

	@Column(name = "STATUS", length = 50, nullable = true)
	private String status;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "problemSolving", orphanRemoval = true)
	private List<ModelTraining> modelTrainings = new ArrayList<>();

	@Override
	public ProblemSolvingBean toBean() {
		ProblemSolvingBean bean = new ProblemSolvingBean();
		bean.setId(getId());
		bean.setProblemId(getProblem() != null ? getProblem().getId() : null);
		bean.setDatasetId(getDataset() != null ? getDataset().getId() : null);
		bean.setStatus(getStatus());
		bean.setModelTrainings(Beanables.toBeanList(getModelTrainings()));
		return bean;
	}

	public ProblemSolvingRawBean toRawBean() {
		ProblemSolvingRawBean bean = new ProblemSolvingRawBean();
		bean.setProblemId(getProblem() != null ? getProblem().getId() : null);
		bean.setDatasetId(getDataset() != null ? getDataset().getId() : null);

		List<ColumnRawBean> columns = new ArrayList<>();
		getProblemSolvingColumns().stream().forEach(column -> columns.add(column.toRawBean()));
		bean.setColumns(columns);

		return bean;
	}

}
