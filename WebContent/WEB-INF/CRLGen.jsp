<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PKIProject</title>
</head>
<body>
    <%@ include file="Header.jsp"%>
    <link href="css/menu_assets/styles.css" rel="stylesheet" type="text/css">

<div id='cssmenu'>
<ul>
   <li class='first'><a href='Crypto'><span>Génération de certificats</span></a></li>
    <li class='active'><a href='CRLGen'><span>Génération de la CRL</span></a></li>
        <li><a href='Sign'><span>Signer un fichier</span></a></li>
   <li class='last'><a href='#'><span>Contact</span></a></li>
</ul>
</div>

<h2 align="center"><br><br>Génération de CRL<br><br></h2>
<form action="CRLGen" method="POST">
		
		<ul>
		<%
		java.util.ArrayList<String> crl = (java.util.ArrayList<String>)request.getAttribute("crl");
		for(String certif : crl){
			out.println("<li>");
			out.println(certif);
			out.println("</li>");
		}
		 %>
		</ul>
		<br>
		<br>
		 <br>
		<input value="Générer le CRL" type="submit" onclick="">
	</form>

</body>
</html>