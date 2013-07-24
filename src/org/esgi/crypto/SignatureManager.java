package org.esgi.crypto;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class SignatureManager {

	 private byte[] realSig;

	    public void sign(File file, File privKey) {
	        try {
	            byte encKey[] = getEncodedValue(privKey);

	            PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(encKey);
	                        
	            KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
	            
	            PrivateKey priv = keyFactory.generatePrivate(priKeySpec);

	            Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
	            dsa.initSign(priv);

	            FileInputStream fis = new FileInputStream(file);
	            BufferedInputStream bufin = new BufferedInputStream(fis);
	            byte[] buffer = new byte[1024];
	            int len;
	            while ((len = bufin.read(buffer)) >= 0) {
	                dsa.update(buffer, 0, len);
	            };
	            bufin.close();

	            realSig = dsa.sign();

	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
	    }

	    public boolean verifSign(File pub_key, File sign, File file) {
	        boolean verifies = false;
	        try {
	            byte encKey[] = getEncodedValue(pub_key);

	            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);

	            KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");

	            PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);

	            FileInputStream sigfis = new FileInputStream(sign);
	            byte[] sigToVerify = new byte[sigfis.available()];
	            sigfis.read(sigToVerify);
	            sigfis.close();

	            Signature sig = Signature.getInstance("SHA1withDSA", "SUN");

	            sig.initVerify(pubKey);

	            FileInputStream datafis = new FileInputStream(file);
	            BufferedInputStream bufin = new BufferedInputStream(datafis);

	            byte[] buffer = new byte[1024];
	            int len;
	            while (bufin.available() != 0) {
	                len = bufin.read(buffer);
	                sig.update(buffer, 0, len);
	            };

	            bufin.close();

	            verifies = sig.verify(sigToVerify);            

	        } catch (Exception e) {

	            System.err.println("Caught exception " + e.toString());
	        }
	        return verifies;
	    }
	    
	    private byte[] getEncodedValue(File file) throws  IOException {
	        FileInputStream keyfis = new FileInputStream(file);
	        byte encKey[] = new byte[keyfis.available()];
	        keyfis.read(encKey);

	        keyfis.close();
	        
	        return encKey;
	    }

	    public void save(String file) throws FileNotFoundException, IOException {
	        /* save the signature in a file */
	        FileOutputStream sigfos = new FileOutputStream(file);
	        sigfos.write(realSig);
	        sigfos.close();
	    }

	    public SignatureManager(String path) throws GeneralSecurityException, IOException {

	        KeyPairManager myKeys = new KeyPairManager();

	        myKeys.savePriKey("certificat.pem");
	        myKeys.savePubKey("certonly.pem");

	        File data = new File("/home/victor/Bureau/test.xml");
	        
	        //signature
	        sign(data, new File("certificat.pem"));
	        save(path+"signature");
	        
	        //verif
	        File suepk = new File("certonly.pem");
	        File sig = new File(path+"signature");

	        System.out.println(verifSign(suepk, sig, data));

	    }
	}