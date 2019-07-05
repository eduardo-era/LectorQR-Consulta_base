<?php
$elhash = $_REQUEST["hash1"];
$con = mysqli_connect("www.racni.com","qrcodes","F354c4tl4n", "1ncub4d0r4") or die ("Sin Conexion");

$sql = "SELECT id_usuario, nombre FROM Usuario Where hash = '$elhash'";

$datos = Array();

$resul = mysqli_query($con,$sql); 

while($row = mysqli_fetch_object($resul))
{
		$datos[] = $row;
}
echo json_encode($datos);
mysqli_close($con);
?>