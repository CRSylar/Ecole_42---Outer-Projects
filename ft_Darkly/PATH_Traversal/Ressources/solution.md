
Documentandomi su [OWASP_PATH_Traversal]https://owasp.org/www-community/attacks/Path_Traversal ho provato ad eseguire questo tipo di "Attacco"

per leggere nel file in `/etc/passwd` 

semplicemente risalendo l'albero con `../` si arriva a ` http://{{IP_ADDRESS}}/?page=../../../../../../../etc/passwd`

dove otteniamo la flag
