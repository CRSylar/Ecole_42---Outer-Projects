const { expect } = require("chai");

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