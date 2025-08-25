// SPDX-License-Identifier: MIT
pragma solidity ^0.8.28;

import "@openzeppelin/contracts/token/ERC721/extensions/ERC721URIStorage.sol";
import "@openzeppelin/contracts/access/Ownable.sol";

contract Token42Art is ERC721URIStorage, Ownable {
  uint256 private _nextTokenId = 1;

  constructor() ERC721("Token42Art", "T42A") Ownable(_msgSender()) {}

  function mintNFT(address to, string calldata uri) external onlyOwner returns (uint256) {
    uint256 tokenID = _nextTokenId++;
    _mint(to, tokenID);
    _setTokenURI(tokenID, uri);
    return tokenID;
  }
}