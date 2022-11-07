

Come dice il Nome stavolta forziamo l'accesso con un "classico" attacco a forza bruta sulla pagina di login

`http://{IP_ADDRESS}/?page=signin`

nella cartella é inserito uno script.sh usato per eseguire l'attacco, ovviamente contiamo sul fatto che la password scelta
sará "debole" altrimenti é un tipo di attacco che potrebbe richiedere tempi di calcolo spropositati

in rete si trovano diversi file contenti password "leaked" e/o compromesse, file con le password piu usate
oltre che ovviemente molti tools per l'attacco in se per se 
( alcuni esempi qui [bruteforce](https://www.forcepoint.com/cyber-edu/brute-force-attack))

nel mio caso ho usato come appoggio un file con le 1000 password deboli piu usate e ho trovato la password che é
`shadow`

ovviamente basta fare il login per avere la flag
