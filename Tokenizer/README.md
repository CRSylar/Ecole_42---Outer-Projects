# Tokenizer

Tokenizer is a simple ERC-20/BEP-20 token project deployed on the BNB Testnet. The project demonstrates the deployment of a basic token contract using Hardhat and Solidity, leveraging OpenZeppelin for secure and reliable contract development.

To add some "function" to the token i've created a simple "Betting system".
the Owner can create a bet (at the moment only one with a boolean response).
Users can than use the token to place the bet ( on true/false outcome ).
The Owner will than resolve the bet by marking the outcome.

The Winner users will receive a part of the total pot. actually dividing in equal parts

## Tech Stack
- **Solidity**: Smart contract language
- **Hardhat**: Development framework for Ethereum-compatible blockchains
- **OpenZeppelin**: Battle-tested library for secure smart contracts
- **BNB Testnet**: Deployment network

## Contract Details
- **Contract Address**: `0x5F7B8403f3016e7A7d66423BE66724e18E56bA49`
- **Explorer Link**: [View on BscScan Testnet](https://testnet.bscscan.com/address/0x5F7B8403f3016e7A7d66423BE66724e18E56bA49#code)
- - **Sourcify verification Link**: [View on Sourcify](https://repo.sourcify.dev/contracts/full_match/97/0x5F7B8403f3016e7A7d66423BE66724e18E56bA49/)

## BONUS
- **Multisig Contract Address**: `0x227a6d9be3dBA398c7ED606D07ffBaCA78e2a5Fa`
- **Explorer Link**: [View on BscScan Testnet](https://testnet.bscscan.com/address/0x227a6d9be3dBA398c7ED606D07ffBaCA78e2a5Fa#code)
- **Sourcify verification Link**: [View on Sourcify](https://repo.sourcify.dev/contracts/full_match/97/0x227a6d9be3dBA398c7ED606D07ffBaCA78e2a5Fa/)

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
6. Verify the deployment:
   ```bash
   npx hardhat verify --network bscTestnet <Insert_Here_the_given_address>
   ```

## BONUS - How to Run

For the Bonus part is asked to add a MultiSig system, this add security as the wallet ownership will be passed to this contract and than for minting and all the owner related function ( like the betting creation ) will be mandatory at least 2 signers to approve it.

1. After the Token deployment run the script
   ```bash
   ./scripts/setTokenAddress.sh
   ```
   this will add the Token Address to the .env file so it can be read by the bonus deployment script

2. Deploy to BNB Testnet:
   ```bash
   npx hardhat run code/scripts/deployMultisig.js --network bscTestnet
   ```
2.5 Prepare for deployment verification:
   since the constructor of the MultiSig contract want an array of addresses
   you'll need to pass the address of (at least) 2 wallet that will be added as `signers`, if you are unsure on how to do it create a file with this content:
   ```js
   // create a .js file
   module.exports = [
      [
         <address_1>,
         <address_2>
      ]
   ]
   ```
   than move to step 3 using the --constructor-flag in the command
3. Verify the deployment:
   ```bash
   npx hardhat verify --network bscTestnet <Insert_Here_the_given_address> --constructor-flag <path_to_the_js_file>
   ```

## Notes
- The contract uses OpenZeppelin's implementation for reliability and security.
- For testing, see the `code/test/Token42.test.js` file.
- Refer to the [subject](./subject.pdf) for more information about the project requests
## License
MIT