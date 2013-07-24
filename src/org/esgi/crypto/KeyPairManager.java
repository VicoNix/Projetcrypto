/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 * Classe permettant de gÃ©rer une clÃ© publique et une clÃ© privÃ©e
 * @author william.pastor
 */
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

    /**
     * Sauvegarde la clÃ© dans un fichier
     * @param filename : nom du fichier dans laquelle la clÃ© sera sauvegardÃ©e
     */
    public void savePubKey(String filename) {
        saveKey(filename, pair.getPublic());
    }

    /**
     * Sauvegarde la clÃ© privÃ©e dans un fichier
     * @param filename : nom dans laquelle la clÃ© sera sauvegardÃ©e'
     */
    public void savePriKey(String filename) {
        saveKey(filename, pair.getPrivate());
    }
    
    /**
     * MÃ©thode gÃ©nÃ©rique pour sauvegarder une clÃ© dans un fichier
     * @param filename : nom du fichier dans laquelle la clÃ© sera sauvegardÃ©e
     * @param generalKey : clÃ© Ã  sauvegarder
     */
    private void saveKey(String filename, Key generalKey) {
        FileOutputStream keyfos = null;
        try {
            /* save the public key in a file */
            byte[] key = generalKey.getEncoded();
            keyfos = new FileOutputStream(new File(folder + filename));
//            ObjectOutputStream oos = new ObjectOutputStream(keyfos);
//            oos.writeObject(priKey);
//            oos.flush();
//            oos.close();
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
