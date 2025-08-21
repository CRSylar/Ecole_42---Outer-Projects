const { expect } = require("chai");
const { ethers } = require("hardhat");

describe("Token42", function () {
  it("Should mint 1 000 000 TK42 to owner", async function () {
    const [owner] = await ethers.getSigners();
    const Token42 = await ethers.getContractFactory("Token42");
    const token = await Token42.deploy();
    await token.waitForDeployment();

    const balance = await token.balanceOf(owner.address);
    expect(balance).to.equal(1_000_000n * 10n ** 18n);
  });
});

describe("Token42 betting", function () {
  let token, owner, alice;

  beforeEach(async () => {
    [owner, alice, bob] = await ethers.getSigners();
    const Token = await ethers.getContractFactory("Token42");
    token = await Token.deploy();
    await token.waitForDeployment();

    await token.transfer(alice.address, ethers.parseUnits("50", 18));
    await token.transfer(bob.address, ethers.parseUnits("50", 18));
  });

  it("Owner opens a bet, Alice joins and claims", async () => {
    await token.openBet("Will it rain in Rome?");
    await token.connect(alice).joinBet(0, true);
    await token.connect(bob).joinBet(0, false);
    await token.resolveBet(0, true); // owner calls
    const aliceBalBefore = await token.balanceOf(alice.address);
    const bobBalBefore = await token.balanceOf(bob.address);
    const bobBalAfter = await token.balanceOf(bob.address);
    await token.connect(alice).claimWinnings(0);
    const aliceBalAfter = await token.balanceOf(alice.address);
    expect(aliceBalAfter).to.be.gt(aliceBalBefore);
    expect(bobBalAfter).to.be.equal(bobBalBefore);
  });
});