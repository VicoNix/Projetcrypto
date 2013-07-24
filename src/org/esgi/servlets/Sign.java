package org.esgi.servlets;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.esgi.crypto.SignatureManager;

/**
 * Servlet implementation class Sign
 */
@WebServlet("/Sign")
public class Sign extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sign() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		try {
			SignatureManager sm = new SignatureManager(getServletContext().getRealPath("/"));
		} catch (GeneralSecurityException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher( "/WEB-INF/Sign.jsp" ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		File fi = new File(getServletContext().getRealPath("/")+"signature");
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
        out1.close();	}

}
