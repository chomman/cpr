package sk.peterjurkovic.cpr.mail;

import org.apache.commons.lang.StringUtils;

import sk.peterjurkovic.cpr.entities.User;

public class MailMessageCreator {
	
	
	
	
	public static String newUserCreatedMessage(User whoCreated, User createdUser, String pass){
		String author = "";
		if(StringUtils.isNotBlank(whoCreated.getFirstName())){
			author = whoCreated.getFirstName();
			if(StringUtils.isNotBlank(whoCreated.getLastName())){
				author += " "+whoCreated.getLastName();
			}
		}else{
			author = whoCreated.getEmail();
		}
		
		StringBuffer message = new StringBuffer("<p>Dobrý den,<br><br>");
		message.append("uživatel <b>"+author+"</b> Vám vytvořil uživatelský účet v informačním systému CPR.<br><br>");
		message.append("Adresa informačního systému: <a href=\"http://www.nlfnorm.cz\">www.nlfnorm.cz</a><br>");
		message.append("Adresa administrace systému: <a href=\"http://www.nlfnorm.cz/admin/\">www.nlfnorm.cz/admin/</a><br>");
		message.append("Vaše přihlašovací jméno: "+createdUser.getEmail()+" <br>");
		message.append("Vaše přihlašovací heslo: "+pass+" <i>(po přihlášení si jej můžete změnit)</i><br> <br>");
		message.append("Zpráva byla automaticky odeslána z informačního systému CPR</p>");
		return message.toString();
	}
}
