package org.esgi.crypto;

import java.io.*;
import java.util.*;
import java.security.*;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.cert.X509Certificate;

public class CertGen {

	// CHARGEMENT DU FICHIER PKCS#12
	public CertGen(String ALIAS, char[] password, String filepath){
		
	KeyStore ks = null;


	Security.addProvider(new BouncyCastleProvider());
	try {
		ks = KeyStore.getInstance("PKCS12");
		// Password pour le fichier personnal_nyal.p12

		ks.load(new FileInputStream(filepath), password);
	} catch (Exception e) {
		System.out.println("Erreur: fichier " +
	                       "personnal_nyal.p12" +
	                       " n'est pas un fichier pkcs#12 valide ou passphrase incorrect");
		return ;
	}

	// RECUPERATION DU COUPLE CLE PRIVEE/PUBLIQUE ET DU CERTIFICAT PUBLIQUE

	X509Certificate cert = null;
	PrivateKey privatekey = null;
	PublicKey publickey = null;

	try {
		Enumeration en = ks.aliases();
		Vector vectaliases = new Vector();

		while (en.hasMoreElements())
			vectaliases.add(en.nextElement());
		String[] aliases = (String []) (vectaliases.toArray(new String[0]));
		for (int i = 0; i < aliases.length; i++)
			if (ks.isKeyEntry(aliases[i]))
			{
				ALIAS = aliases[i];
				break;
			}
		privatekey = (PrivateKey)ks.getKey(ALIAS, password);
		cert = (X509Certificate)ks.getCertificate(ALIAS);
		publickey = ks.getCertificate(ALIAS).getPublicKey();
	} catch (Exception e) {
		e.printStackTrace();
		return ;
	}
	}
}
