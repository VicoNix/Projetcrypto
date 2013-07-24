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
   <li class='active'><a href='Crypto'><span>Génération de certificats</span></a></li>
    <li><a href='CRLgen'><span>Génération de la CRL</span></a></li>
   <li class='last'><a href='#'><span>Contact</span></a></li>
</ul>
</div>
    
<h2 align="center"><br><br>Génération de certificat<br><br></h2>
<form action="Crypto" method="POST">
		<table>
			<tr>
				<td>DN du détenteur du certificat</td>

				<td><input id="dn" name="dn"></td>
			</tr>

			<tr>
				<td>Mot de passe</td>
				<td><input name="password" id="password" type="password"></td>
			</tr>
			<tr>
				<td>Nombre de jours de validité :</td>
				<td><input name="validitydays" id="validitydays"></td>
			</tr>
		</table>
		<br>
		<br>
		 <br>
		<input value="Générer un certificat" type="submit" onclick="">
	</form>

</body>
</html>