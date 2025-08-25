const {ethers} = require("hardhat");

async function main() {
  const [deployer] = await ethers.getSigners();
  const t42Art = await ethers.getContractFactory("Token42Art");
  const nft = t42Art.attach(process.env.NFT_CONTRACT_ADDRESS);

  const uris = await JSON.parse(process.env.NFT_URIS);
  for (let i = 0; i < uris.length; i++) {
    const tx = await nft.mintNFT(deployer.address, uris[i]);
    await tx.wait();
    console.log(`Minted NFT ${i + 1} with URI: ${uris[i]}`);
  }
}

main().catch((error) => {
  console.error(error);
  process.exitCode = 1;
});