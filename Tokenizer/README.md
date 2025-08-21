# Tokenizer

Tokenizer is a simple ERC-20/BEP-20 token project deployed on the BNB Testnet. The project demonstrates the deployment of a basic token contract using Hardhat and Solidity, leveraging OpenZeppelin for secure and reliable contract development.

## Tech Stack
- **Solidity**: Smart contract language
- **Hardhat**: Development framework for Ethereum-compatible blockchains
- **OpenZeppelin**: Battle-tested library for secure smart contracts
- **BNB Testnet**: Deployment network

## Contract Details
- **Contract Address**: `0x2909F24FE716F87364A24E8a48A54a89204A288E`
- **Explorer Link**: [View on BscScan Testnet](https://testnet.bscscan.com/address/0x2909F24FE716F87364A24E8a48A54a89204A288E)

## How to Run
1. Clone the repository
2. Install dependencies:
   ```bash
   npm install
   ```
3. Configure your `.env` file with your testnet credentials
4. Compile contracts:
   ```bash
   npx hardhat compile
   ```
5. Deploy to BNB Testnet:
   ```bash
   npx hardhat run code/scripts/deploy.js --network bscTestnet
   ```

## Notes
- The contract uses OpenZeppelin's implementation for reliability and security.
- For testing, see the `code/test/Token42.test.js` file.
- Refer to the [subject](./subject.pdf) for more information about the project requests
## License
MIT