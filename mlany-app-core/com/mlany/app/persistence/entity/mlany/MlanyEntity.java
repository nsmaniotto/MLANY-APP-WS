package com.mlany.app.persistence.entity.mlany;

@MappedSuperclass
@EqualsAndHashCode(of = 'id')
public abstract class MlanyEntity implements AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID"
	@GeneratedValue(generator = "sequence-generator")
	@GenericGenerator(name = "sequence-generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = "hibernate_sequence"),
			@Parameter(name = "initial_value", value = "1"),
			@Parameter(name = "increment_size", value = "1")
	})
	protected Long id;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
}