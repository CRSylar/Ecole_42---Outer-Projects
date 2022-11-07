sulla pagina `http://{{IP_ADDRESS}}/?page=e43ad1fdc54babe674da7c7b8f0127bde61de3fbe01def7d00f151c2fcca6d1c`
Usando i DevTools ( F12 )
notiamo un lungo commento che finisce con

```html
<!--
You must cumming from : "https://www.nsa.gov/" to go to the next step
-->
<!--
Let's use this browser : "ft_bornToSec". It will help you a lot.
-->
```

Capiamo che con una richiesta ben "costruita" otteniamo qualcosa
quindi con il comando
```
curl -H 'User-Agent: ft_bornToSec' -H 'Referer: https://www.nsa.gov/' 'http://{{IP_ADDRESS}}/?page=e43ad1fdc54babe674da7c7b8f0127bde61de3fbe01def7d00f151c2fcca6d1c' | grep flag

```

otteniamo la flag
