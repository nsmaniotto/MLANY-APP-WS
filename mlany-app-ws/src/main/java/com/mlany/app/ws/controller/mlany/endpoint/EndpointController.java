package com.mlany.app.ws.controller.mlany.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mlany.app.ws.bean.mlany.endpoint.EndpointBean;
import com.mlany.app.ws.services.mlany.endpoint.EndpointService;

@RestController
@RequestMapping("endpoint")
public class EndpointController {

	@Autowired
	private EndpointService endpointService;

	@PostMapping(value = "create")
	public Long create(@RequestBody EndpointBean endpointBean) {
		return endpointService.save(endpointBean).getId();
	}
}
