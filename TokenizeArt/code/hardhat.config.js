require("@nomicfoundation/hardhat-toolbox");
require("dotenv").config();

const PRIVATE_KEY = process.env.PRIVATE_KEY || "0x0000000000000000000000000000000000000000000000000000000000000000";
const ETHERSCAN_API_KEY = process.env.ETHERSCAN_API_KEY || "YourEtherscanAPIKey";

module.exports = {
  solidity: "0.8.28",
  networks: {
    bscTestnet: {
      url: "https://data-seed-prebsc-1-s1.binance.org:8545",
      chainId: 97,
      accounts: [PRIVATE_KEY],
    },
  },
  etherscan: {
    apiKey: ETHERSCAN_API_KEY,
  },
  sourcify: {
    // Disabled by default
    // Doesn't need an API key
    enabled: true
  }
};