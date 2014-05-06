<?php

require_once "./i_pristupy.php";
require_once "./nlfnorm_fnc.php";
require_once "./i-sgp-sluzby.php";



if(!isLocalhost() && (!isset($_POST['token']) || !isTokenValid($_POST['token']))){
	header('HTTP/1.1 401 Unauthorized', true, 401);
	logRequest(true);
	if(!isset($_POST['token'])){
		echo json_encode( array( "error" => 1, "message" => "Access token is not set" ) );
	}else{
		echo json_encode( array( "error" => 1, "message" => "Invalid access token." ) );
	}
	exit;
}

try{
	if(empty($_POST['data'])){
		throw new Exception("Request body is empty", 1);
	}
	$json = json_decode($_POST['data'], true);
	if(isset($json['user']) && isset($json['user']["userId"])){
		createOrUpdateUser($json['user']);
	}

	if(isset($json['publications']) && count($json['publications']) > 0){
		createOrUpdatePublications($json['publications']);
	}
	$data = array( "error" => 0, "message" => "SUCCESS");
	logRequest();
}catch(Exception $ex){
	$data = array( "error" => 1, "message" => $ex->getMessage() );
	logRequest(true);
}

header('Content-Type: application/json');
echo json_encode( $data );
exit;