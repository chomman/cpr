begin;
INSERT INTO email_template (id, changed, code, created, enabled, body, name, subject, variables, variable_description, id_user_changed_by, id_user_created_by, bcc_forwarding) VALUES (10, '2014-06-02 18:31:34.245', 'USER_CREATE', '2014-06-02 18:33:01.256', true, 'Dobr&yacute; den,<br /><br />zapomněli jste Va&scaron;e přihla&scaron;ovac&iacute; heslo do syst&eacute;mu nlfnorm.cz?<br />Klikněte na n&iacute;že uveden&yacute; odkaz, kde budete vyzv&aacute;ni k vytvořen&iacute; hesla nov&eacute;ho. Platnost odkazu je ${platnostOdkazu} hodin.<br /><br /><strong><a href="${odkaz}">${odkaz}</a></strong><br /><br />S př&aacute;n&iacute;m hezk&eacute;ho dne<br />Port&aacute;l nlfnorm.cz', 'E-mail je odeslán po vytvoření nového uživatele a případě pokud je povolena možnost "Odeslat informační email"', 'Vytvoření nového uživatele s přístupem do administrace', 'odkaz, platnostOdkazu', '<span id="result_box" class="short_text" lang="cs"><span class="hps">Proměnn&eacute;, kter&eacute;</span> <span class="hps">je možn&eacute; v</span> <span class="hps">těle</span> <span class="hps">e</span><span class="atn">-</span>mailu <span class="hps">použ&iacute;t:<br /></span></span><br />
<table style="width: 803px;" width="803">
<tbody>
<tr>
<td><strong>${<span id="result_box" lang="cs">odkaz</span>}</strong></td>
<td><span id="result_box" lang="cs"><span class="hps">Odkaz</span>, <span class="hps">jehož prostřednictv&iacute;m si</span> <span class="hps">m&aacute; možnost</span> <span class="hps">uživatel</span> <span class="hps">resetovat</span> <span class="hps">heslo</span></span></td>
</tr>
<tr>
<td><strong>${<span id="result_box" lang="cs">platnostOdkazu</span>}</strong></td>
<td><span id="result_box" class="short_text" lang="cs"><span class="hps">Platnost</span> <span class="hps">odkazu v</span> <span class="hps">hodin&aacute;ch</span></span></td>
</tr>
</tbody>
</table>
<span id="result_box" class="short_text" lang="cs"></span>', 1, 1, '');

end;