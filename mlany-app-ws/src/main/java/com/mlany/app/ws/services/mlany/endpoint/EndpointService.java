package com.mlany.app.ws.services.mlany.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlany.app.persistence.entity.mlany.endpoint.Endpoint;
import com.mlany.app.persistence.repository.mlany.endpoint.EndpointRepository;
import com.mlany.app.persistence.repository.mlany.model.ModelInstanceRepository;
import com.mlany.app.ws.bean.mlany.endpoint.EndpointBean;

@Service
public class EndpointService {

	/* =============== SERVICES =============== */

	/* ============= REPOSITORIES ============= */

	@Autowired
	private EndpointRepository endpointRepository;

	@Autowired
	private ModelInstanceRepository modelInstanceRepository;

	public Endpoint save(EndpointBean endpointBean) {
		Endpoint endpoint = endpointBean.getId() != null
				? endpointRepository.findById(endpointBean.getId()).orElse(new Endpoint())
				: new Endpoint();

		endpoint.setName(endpointBean.getName());

		endpoint.setModelInstance(endpointBean.getModelInstanceId() != null
				? modelInstanceRepository.findById(endpointBean.getModelInstanceId()).orElse(null)
				: null);

		return endpointRepository.save(endpoint);
	}
}
