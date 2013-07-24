<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PKIProject</title>

<style>
.box-table-a {
	position: relative;
	font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
	font-size: 12px;
	margin: 45px;
	width: 480px;
	text-align: left;
	border-collapse: collapse;
}

.box-table-a th {
	font-size: 13px;
	font-weight: normal;
	padding: 8px;
	background: #b9c9fe;
	border-top: 4px solid #aabcfe;
	border-bottom: 1px solid #fff;
	color: #039;
}

.box-table-a td {
	padding: 8px;
	background: #e8edff;
	border-bottom: 1px solid #fff;
	color: #669;
	border-top: 1px solid transparent;
}

.box-table-a tr:hover td {
	background: #d0dafd;
	color: #339;
}
</style>


</head>




<script type="text/javascript">
	var clicEnCours = false;
	var position_x = 0;
	var position_y = 0;
	var origineDiv_x = 0;
	var iExplorer = false;
	var deplacable = "";
	if (document.all) {
		iExplorer = true;
	}
	function boutonPresse(pDiv) {
		chaineX = document.getElementById(pDiv).style.left;
		chaineY = document.getElementById(pDiv).style.top;
		origineDiv_x = x - chaineX.substr(0, chaineX.length - 2);
		origineDiv_y = y - chaineY.substr(0, chaineY.length - 2);
		clicEnCours = true;
		deplacable = pDiv;
		document.getElementById(deplacable).style.cursor = 'move';
		document.getElementById(deplacable).style.zIndex = 100;
		document.getElementById(deplacable).style.border = '1px #000000 dotted';
	}

	function boutonRelache(pDiv) {
		clicEnCours = false;
		document.getElementById(deplacable).style.cursor = 'default';
		document.getElementById(deplacable).style.zIndex = 1;
		document.getElementById(deplacable).style.border = '1px #000000 solid';
		deplacable = "";
	}

	function deplacementSouris(e) {

		x = (iExplorer) ? event.x + document.body.scrollLeft : e.pageX;
		y = (iExplorer) ? event.y + document.body.scrollTop : e.pageY;

		if (clicEnCours && document.getElementById) {
			position_x = x - origineDiv_x;
			position_y = y - origineDiv_y;
			document.getElementById(deplacable).style.left = position_x;
			document.getElementById(deplacable).style.top = position_y;
		}
	}

	if (!iExplorer) {
		document.captureEvents(Event.MOUSEMOVE);
	}
	document.onmousemove = deplacementSouris;

	function ViewName() {
		var saisie = prompt("Comment souhaitez vous appeller votre vue", "");

		while (saisie == "") {
			alert("Attention vous devez imperativement donner un nom a votre vue");
			saisie = prompt("Comment souhaitez vous appeller votre vue", "");
		}

		document.getElementById('nom_vue').value = saisie;

	}
</script>

<body>
	<%@ include file="Header.jsp"%>
	<link href="css/menu_assets/styles.css" rel="stylesheet"
		type="text/css">

	<div id='cssmenu'>
		<ul>
			<li class='first'><a href='Crypto'><span>Génération
						de certificats</span></a></li>
			<li class='active'><a href='CRLGen'><span>Génération
						de la CRL</span></a></li>
			<li><a href='Sign'><span>Signer un fichier</span></a></li>
			<li class='last'><a href='#'><span>Contact</span></a></li>
		</ul>
	</div>

	<h2 align="center">
		<br> <br>Génération de CRL<br> <br>
	</h2>
	<form action="CRLGen" method="POST">
		<table class="box-table-a" id="div0"
			onmousedown="boutonPresse('div0')" onmouseup="boutonRelache('div0')">

			<thead>

				<tr>
					<th scope="col">Liste des certificats</th>
					<th scope="col"></th>
				</tr>
			</thead>




			<tbody>
				<tr>
					<td>Numero de serie</td>
					<td>Date de revocation</td>
				</tr>
				<%
					java.util.ArrayList<String> crl = (java.util.ArrayList<String>) request
							.getAttribute("crl");
					for (String certif : crl) {
						out.println("<tr>");
						
						out.println("<td>");
						out.println(certif.substring(0,certif.indexOf(" ")));
						out.println("</td>");
						
						out.println("<td>");
						out.println(certif.substring(certif.indexOf(" ")));
						out.println("</td>");
						
						out.println("</tr>");
					}
				%>

			</tbody>

		</table>
		<ul>

		</ul>
		<br> <br> <br> <input value="Générer le CRL"
			type="submit" onclick="">
	</form>

</body>
</html>