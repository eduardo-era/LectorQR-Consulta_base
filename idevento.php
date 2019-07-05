<?php
$elnombre = $_REQUEST["Nombre_evento"];
$con = mysqli_connect("www.racni.com","qrcodes","F354c4tl4n", "1ncub4d0r4") or die ("Sin Conexion");

mysqli_set_charset($conn,"utf8");
$sql = "SELECT id_evento FROM Evento Where nombre_evento = '$elnombre'";

$datos = Array();

$resul = mysqli_query($con,$sql); 

while($row = mysqli_fetch_object($resul))
{
		$datos[] = $row;
}
echo json_encode($datos);
mysqli_close($con);
?>