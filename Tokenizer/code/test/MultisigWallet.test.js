const { expect } = require("chai");

describe("MultiSigWallet – 2-of-3", function () {
  let token, wallet;
  let deployer, s1, s2, s3;

  beforeEach(async () => {
    [deployer, s1, s2, s3] = await ethers.getSigners();
    const signers = [deployer.address, s1.address, s2.address];

    // 1. Token
    const Token42 = await ethers.getContractFactory("Token42");
    token = await Token42.deploy();
    await token.waitForDeployment();

    // 2. Wallet
    const MultiSigWallet = await ethers.getContractFactory("MultiSigWallet");
    wallet = await MultiSigWallet.deploy(signers);
    await wallet.waitForDeployment();

    // 3. Transfer ownership
    await token.transferOwnership(await wallet.getAddress());
  });

  it("requires 2 signatures to mint", async () => {
    const calldata = token.interface.encodeFunctionData("mint", [
      s3.address,
      ethers.parseUnits("1000", 18),
    ]);

    // 1st approval
    await wallet.connect(s1).execute(token.target, calldata);

    // 2nd approval → tx executes
    await expect(
      wallet.connect(s2).execute(token.target, calldata)
    ).to.changeTokenBalance(token, s3, ethers.parseUnits("1000", 18));
  });

  it("reverts with only 1 approval", async () => {
    const calldata = token.interface.encodeFunctionData("mint", [
      s3.address,
      ethers.parseUnits("1000", 18),
    ]);
    await wallet.connect(s1).execute(token.target, calldata);

    // Trying to mint again without 2nd signer fails
    await expect(
      wallet.connect(s1).execute(token.target, calldata)
    ).to.be.revertedWith("Already approved");
  });
});