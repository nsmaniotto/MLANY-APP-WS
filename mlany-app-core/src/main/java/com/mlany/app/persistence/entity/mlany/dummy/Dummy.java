package com.mlany.app.persistence.entity.mlany.dummy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mlany.app.persistence.entity.Beanable;
import com.mlany.app.persistence.entity.mlany.MlanyEntity;
import com.mlany.app.ws.bean.mlany.dummy.DummyBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "DUMMY")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {})
public class Dummy extends MlanyEntity implements Beanable<DummyBean> {

	private static final long serialVersionUID = 1L;

	@Column(name = "NAME", length = 50, nullable = true)
	private String name;

	@Override
	public DummyBean toBean() {
		DummyBean bean = new DummyBean();
		bean.setId(getId());
		bean.setName(getName());
		return bean;
	}

}
