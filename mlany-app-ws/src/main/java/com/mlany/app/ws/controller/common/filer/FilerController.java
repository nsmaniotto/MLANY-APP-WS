package com.mlany.app.ws.controller.common.filer;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mlany.app.ws.services.common.filer.FilerService;

@RestController
@RequestMapping("filer")
public class FilerController {

	@Autowired
	private FilerService filerService;

	@GetMapping(value = "{fileInfoId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public @ResponseBody byte[] getFile(@PathVariable Long fileInfoId) throws IOException {
		return filerService.getFileAsBytes(fileInfoId);
	}

	@PutMapping(value = "{fileInfoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Boolean updateFile(@PathVariable Long fileInfoId, @RequestBody byte[] content)
			throws IOException {
		return filerService.updateFileContent(fileInfoId, content);
	}
}
