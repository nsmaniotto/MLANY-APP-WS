package com.mlany.app.ws.controller.mlany.dummy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mlany.app.ws.services.mlany.dummy.DummyService;
import com.mlany.app.ws.bean.mlany.dummy.DummyBean;

@RestController
@RequestMapping("dummy")
public class DummyController {

	@Autowired
	private DummyService dummyService;

	@GetMapping(value = "{id}")
	public DummyBean get(@PathVariable long id) {
		return dummyService.getDummyBean(id);
	}
}
