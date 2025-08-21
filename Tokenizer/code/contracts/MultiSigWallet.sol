// SPDX-License-Identifier: MIT
pragma solidity ^0.8.28;

import "@openzeppelin/contracts/token/ERC20/ERC20.sol";
import "@openzeppelin/contracts/access/Ownable.sol";

contract MultiSigWallet {
    address[] public signers;
    uint256 public constant REQUIRED = 2;
    mapping(bytes32 => mapping(address => bool)) public approvals;

    constructor(address[] memory _signers) {
        signers = _signers;
    }

    modifier onlySigner() {
        bool ok;
        for (uint i; i < 3; ++i) {
            if (msg.sender == signers[i]) {
                ok = true;
            }
        }
        require(ok, "Not a signer");
        _;
    }

    function execute(address target, bytes calldata data) external onlySigner {
        bytes32 txHash = keccak256(abi.encodePacked(target, data));
        require(!approvals[txHash][msg.sender], "Already approved");

        approvals[txHash][msg.sender] = true;

        uint256 count = 0;
        for (uint i; i < 3; ++i) {
            if (approvals[txHash][signers[i]]) {
                count++;
            }
        }

        if (count >= REQUIRED) {
            (bool ok, ) = target.call(data);
            require(ok, "Exec failed");
        }
    }
}
