package org.esgi.crypto;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.DataLengthException;


/**
 * Servlet implementation class Crypto
 */
@WebServlet("/Crypto")
public class Crypto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Crypto() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			//System.out.println(new X509CertificateGenerator("ca.p12", "test password", "Test CA", false).createCertificate("Test CN", 30, "test.p12", "test"));
		X509CertificateGenerator xcg = new X509CertificateGenerator(caFile, caPassword, caAlias, useBCAPI)
		
		} catch (InvalidKeyException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		} catch (DataLengthException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		} catch (CryptoException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher( "/WEB-INF/Crypto.jsp" ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.getServletContext().getRequestDispatcher( "/WEB-INF/Crypto.jsp" ).forward( request, response );
	}

}
