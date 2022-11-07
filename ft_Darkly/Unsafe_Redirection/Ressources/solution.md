
Usando i DevTools per ispezionare i "Tasti social" 

notiamo che quello di Facebook è -> 

` <a href="index.php?page=redirect&amp;site=test" class="icon fa-facebook"></a> `

Come consigliato su [OWASP_Redirections](https://cheatsheetseries.owasp.org/cheatsheets/Unvalidated_Redirects_and_Forwards_Cheat_Sheet.html)
inserire dei reindizzamenti "hard" senza nessun controllo non é sicuro e infatti basta sostituire

` http://{{IP_ADDRESS}}/index.php?page=redirect&site=facebook`

con

` http://{{IP_ADDRESS}}/index.php?page=redirect&site=QUALSIASI_COSA`

per avere la flag
