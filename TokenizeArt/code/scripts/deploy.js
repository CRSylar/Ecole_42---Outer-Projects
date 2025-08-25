const { ethers } = require("hardhat");

async function main() {
  const [deployer] = await ethers.getSigners();
  console.log("Deploying contracts with account:", deployer.address);

  const t42art = await ethers.getContractFactory("Token42Art");
  const nft = await t42art.deploy();
  await nft.waitForDeployment();

  console.log("Token42Art deployed to: ", await nft.getAddress());
}

main().catch((error) => {
  console.error(error);
  process.exitCode = 1;
});