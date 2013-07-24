package org.esgi.crypto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeyPairManager {

    private String folder = "";
    private KeyPair pair;
    
    public KeyPairManager() throws  GeneralSecurityException{
        this("DSA", "SUN");
    }
    
    public KeyPairManager(String algoName) throws  GeneralSecurityException{
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algoName);
        SecureRandom random = new SecureRandom();
        keyGen.initialize(1024, random);

        pair = keyGen.generateKeyPair();
    }
    
    public KeyPairManager(String algoName, String provider) throws GeneralSecurityException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algoName, provider);
        SecureRandom random = new SecureRandom();
        keyGen.initialize(1024, random);

        pair = keyGen.generateKeyPair();
    }

   // Sauvegarde clé public dans un fichier
    public void savePubKey(String filename) {
        saveKey(filename, pair.getPublic());
    }

    // Sauvegarde clé privé dans fichier
    public void savePriKey(String filename) {
        saveKey(filename, pair.getPrivate());
    }
    
   // Save clé dans un fichier (generique)
    private void saveKey(String filename, Key generalKey) {
        FileOutputStream keyfos = null;
        try {
            /* save the public key in a file */
            byte[] key = generalKey.getEncoded();
            keyfos = new FileOutputStream(new File(folder + filename));
            keyfos.write(key);
            keyfos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KeyPairManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KeyPairManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public PrivateKey getPrivateKey() {
        return pair.getPrivate();
    }

    public PublicKey getPublicKey() {
        return pair.getPublic();
    }

    public KeyPair getPair() {
        return pair;
    }

    public void setPair(KeyPair pair) {
        this.pair = pair;
    }
}
