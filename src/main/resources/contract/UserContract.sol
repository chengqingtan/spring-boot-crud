// SPDX-License-Identifier: MIT
 pragma solidity >=0.4.16 <0.9.0;
 contract UserContract{
    //管理员地址
    address private _manager;
    //用户名映射至用户余额
    mapping(address => uint256) private _balance;
    
    constructor(){
        _manager = msg.sender;
    }

     /// @dev 发行货币,不存在这个用户时返回false
     function add_balance(address account, uint256 amount) external view returns (bool){

     }
     /// @dev 转账,金额不足或账户不存在时返回false;否则转账成功返回true
     function transfer(address from,address to,uint256 amount) external view returns (bool){

     }
     /// @dev 查看当前账户地址余额,余额，没有用户时返回 -1
     function query_balance(address account) external view returns (uint256){

     }
    
 }