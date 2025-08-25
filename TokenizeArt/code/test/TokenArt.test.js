const { expect } = require("chai");
const { ethers } = require("hardhat");

describe("TokenArt", function() {
  let nft,owner;

  beforeEach(async () => {
    [owner] = await ethers.getSigners();
    const t42Art = await ethers.getContractFactory("Token42Art");
    nft = await t42Art.deploy();
    await nft.waitForDeployment();
  })

  it("mints an NFT and assign ownership", async () => {
    const uri = "ipfs://bafybeifabcbmdedjl6b4eeqx6ca6ecoooux6dghqrh5k5j2bz3qihhwbeq/croco.json";
    const tx = await nft.mintNFT(owner.address, uri);
    await tx.wait();

    const ownerOf = await nft.ownerOf(1);
    expect(ownerOf).to.equal(owner.address);
  })
})