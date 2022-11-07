
Chiudiamo momentaneamente il Browser per spostarci sul Terminale ed eseguiamo il comando

` nmap -A {{IP_ADDRESS}}/80`

Otteniamo

```
[...]
| http-robots.txt: 2 disallowed entries
|_/whatever /.hidden
|_http-server-header: nginx/1.8.0
|_http-title: BornToSec - Web Section
4242/tcp open  ssh     OpenSSH 5.9p1 Debian 5ubuntu1.7 (Ubuntu Linux; protocol 2.0)
| ssh-hostkey:
|   1024 c1:03:76:40:29:e8:ab:f6:8a:9f:1c:71:6e:23:e0:58 (DSA)
|   2048 89:95:1a:c3:7c:1b:fc:3c:34:1d:76:d5:c9:fa:86:03 (RSA)
|_  256 09:86:1a:be:13:a5:a1:0c:7f:f7:55:50:ac:7a:c7:1a (ECDSA)
[...]
```

Notiamo la path `/whatever/`  al cui interno c'é l'interessante file `htpasswd`, che a sua volta contiene
`root:8621ffdbc5698829397d97767ac13db3 `

usando i tool online usati anche per il cooke ( hashes e md5online) decifriamo la password che risulta essere :

`dragon`

ora possiamo semplicamente fare il logIn e ottenere la Flag


Documentazione -> [OWASP](https://www.owasp.org/index.php/Review_Webserver_Metafiles_for_Information_Leakage_(OTG-INFO-003))
