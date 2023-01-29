package com.mlany.app.ws.bean.mlany.problem;

import java.io.Serializable;

import com.mlany.app.ws.bean.AbstractBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class ProblemSolvingColumnBean implements Serializable, AbstractBean {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long problemSolvingId;
	private String name;
	private String type;
	private String inputOutput;

	@Override
	public String toString() {
		return "DatasetColumnBean [id=" + id + ", problemSolvingId=" + problemSolvingId + ", name=" + name + ", type="
				+ type + ", inputOutput=" + inputOutput + "]";
	}
}