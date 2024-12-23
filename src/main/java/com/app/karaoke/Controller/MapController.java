package com.app.karaoke.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

//Map 컨트롤러

@Slf4j
@Controller
public class MapController {
	
	@GetMapping("/map")
	public String Map() {
		return "map";
	}
}
