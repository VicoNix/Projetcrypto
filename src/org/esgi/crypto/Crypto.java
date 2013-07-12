package org.esgi.crypto;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
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
import javax.servlet.ServletOutputStream;
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
		
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/Crypto.jsp" ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String filepath=getServletContext().getRealPath("/")+"certgen.p12";
		int days = 0;
		try{
		days=Integer.parseInt(request.getParameter("validitydays"));
		}
		catch(NumberFormatException e)
		{
			ServletOutputStream out = response.getOutputStream();
			out.print("<script>alert('Veuillez rensignez un nombre de jours correct !')</script>");
		}
		try {
			if(new X509CertificateGenerator(getServletContext().getRealPath("/")+"certificat.p12", "toto", "webmail.monsite.fr", false).createCertificate((String) request.getParameter("dn"), days, filepath, (String) request.getParameter("password")))
			{
				 File fi = new File(filepath);
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
			else
			{
				ServletOutputStream out = response.getOutputStream();
				out.print("<script>alert('Paramètres non valides !')</script>");
			}
		
		
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
		//this.getServletContext().getRequestDispatcher( "/WEB-INF/Crypto.jsp" ).forward( request, response );
	}

}
