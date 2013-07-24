package org.esgi.servlets;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bouncycastle.asn1.x509.CRLReason;
import org.esgi.crypto.CRLManager;

/**
 * Servlet implementation class CRLGen
 */
@WebServlet("/CRLGen")
public class CRLGen extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private CRLManager crlM;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CRLGen() {
        super();
       try {
		crlM=new CRLManager();
		//Certificat à révoquer
	        
	} catch (InvalidKeyException e) {
		// TODO Bloc catch généré automatiquement
		e.printStackTrace();
	} catch (KeyStoreException e) {
		// TODO Bloc catch généré automatiquement
		e.printStackTrace();
	} catch (NoSuchAlgorithmException e) {
		// TODO Bloc catch généré automatiquement
		e.printStackTrace();
	} catch (CertificateException e) {
		// TODO Bloc catch généré automatiquement
		e.printStackTrace();
	} catch (NoSuchProviderException e) {
		// TODO Bloc catch généré automatiquement
		e.printStackTrace();
	} catch (SignatureException e) {
		// TODO Bloc catch généré automatiquement
		e.printStackTrace();
	} catch (CRLException e) {
		// TODO Bloc catch généré automatiquement
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Bloc catch généré automatiquement
		e.printStackTrace();
	}
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CertificateFactory cf;
		try {
			crlM.createCRL(getServletContext().getRealPath("/")+"certificat.p12", "toto", "webmail.monsite.fr", "webmail.monsite.fr", "toto");

	        crlM.save(getServletContext().getRealPath("/")+"test.crl");


			cf = CertificateFactory.getInstance("X.509");
			X509Certificate generateCertificate = (X509Certificate) cf.generateCertificate(new FileInputStream(getServletContext().getRealPath("/")+"certonly.pem"));

	        crlM.revoke(getServletContext().getRealPath("/")+"test.crl",generateCertificate, CRLReason.keyCompromise);

	        
		} catch (CertificateException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
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
		} catch (CRLException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
        
		
		request.setAttribute("crl", crlM.displayCrls());
       
		this.getServletContext().getRequestDispatcher( "/WEB-INF/CRLGen.jsp" ).forward( request, response );

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

				
		        System.out.println("path="+getServletContext().getRealPath("/"));
		            File fi = new File(getServletContext().getRealPath("/")+"test.crl");
		             response.setContentType("application/octet-stream" );
		             response.setHeader("Content-Disposition","attachment; filename="+fi.getName()+";" );
		             response.setContentLength ((int)fi.length());
		             
		             ServletOutputStream out1 = response.getOutputStream();
		             BufferedInputStream fif= new BufferedInputStream(new FileInputStream(fi));
		             int data;
		             while((data = fif.read())!=-1) {
		             out1.write(data);
		             }
		             
		             fif.close();
		             out1.close(); 
	}

}
