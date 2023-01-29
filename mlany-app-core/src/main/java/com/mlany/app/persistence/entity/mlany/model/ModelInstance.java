package com.mlany.app.persistence.entity.mlany.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mlany.app.persistence.entity.Beanable;
import com.mlany.app.persistence.entity.common.FileInfo;
import com.mlany.app.persistence.entity.mlany.MlanyEntity;
import com.mlany.app.ws.bean.mlany.model.ModelInstanceBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MODEL_INSTANCE")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {})
public class ModelInstance extends MlanyEntity implements Beanable<ModelInstanceBean> {

	private static final long serialVersionUID = 1L;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "MODEL_NAME", referencedColumnName = "NAME", nullable = false),
			@JoinColumn(name = "MODEL_FAMILY", referencedColumnName = "FAMILY", nullable = false) })
	private Model model;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FILE_INFO_ID", nullable = true)
	private FileInfo fileInfo;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "modelInstance", orphanRemoval = true)
	private List<ModelInstanceParameter> parameters = new ArrayList<>();

	@Override
	public ModelInstanceBean toBean() {
		ModelInstanceBean bean = new ModelInstanceBean();
		bean.setId(getId());
		bean.setModel(getModel() != null ? getModel().toBean() : null);
		bean.setFileInfo(getFileInfo() != null ? getFileInfo().toBean() : null);
		return bean;
	}

}
