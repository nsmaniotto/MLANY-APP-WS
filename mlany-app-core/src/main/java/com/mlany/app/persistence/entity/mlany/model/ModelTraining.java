package com.mlany.app.persistence.entity.mlany.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mlany.app.persistence.entity.Beanable;
import com.mlany.app.persistence.entity.common.FileInfo;
import com.mlany.app.persistence.entity.mlany.MlanyEntity;
import com.mlany.app.persistence.entity.mlany.problem.ProblemSolving;
import com.mlany.app.ws.bean.mlany.model.ModelTrainingBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MODEL_TRAINING")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {})
public class ModelTraining extends MlanyEntity implements Beanable<ModelTrainingBean> {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PROBLEM_SOLVING_ID", nullable = false)
	private ProblemSolving problemSolving;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MODEL_INSTANCE_ID", nullable = false)
	private ModelInstance modelInstance;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VECTORIZER_FILE_INFO_ID", nullable = true)
	private FileInfo vectorizerFileInfo;

	@Column(name = "STATUS", length = 50, nullable = true)
	private String status;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MODEL_TRAINING_RESULT_ID", nullable = true)
	private ModelTrainingResult modelTrainingResult;

	@Override
	public ModelTrainingBean toBean() {
		ModelTrainingBean bean = new ModelTrainingBean();
		bean.setId(getId());
		bean.setProblemSolvingId(getProblemSolving() != null ? getProblemSolving().getId() : null);
		bean.setModelInstance(getModelInstance() != null ? getModelInstance().toBean() : null);
		bean.setVectorizerFileInfo(getVectorizerFileInfo() != null ? getVectorizerFileInfo().toBean() : null);
		bean.setStatus(getStatus());
		bean.setModelTrainingResult(getModelTrainingResult() != null ? getModelTrainingResult().toBean() : null);
		return bean;
	}

}
