begin;
INSERT INTO email_template (id, changed, code, created, enabled, body, name, subject, variables, variable_description, id_user_changed_by, id_user_created_by, bcc_forwarding) VALUES (10, '2014-06-02 18:42:26.64', 'USER_CREATE', '2014-06-02 18:33:01.256', true, '<span id="result_box" lang="cs"><span class="hps">Dobr&yacute; den,<br /><br />pr&aacute;vě V&aacute;m</span> <span class="hps">byl vytvořen</span> <span class="hps">&uacute;čet</span> <span class="hps">na port&aacute;l</span> <a href="http://www.nlfnorm.cz" target="_blank">www.nlfnorm.cz</a>, <br /><br /><span class="hps">Va&scaron;e přihla&scaron;ovac&iacute;</span> <span class="hps">jm&eacute;no</span><span>:</span> <strong>${login}</strong><br /><span class="hps">Va&scaron;e přihla&scaron;ovac&iacute;</span> <span class="hps">heslo</span>: <strong>${heslo}</strong><br /><br />S př&aacute;n&iacute;m hezk&eacute;ho dne<br />Port&aacute;l nlfnorm.cz</span>', 'E-mail odeslán po vytvoření nového uživatele, pokud je povolena možnost: Odeslat informační email', 'Vytvoření účtu na portálu nlfnorm.cz', 'login, heslo', '<span id="result_box" class="short_text" lang="cs"><span class="hps">Proměnn&eacute;, kter&eacute;</span> <span class="hps">je možn&eacute; v</span> <span class="hps">těle</span> <span class="hps">e</span><span class="atn">-</span>mailu <span class="hps">použ&iacute;t:<br /></span></span><br />
<table style="width: 803px;" width="803">
<tbody>
<tr>
<td><strong>${<span id="result_box" lang="cs">login</span>}</strong></td>
<td><span id="result_box" lang="cs"><span class="hps">Přihla&scaron;ovac&iacute; jm&eacute;no do syst&eacute;mu</span></span></td>
</tr>
<tr>
<td><strong>${<span id="result_box" lang="cs">heslo</span>}</strong></td>
<td><span id="result_box" lang="cs"><span class="hps">Přihla&scaron;ovac&iacute; heslo do syst&eacute;mu</span></span></td>
</tr>
</tbody>
</table>
<span id="result_box" class="short_text" lang="cs"></span>', 1, 1, '');

end;