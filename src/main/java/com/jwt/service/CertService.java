package com.jwt.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Enumeration;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class CertService {

	private String pin = "1234";
	private String certPath = "cert/certificate.p12";

	public RSAPublicKey loadRSAPublicKey()
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		KeyStore store = getKeyStore();
		Enumeration<String> aliases = store.aliases();
		String alias = null;
		alias = aliases.nextElement();

		return (RSAPublicKey) store.getCertificate(alias).getPublicKey();

	}

	public RSAPrivateKey loadRSAPrivateKey() throws UnrecoverableKeyException, KeyStoreException,
			NoSuchAlgorithmException, CertificateException, IOException {
		KeyStore store = getKeyStore();
		Enumeration<String> aliases = store.aliases();
		String alias = null;
		alias = aliases.nextElement();

		return (RSAPrivateKey) store.getKey(alias, pin.toCharArray());

	}

	private KeyStore getKeyStore()
			throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException {
		try (InputStream fis = new ClassPathResource(certPath).getInputStream()) {
			KeyStore store = KeyStore.getInstance("PKCS12");
			store.load(fis, pin.toCharArray());
			return store;
		}

	}

}
