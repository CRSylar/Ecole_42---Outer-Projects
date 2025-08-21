// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

import "@openzeppelin/contracts/token/ERC20/ERC20.sol";
import "@openzeppelin/contracts/access/Ownable.sol";

contract Token42 is ERC20, Ownable {
    constructor() ERC20("Token42", "TK42") Ownable(msg.sender) {
        // Mint 1 000 000 TK42 to deployer
        _mint(msg.sender, 1_000_000 * 10 ** decimals());
    }

    // Optional: owner can mint more
    function mint(address to, uint256 amount) external onlyOwner {
        _mint(to, amount);
    }
}