const { ethers } = require("hardhat");

async function main() {
  const [deployer] = await ethers.getSigners();
  console.log("Deploying with account:", deployer.address);

  const Token42 = await ethers.getContractFactory("Token42");
  const token = await Token42.deploy();
  await token.waitForDeployment();

  const address = await token.getAddress();
  console.log("Token42 deployed to:", address);
}

main().catch((error) => {
  console.error(error);
  process.exitCode = 1;
});