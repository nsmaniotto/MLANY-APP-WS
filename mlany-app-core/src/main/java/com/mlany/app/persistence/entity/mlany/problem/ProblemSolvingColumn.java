package com.mlany.app.persistence.entity.mlany.problem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mlany.app.persistence.entity.Beanable;
import com.mlany.app.persistence.entity.mlany.MlanyEntity;
import com.mlany.app.ws.bean.mlany.common.ColumnRawBean;
import com.mlany.app.ws.bean.mlany.problem.ProblemSolvingColumnBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PROBLEM_SOLVING_COLUMN")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, of = {})
public class ProblemSolvingColumn extends MlanyEntity implements Beanable<ProblemSolvingColumnBean> {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROBLEM_SOLVING_ID", nullable = false)
	private ProblemSolving problemSolving;

	@Column(name = "NAME", length = 250, nullable = false)
	private String name;

	@Column(name = "TYPE", length = 50, nullable = true)
	private String type;

	@Column(name = "INPUT_OUPUT", length = 50, nullable = true)
	private String inputOutput;

	@Override
	public ProblemSolvingColumnBean toBean() {
		ProblemSolvingColumnBean bean = new ProblemSolvingColumnBean();
		bean.setId(getId());
		bean.setProblemSolvingId(getProblemSolving() != null ? getProblemSolving().getId() : null);
		bean.setName(getName());
		bean.setType(getType());
		bean.setInputOutput(getInputOutput());
		return bean;
	}

	public ColumnRawBean toRawBean() {
		ColumnRawBean bean = new ColumnRawBean();
		bean.setName(getName());
		bean.setType(getType());
		bean.setInputOutput(getInputOutput());
		return bean;
	}

}
