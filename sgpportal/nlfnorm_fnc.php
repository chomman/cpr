<?php 


ini_set('display_errors', 'Off');
ini_set("log_errors" , 1);
ini_set("error_log" , "errors.log");


function logRequest($withBody = false){
	$req_dump = $withBody || isLocalhost() ? print_r($_POST, true) : '';
	$fp = fopen('request.log', 'a');
	fwrite($fp, date("Y-m-d H:i:s") . ' / '. getIpAddress(). ' / '. $req_dump).'\n ---- \n' ;
	fclose($fp);
}

function getRequestBody(){
	return $HTTP_RAW_POST_DATA;
}

function getIpAddress(){
	if (!empty($_SERVER['HTTP_CLIENT_IP'])) {
   		return $_SERVER['HTTP_CLIENT_IP'];
	} elseif (!empty($_SERVER['HTTP_X_FORWARDED_FOR'])) {
		return $_SERVER['HTTP_X_FORWARDED_FOR'];
	} else {
		return $_SERVER['REMOTE_ADDR'];
	}
}

function createNewUser($id, $login, $pass, $timestamp){
	global $dbTabulka;

	@$result = MySQL_Query("INSERT INTO ".$dbTabulka[100]." (id, login, pass, changed) VALUES (".toInt($id).", '".escape($login)."',  '".($pass)."', '$timestamp')");
	if(!$result){
		throw new Exception("Could not create user [$login]. | "  . getMysqlError());
	}
}

function updateUser($id, $login, $pass, $timestamp){
	global $dbTabulka;
	@$result = MySQL_Query("UPDATE ".$dbTabulka[100]." SET login= '".escape($login)."', pass='".($pass)."', changed = '$timestamp' WHERE id=".toInt($id)." LIMIT 1");
	if(!$result){
		throw new Exception("Could not update user [$login]. | "  . getMysqlError());
	}
}

function userExists($id){
	global $dbTabulka;
	@$result = MySQL_Query("SELECT id FROM ".$dbTabulka[100]." WHERE id=" . toInt($id)." LIMIT 1");
	if(!$result){
		throw new Exception("Could not retrieve user [$id]. | "  . getMysqlError());
	}
	return count($result) == 1;
}

function getPublication($id, $code){
	global $dbTabulka;

	@$result = MySQL_Query("SELECT * FROM ".$dbTabulka[101]." WHERE user_id = ".toInt($id)." AND service_code ='".escape($code)."' LIMIT 1");
	if(!$result){
		throw new Exception("Could not select service [$id][$code]. | "  . getMysqlError());
	}
	if(mysql_num_rows($result) > 0){
		return mysql_fetch_array($result, MYSQL_ASSOC);
	}
	return null;
}

function updatePublication($id, $code, $validity, $timestamp){
	global $dbTabulka;
	@$result = MySQL_Query("UPDATE ".$dbTabulka[101]." SET  service_code ='".escape($code)."', validity ='$validity', changed='$timestamp' WHERE user_id = ".toInt($id)." AND service_code ='".escape($code)."' LIMIT 1");
	if(!$result){
		throw new Exception("Could not update service [$id][$code]. | "  . getMysqlError());
	}
}

function createPublication($id, $code, $validity, $timestamp){
	global $dbTabulka;
	@$result = MySQL_Query("INSERT INTO ".$dbTabulka[101]." (user_id, service_code, validity, changed) VALUES ( ".toInt($id).", '".escape($code)."', '".$validity."', '".$timestamp."')");
	if(!$result){
		throw new Exception("Could not create service [$id][$code][$validity][$timestamp]. | "  . getMysqlError());
	}
}



function createOrUpdatePublications($publications){
	$userExists = null;
	foreach ($publications as $item) {
		if($userExists == null){
			$userExists = userExists($item['userId']);
			if(!$userExists){
				throw new Exception("User with [id=".$item['userId']."] was not found.", 1);
			}
		}
	    $pub = getPublication($item['userId'], $item['serviceCode']);
	    if($pub == null){
	    	createPublication($item['userId'], $item['serviceCode'], $item['validity'], $item['changed']);
	    }else{
	    	updatePublication($item['userId'], $item['serviceCode'], $item['validity'], $item['changed']);
	    }
	}
}

function createOrUpdateUser($user){
	if(userExists($user['userId'])){
		updateUser($user['userId'], $user['login'], $user['pass'], $user['changed']);
	}else{
		createNewUser($user['userId'], $user['login'], $user['pass'], $user['changed']);
	}
}

function getAllValidUsersPublications(){
	global $dbTabulka;
	$query = "SELECT u.pass as password, u.login as login, s.service_code as service FROM `".$dbTabulka[101]."` s ".
			 " join `".$dbTabulka[100]."`u on u.`id` = s.`user_id` ".
			 " where s.validity >= CURDATE()";
	@$result = MySQL_Query($query);
	if(!$result){
		throw new Exception("Could not retrieve pulication for htpasswds update. | "  . getMysqlError());
	}
	$data = array();
	while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
	   $data[] = $row;
	}
	return $data ;
}

function isTokenValid($token){
	return $token == "";
}

function getMysqlError(){
	global $spojeni;
	return mysql_errno($spojeni) . ": " . mysql_error($spojeni);
}

function escape($val){
	return mysql_real_escape_string( $val );
}

function toInt($val){
	return intval($val);
}