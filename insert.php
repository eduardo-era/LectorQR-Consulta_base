<?php
$elidevento = $_REQUEST["id_evento"];
$elidusuario = $_REQUEST["id_usuario"];
$con = mysqli_connect("www.racni.com","qrcodes","F354c4tl4n", "1ncub4d0r4") or die ("Sin Conexion");

$ide = (int) $elidevento;
$idu = (int) $elidusuario;

$sql = "INSERT INTO Asistencia (id_evento,id_usuario) VALUES ($ide,$idu)";

$datos = Array();

$resul = mysqli_query($con,$sql);

while($row = mysqli_fetch_object($resul))
{
		$datos[] = $row;
}

echo json_encode($datos);

mysqli_close($con);
?>