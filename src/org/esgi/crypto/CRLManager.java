package org.esgi.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import org.apache.commons.codec.binary.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.x509.CRLReason;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V2CRLGenerator;

/**
 *
 * @author Bill
 */
public class CRLManager {
    
    private X509CRL crl;
    private X509V2CRLGenerator crlGen;
    private X509Certificate caCrlCert;
    private PrivateKey caCrlPrivateKey;


    public void createCRL(String keystore, String password, String certificateAlias, String keyAlias, String passKey) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, NoSuchProviderException, InvalidKeyException, SignatureException {
        crlGen = new X509V2CRLGenerator();
        Security.addProvider(new BouncyCastleProvider());
        Calendar cal = new GregorianCalendar();
        Date now = cal.getTime();
        cal.add(Calendar.YEAR, 1);
        Date nextUpdate = cal.getTime();

        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(new BufferedInputStream(new FileInputStream(keystore)), password.toCharArray());

        caCrlCert = (X509Certificate) ks.getCertificate(certificateAlias);

        KeyPair kp = getPrivateKey(ks, keyAlias, passKey.toCharArray());
        caCrlPrivateKey = kp.getPrivate();

        crlGen.setIssuerDN(new X500Principal("CN=www.pkimanager.org"));

        crlGen.setThisUpdate(now);
        crlGen.setNextUpdate(nextUpdate);
        crlGen.setSignatureAlgorithm(caCrlCert.getSigAlgName());
        
        try {
			crl = crlGen.generate(caCrlPrivateKey);
		} catch (CRLException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}

    }

    public void save(String path) throws IOException, CRLException {
        FileWriter fstream = new FileWriter(path);
        BufferedWriter out = new BufferedWriter(fstream);
        out.write("-----BEGIN CERTIFICATE-----\n");
        out.write(Base64.encodeBase64String(crl.getEncoded()));
        out.write("-----END CERTIFICATE-----");
        out.close();
    }

    public X509CRL getCrl() {
        return crl;
    }

    public void revoke(String crlPath,X509Certificate cert, int reason) throws IOException, CertificateParsingException, NoSuchProviderException, SecurityException, SignatureException, InvalidKeyException, CRLException {
        Calendar cal = new GregorianCalendar();
        Date now = cal.getTime();
        cal.add(Calendar.YEAR, 1);
        crlGen.addCRLEntry(cert.getSerialNumber(), now, reason);

        try {
			crl = crlGen.generate(caCrlPrivateKey);
		} catch (IllegalStateException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
        
        save(crlPath);

    }

    public KeyPair getPrivateKey(KeyStore keystore, String alias, char[] password) {
        try {
            // Get private key
            Key key = keystore.getKey(alias, password);
            if (key instanceof PrivateKey) {
                // Get certificate of public key
                java.security.cert.Certificate cert = keystore.getCertificate(alias);

                // Get public key
                PublicKey publicKey = cert.getPublicKey();

                // Return a key pair
                return new KeyPair(publicKey, (PrivateKey) key);
            }
        } catch (UnrecoverableKeyException e) {
        } catch (NoSuchAlgorithmException e) {
        } catch (KeyStoreException e) {
        }
        return null;
    }

    public CRLManager() throws InvalidKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, NoSuchProviderException, SignatureException, IOException, CRLException
    {
    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());


    }
}
