# Token42Art – 42 Spirit Animals NFT

### Overview
A BEP-721 NFT collection of four spirit animals (Crocodile, Fox, Owl, Whale).  
Each artwork hides the number **42** and is stored immutably on IPFS.

---

### Deployment Info
| Item | Value |
|---|---|
| Network | **BNB Smart Chain Test-net** |
| Contract address | `0x19c8C47a7AAE31D8Da414384B32E5D809c8Bc7f3` |
| IPFS images & metadata | `ipfs://bafybeickfs2so6caxzrikeasrvszewn4gbmufvwuki6xvges3quopz6ptu/croco.json`, `ipfs://bafybeickfs2so6caxzrikeasrvszewn4gbmufvwuki6xvges3quopz6ptu/whale.json`,`ipfs://bafybeickfs2so6caxzrikeasrvszewn4gbmufvwuki6xvges3quopz6ptu/owl.json`,`ipfs://bafybeickfs2so6caxzrikeasrvszewn4gbmufvwuki6xvges3quopz6ptu/foxy.json` |
| Supply | 4 unique 1/1 pieces |

---

### Usage – Quick Start
1. Connect wallet to BSC Test-net.
2. Call `ownerOf(tokenId)` to verify owner.
3. Call `tokenURI(tokenId)` to fetch IPFS metadata.
4. View on **OpenSea test-net** or any BSC explorer.

---

### Choices Explained
- **BEP-721** – standard ERC-721 fork for BNB compatibility.  
- **IPFS** – censorship-resistant storage (bonus requirement).  
- **Ownable mint** – only owner can mint (no front-end yet).  
- **Test-net only** – zero real money, per subject rule.

---

### Directory Layout (per subject)
```
tokenizeart/
├── README.md
├── code/
│   ├── contracts/Token42Art.sol
│   ├── scripts/deploy.js
│   └── test/Token42Art.test.js
├── documentation/
│   ├── whitepaper.md
├── mint/
│   └── metadata/
│   │   ├── croco.json
│   │   ├── foxy.json
│   │   ├── owl.json
│   │   ├── whale.json
│   └── run.js
└── deployment/
│   ├── whitepaper.md

```

---

### Verify
```bash
npx hardhat test
npx hardhat run code/scripts/deploy.js --network bscTestnet
npx hardhat verify --network bscTestnet <YOUR_DEPLOYED_ADDRESS>
```

© 2025 cromalde – École 42 Roma

