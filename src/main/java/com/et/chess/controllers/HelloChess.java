package com.et.chess.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.et.chess.service.ProtocolServerImpl;
import com.et.chess.util.ChessUtils;
import com.google.gson.Gson;

@RestController
@RequestMapping("/AKTeam")
public class HelloChess {
	
	 @RequestMapping(value = "/2020")
	    public CompletableFuture<String> join(@RequestParam("protocol") String protocol) {
	        return CompletableFuture.supplyAsync(() -> {
	            Map<String, Object> map = new HashMap<>();
	            map.put("code", 1);
	            map.put("msg", "Hello,Chess");
	            return new Gson().toJson(map);
	        });
	    }
}
