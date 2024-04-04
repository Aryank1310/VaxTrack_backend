package com.hughes.vms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class PdfController {
	@PostMapping("/createPdf")
	public void createPdf() {
		
		
	}

}
