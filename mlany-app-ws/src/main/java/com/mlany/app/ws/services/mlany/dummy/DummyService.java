package com.mlany.app.ws.services.mlany.dummy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlany.app.persistence.entity.mlany.dummy.Dummy;
import com.mlany.app.persistence.repository.mlany.dummy.DummyRepository;
import com.mlany.app.ws.bean.mlany.dummy.DummyBean;
import com.mlany.app.ws.utils.helper.IntrospectionHelper;

@Service
public class DummyService {

	/* =============== SERVICES =============== */

	/* ============= REPOSITORIES ============= */

	@Autowired
	private DummyRepository dummyRepository;

	public Dummy getDummy(long id) {
		return IntrospectionHelper.findMandatory(dummyRepository, id);
	}

	public DummyBean getDummyBean(long id) {
		return getDummy(id).toBean();
	}
}
