package com.mlany.app.persistence.entity.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.mlany.app.persistence.entity.AbstractEntity;
import com.mlany.app.persistence.entity.Beanable;
import com.mlany.app.ws.bean.common.FileInfoBean;
import com.mlany.app.ws.bean.common.FileInfoRawBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "FILE_INFO")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class FileInfo implements AbstractEntity, Beanable<FileInfoBean> {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "GENERATED_ID", nullable = false)
	@GeneratedValue(generator = "sequence-generator")
	@GenericGenerator(name = "sequence-generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = "SQ_FILE_INFO"), @Parameter(name = "initial_value", value = "1"),
			@Parameter(name = "increment_size", value = "1") })
	protected Long id;

	@Column(name = "PATH", length = 255, nullable = false)
	private String path;

	@Column(name = "NAME", length = 150, nullable = false)
	private String name;

	@Override
	public FileInfoBean toBean() {
		FileInfoBean bean = new FileInfoBean();
		bean.setId(getId());
		bean.setPath(getPath());
		bean.setName(getName());
		return bean;
	}

	public FileInfoRawBean toRawBean() {
		FileInfoRawBean bean = new FileInfoRawBean();
		bean.setPath(getPath());
		bean.setPrefix(new StringBuilder().append(getId()).append("-").toString());
		bean.setName(getName());
		return bean;
	}

}
