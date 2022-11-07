
# Cookie Vulnerability

Aprire i DevTools di Chrome ( F12 ), nella sezione Application -> Cookie

abbiamo un solo cookie fornito dal sito chiamato `I_am_admin` con il valore
  `68934a3e9455fa72420237eb05902327`


tramite il sito [hash_identifier](https://hashes.com/en/tools/hash_identifier)
otteniamo che è molto probabilmente criptato in `md5`.

usando quindi il sito [md5online](http://www.md5online.it/index.lm) possiamo decriptare il cookie, che contiene
  `false`,

sempre sullo stesso sito possiamo inserire noi una stringa da criptare, inseriamo `true` in md5 e modifichiamo il cookie.
appena fatto il sito ci dara un alert contenente la flag.


documentazione in merito -> [Session_hijcacking](https://owasp.org/www-community/attacks/Session_hijacking_attack)
