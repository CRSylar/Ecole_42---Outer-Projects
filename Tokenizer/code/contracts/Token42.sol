// SPDX-License-Identifier: MIT
pragma solidity ^0.8.28;

import "@openzeppelin/contracts/token/ERC20/ERC20.sol";
import "@openzeppelin/contracts/access/Ownable.sol";

contract Token42 is ERC20, Ownable {

    uint256 public constant BET_COST = 42 * 10 ** 18;

    struct Bet {
        string question;
        bool outcome;
        bool resolved;
        uint256 totalPot;
        uint256 trueCount;
        uint256 falseCount;
        mapping (address => bool) votedTrue;
        mapping (address => bool) votedFalse;
    }

    uint256 public nextBetId;
    mapping (uint256 => Bet) public bets;

    constructor() ERC20("Token42", "TK42") Ownable(_msgSender()) {
        // Mint 1 000 000 TK42 to deployer
        _mint(_msgSender(), 1_000_000 * 10 ** decimals());
    }

    // Optional: owner can mint more
    function mint(address to, uint256 amount) external onlyOwner {
        _mint(to, amount);
    }

    // ----- Betting ------
    function openBet(string calldata question) external onlyOwner returns (uint256 id) {
        id = nextBetId++;
        bets[id].question = question;
    }

    function joinBet(uint256 betID, bool choice) external {
        Bet storage bet = bets[betID];
        require(bytes(bet.question).length > 0, "Bet does not exist");
        require(!bet.resolved, "Bet already resolved");
        require(balanceOf(_msgSender()) >= BET_COST, "Insufficient balance");
        require(!bet.votedTrue[_msgSender()] && !bet.votedFalse[_msgSender()], "Already voted in this Bet");

        _transfer(_msgSender(), address(this), BET_COST);
        if (choice) {
            bet.votedTrue[_msgSender()] = true;
            bet.trueCount += 1;
        } else {
            bet.votedFalse[_msgSender()] = true;
            bet.falseCount += 1;
        }

        bet.totalPot += BET_COST;
    }

    function resolveBet(uint256 betID, bool outcome) external onlyOwner {
        Bet storage bet = bets[betID];
        require(bytes(bet.question).length > 0, "Bet does not exist");
        require(!bet.resolved, "Bet already resolved");

        bet.outcome = outcome;
        bet.resolved = true;
    }

    function claimWinnings(uint256 betID) external {
        Bet storage bet = bets[betID];
        require(bet.resolved, "Bet not resolved yet");
        require(bet.votedTrue[_msgSender()] || bet.votedFalse[_msgSender()], "No valid vote in the selected bet");

        bool won = (bet.outcome && bet.votedTrue[_msgSender()]) || (!bet.outcome && bet.votedFalse[_msgSender()]);
        require(won, "Sorry, No winnings to claim");

        uint256 winners = bet.outcome ? bet.trueCount : bet.falseCount;
        uint256 reward = bet.totalPot / winners;

        _transfer(address(this), _msgSender(), reward);

        // avoid re-claims
        if (bet.outcome) {
            delete bet.votedTrue[_msgSender()];
        } else {
            delete bet.votedFalse[_msgSender()];
        }
    }
}