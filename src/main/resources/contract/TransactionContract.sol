// SPDX-License-Identifier: MIT
 pragma solidity >=0.4.16 <0.9.0;
 contract TransactionContract{
    struct Transaction {
        uint256 pid;
        string purchase_username;
        string owner;
        string transaction_date;
        uint256 price;
    }
    Transaction[] _transactions;

    constructor(){
    }
     /// @dev 记录交易
     function record_transaction(uint256 pid, string memory purchase_username, string memory owner, string memory transaction_date, uint256 price) external view returns (bool){
         
     }
    
 }