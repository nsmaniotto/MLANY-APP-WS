package com.mlany.app.persistence.entity.mlany.dummy;

@Entity
@Table(name = "DUMMY")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {})
public class Dummy extends MlanyEntity implements Beanable<DummyBean> {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "NAME");
	private String name;
}