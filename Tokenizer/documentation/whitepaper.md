# Token42 White-paper  
**From Classroom Token to Decentralised Betting Broker**  
*Version 1.0 – August 2025*  
*CRSylar – École 42 Roma*

---

## 1. Vision
Token42 began as an educational BEP-20 token.  
We now extend it into a **decentralised betting broker** that lets **anyone** create, join and resolve prediction markets **without a central operator**.

---

## 2. Core Architecture

| Layer | Purpose |
|---|---|
| Token42 (ERC-20) | Unit of account, liquidity, rewards |
| Betting Engine | On-chain markets with transparent resolution |
| Multi-Sig Wallet | Security layer (2-of-3) for critical actions |
| Future Front-End | Web3 DApp (MetaMask-only, no server) |

---

## 3. Betting Engine (V1)

- **Markets** are opened by the **creator** (initially the owner).  
- **Bet cost** is fixed in TK42 (100 tokens).  
- **Outcome** is set by the creator → winners split the pot.  
- **Claim** is trust-less, executed by the winners themselves.

---

## 4. Roadmap (Next Iterations)

| Phase | Feature | Technical Change |
|---|---|---|
| **V2** | **Oracle Voting** | Outcome decided by >50 % of winners + >10 % of losers (on-chain tally). |
| **V3** | **Liquidity Pool** | Pair TK42/BNB on PancakeSwap; 0.3 % fee funds future markets. |
| **V4** | **Permissionless Creation** | Anyone deposits 10 000 TK42 collateral to open a market. |
| **V5** | **Governance DAO** | TK42 stakers vote on parameters, fee splits, oracle upgrades. |

---

## 5. Risk & Mitigation

| Risk | Mitigation |
|---|---|
| Oracle manipulation | Majority-vote + loser stake. |
| Creator rug-pull | Collateral locked until resolution. |
| Front-running | Commit-reveal scheme for votes. |

---

## 6. Summary
Token42 is no longer *just* a token.  
With incremental, on-chain features it can evolve into a fully **decentralised, community-governed betting protocol**—all while remaining a lightweight, auditable smart-contract suite.

---

**© 2025 CRSylar – École 42 Roma**