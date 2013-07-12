package org.esgi.crypto;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.x509.CRLReason;
import org.bouncycastle.asn1.x509.CRLNumber;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.x509.X509V2CRLGenerator;
import org.bouncycastle.x509.extension.AuthorityKeyIdentifierStructure;

public class CRLGenerator{
	
	private X509V2CRLGenerator crlGen;
	
	public CRLGenerator()
	{
		crlGen = new X509V2CRLGenerator();
	}
	
	public X509CRL GenerateCRL(long certnumber, PrivateKey caCrlPrivateKey)
	{
X509V2CRLGenerator   crlGen = new X509V2CRLGenerator();
Date                 now = new Date();
Date                 nextUpdate = new Date(2020, 12, 1);

crlGen.setIssuerDN(new X500Principal("CN=Test CA"));

crlGen.setThisUpdate(now);
crlGen.setNextUpdate(nextUpdate);
crlGen.setSignatureAlgorithm("SHA512withRSA");

try {
	return  crlGen.generateX509CRL(caCrlPrivateKey, "BC");
} catch (InvalidKeyException e) {
	// TODO Bloc catch généré automatiquement
	e.printStackTrace();
} catch (NoSuchProviderException e) {
	// TODO Bloc catch généré automatiquement
	e.printStackTrace();
} catch (SecurityException e) {
	// TODO Bloc catch généré automatiquement
	e.printStackTrace();
} catch (SignatureException e) {
	// TODO Bloc catch généré automatiquement
	e.printStackTrace();
}
return null;

}
	
	public void addEntry(long certnumber, Date now, int crlreason)
	{
		//Raison de revocation ex CRLReason.privilegeWithdrawn
		crlGen.addCRLEntry(BigInteger.valueOf(certnumber), now, crlreason);
	}

}