package com.jwt.controller;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.service.JwtService;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
@RequestMapping("/tokens")
public class JwtRestController {
	private final JwtService jwtService;

	public JwtRestController(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@PostMapping
	public ResponseEntity<Map<String, String>> createToken(@RequestBody Map<String, String> data)
			throws UnrecoverableKeyException, IllegalArgumentException, KeyStoreException, NoSuchAlgorithmException,
			CertificateException, IOException {
		log.info(data);
		String token = jwtService.createToken(data);
		return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("token", token));
	}
	
	@PostMapping(value="/verify")
	public ResponseEntity<Map<String, String>> verify(@RequestBody Map<String, String> data)
			throws UnrecoverableKeyException, IllegalArgumentException, KeyStoreException, NoSuchAlgorithmException,
			CertificateException, IOException {
		log.info(data);
		jwtService.verifyToken(data);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
