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
    Transaction[] private _transactions;

    constructor(){
    }
     /// @dev 记录交易
     function record_transaction(uint256 pid, string memory purchase_username, string memory owner, string memory transaction_date, uint256 price) external returns (bool){
        Transaction memory newTransaction = Transaction(pid, purchase_username, owner, transaction_date, price);
        _transactions.push(newTransaction);
        return true;
     }
     /// @dev 查询所有交易
     function query_all_transaction()external view returns(Transaction[] memory){
         return _transactions;
     }
    /// @dev 根据pid查询交易
     function query_pid_transaction(uint256 pid)external view returns(Transaction memory){
         uint n = _transactions.length;
         Transaction memory transaction;
         for(uint i = 0; i < n; i++){
             if(pid == _transactions[i].pid){
                 transaction = _transactions[i];
             }
         }
         return transaction;
     }

     /// @dev 根据owner查询交易
     function query_owner_transaction(string memory owner)external returns(Transaction[] memory){
         uint n = _transactions.length;
         uint count = 0;
         for(uint i = 0; i < n; i++){
             string memory temp = _transactions[i].owner;
             if(stringCompare(owner, temp)){
                 count += 1;
             }
         }
        Transaction[] memory transaction = new Transaction[](count);
        uint count2 = 0;
        for(uint i = 0; i < n; i++){
             string memory temp = _transactions[i].owner;
             if(stringCompare(owner, temp)){
                 transaction[count2] = _transactions[i];
                 count2 += 1;
             }
         }
         return transaction;
     }
     
     function stringCompare(string memory a, string memory b)internal returns(bool){
         if(bytes(a).length != bytes(b).length){
             return false;
         }else{
            for(uint i = 0; i < bytes(a).length; i ++) {
            if(bytes(a)[i] != bytes(b)[i]) {
                return false;
            }
        }
        return true;
        }
     }
    
}