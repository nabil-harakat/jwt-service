package com.jwt.service;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.Nullable;
import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Service
public class JwtService {

	private CertService certService;

	public JwtService(CertService certService) {
		this.certService = certService;
	}

	// Generate a JWT token
	public String createToken(@Nullable Map<String, String> data) throws UnrecoverableKeyException,
			IllegalArgumentException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		// check inputs
		if (data == null) {
			throw new IllegalArgumentException("Data map is null");
		}
		// Validate inputs
		String subject = data.get("sub");
		if (subject == null || subject.isEmpty()) {
			throw new IllegalArgumentException("Subject is required");
		}

		// Generate random IDs for JWT and header
		String jwtId = UUID.randomUUID().toString();
		String kid = UUID.randomUUID().toString();

		// Calculate the expiration date
		Date validity = calculateExpirationDate();

		// Create a JWT with a set of claims
		Builder builder = JWT.create().withSubject(subject).withIssuer("iss").withAudience("aud").withJWTId(jwtId)
				.withIssuedAt(new Date()).withExpiresAt(validity);

		// Create an Algorithm with the private key
		Algorithm algorithm = Algorithm.RSA256(certService.loadRSAPrivateKey());

		// Create a JWS header with the X.509 certificate and algorithm
		Map<String, Object> headerClaims = Map.of("kid", kid);
		builder.withHeader(headerClaims);

		// Sign the JWT with the private key
		String token = builder.sign(algorithm);

		// Print the JWT token
		log.info("JWT Token: " + token);

		return token;

	}

	public void verifyToken(String jwtToken) throws IllegalArgumentException, KeyStoreException,
			NoSuchAlgorithmException, CertificateException, IOException {

		// Define the algorithm and secret key used to sign the JWT
		Algorithm algorithm = Algorithm.RSA256(certService.loadRSAPublicKey());

		// Create a JWTVerifier with the expected algorithm and secret
		JWTVerifier verifier = JWT.require(algorithm).build();

		// Verify the JWT
		try {
			DecodedJWT decodedJWT = verifier.verify(jwtToken);
			log.info("JWT is valid.");
			log.info("Subject: " + decodedJWT.getSubject());
			log.info("Issuer: " + decodedJWT.getIssuer());
			log.info("Expiration: " + decodedJWT.getExpiresAt());
		} catch (Exception e) {
			log.error("JWT verification failed: " + e.getMessage());
		}

	}

	// Calculate the expiration date as 2 hours from the current time
	private Date calculateExpirationDate() {
		LocalDateTime expirationDateTime = LocalDateTime.now().plusHours(2);
		return Date.from(expirationDateTime.toInstant(ZoneOffset.UTC));
	}

}
