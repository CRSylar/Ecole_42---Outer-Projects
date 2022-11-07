
Lasciamo il Browser e apriamo il terminale, mappiamo il sito per trovare eventuali file/cartelle nascoste alla navigazione

`nmap -A {{IP_ADDRESS}}/80 `

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

La parte che ci interessa é quel percorso /.hidden che possiamo raggiungere dalla home page
`http://{{IP_ADDRESS}}/.hidden `

notiamo che al suo interno ci sono una serie di sub-directory e di file README,
scarichiamo tutto 

` wget -r -nc -np --reject "index.html" -e robots=off http://{{IP_ADDRESS}}/.hidden/ `

e facendo una semplice ricerca in tutte per trovare una eventuale flag con il comando 

`find {{IP_ADDRESS}}/.hidden -name "README" | xargs grep -e '[0-9]' `

troviamo la flag nel README al percorso 
` {{IP_ADDRESS}}/.hidden/whtccjokayshttvxycsvykxcfm/igeemtxnvexvxezqwntmzjltkt/lmpanswobhwcozdqixbowvbrhw/README `
