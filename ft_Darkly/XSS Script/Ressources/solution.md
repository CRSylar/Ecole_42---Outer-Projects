
Navighiamo su http://{IP_ADDRESS}/index.php?page=feedback

e inseriamo la parola `script` nel campo `name`

tecnimamente non sarebbe neanche un vero attacco XSS in quanto non c'è nessuno script che viene eseguito.

a scopo "didattico" comunque si puo eseguire un XSS vero e proprio chiudendo il tag e inserendo il tag script


` </td><script>alert("Ciao")</script> `


info -> [XSS](https://www.owasp.org/index.php/Cross-site_Scripting_(XSS))

[OWASP_XSS_PREVENTION](https://cheatsheetseries.owasp.org/cheatsheets/Cross_Site_Scripting_Prevention_Cheat_Sheet.html)
