package com.codemos.ovsystem.web.controller;

import com.codemos.ovsystem.OverSystem;
import com.codemos.ovsystem.domain.dto.DataDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class GetController {
	@GetMapping("/get/{item}")
	@ResponseBody
	public List<DataDto> get(@PathVariable String item) {
		OverSystem overSystem = new OverSystem();
		
		return overSystem.get(item);
	}
}
