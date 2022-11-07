
Sulla nostra Home page http://{IP_ADDRESS}/

l'unica immagine cliccabile è quella di NSA che porta a http://{IP_ADDRESS}/?page=media&src=nsa 

possiamo provare a modificare il paramentro da passsare a scr

Modifichiamo quindi `nsa` inserendo uno script in Base64  NB. se non é base64 non funzionerá

online ci sono infiniti tool per la codifica di una stringa in base64, la stringa é semplicemente
```
<script>alert("Fuck");</script>
```

quindi la nostra Url diventa
`
http://{IP_ADDRESS}/?page=media&src=data:text/html;base64,PHNjcmlwdD5hbGVydCgid3RmIik7PC9zY3JpcHQ+ `

Documentazione
- [owasp - XSS](https://www.owasp.org/index.php/Cross-site_Scripting_(XSS))
- 
- [owasp - prevent XSS breaches](https://cheatsheetseries.owasp.org/cheatsheets/Cross_Site_Scripting_Prevention_Cheat_Sheet.html)
