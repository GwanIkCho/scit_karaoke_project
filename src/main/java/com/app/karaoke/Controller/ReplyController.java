package com.app.karaoke.Controller;

import org.springframework.stereotype.Controller;

import com.app.karaoke.Service.ReplyService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReplyController {
	private final ReplyService service;
}
