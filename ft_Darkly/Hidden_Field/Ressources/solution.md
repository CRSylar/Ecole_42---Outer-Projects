DATA_VALIDATION_HIDDEN_FIELD


Navigando sulla pagina http://{IP_ADDRESS}/?page=recover

Usando i DevTool di Chrome ( F12 ) ispezioniamo il form, dove troviamo un tag <input> in stato Hidden che
contiene il valore "webmaster@borntosec.com" 

```
<input type="hidden" name="mail" value="webmaster@borntosec.com" maxlength="15">
```


Documentazione qui -> [OWASP_HIDDEN_FIELDS](https://owasp.org/projects/#Hidden_fields)
