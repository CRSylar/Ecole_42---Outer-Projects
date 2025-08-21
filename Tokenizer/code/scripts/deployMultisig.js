const {ethers} = require("hardhat");

async function main() {

  if (!process.env.TOKEN_ADDRESS || process.env.TOKEN_ADDRESS === "") {
    console.error("TOKEN_ADDRESS is not set in the .env file");
    console.info("please run `scripts/setTokenAddress.sh` script first"); 
    return;
  }

  console.log("Deploying MultiSigWallet...");

  const [deployer] = await ethers.getSigners();
  const signers = [deployer.address, deployer.address];

  const MultiSigWallet = await ethers.getContractFactory("MultiSigWallet");
  const wallet = await MultiSigWallet.deploy(signers);
  await wallet.waitForDeployment();

  console.log("MultiSigWallet deployed to:", await wallet.getAddress());

  // attach existing Token42
  const t42 = await ethers.getContractFactory("Token42");
  const token = t42.attach(process.env.TOKEN_ADDRESS);
  await token.transferOwnership(await wallet.getAddress());
  console.log("Ownership of Token42 transferred to MultiSigWallet:", await wallet.getAddress());
}

main().catch((error) => {
  console.error(error);
  process.exitCode = 1;
});