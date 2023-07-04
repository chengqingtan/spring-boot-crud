// SPDX-License-Identifier: MIT
 pragma solidity >=0.4.16 <0.9.0;
 contract UserContract{
    //管理员地址
    address  _manager;
    //用户名映射至用户余额
    mapping(address => uint256) private _balance;
    address[] private balanceKeys;
    constructor(){
        _manager = msg.sender;
    }

     /// @dev 发行货币,不存在这个用户时返回false
     function add_balance(address account, uint256 amount) external returns (bool){
         if(account != address(0)){
             uint n = balanceKeys.length;
             for(uint i = 0; i < n; i++){
                 if(account == balanceKeys[i]) {//如果找到对应的用户
                     
                     _balance[account] += amount;
                     return true;
                 }
             }
            return false;//用户不存在
         }else{
             return false;//地址为空
         }
        
     }
     /// @dev 转账,金额不足或账户不存在时返回false;否则转账成功返回true
     function transfer(address from,address to,uint256 amount) external returns (bool){
         if(_balance[from] < amount){
             return false;
         }else{
             uint n = balanceKeys.length;
             for(uint i = 0; i < n; i++){
                 if(to == balanceKeys[i]) {//如果找到对应的用户
                     _balance[from] -= amount;
                     _balance[to] += amount;
                     return true;
                 }
             }
         }
         return false;

     }
     /// @dev 查看当前账户地址余额,余额，没有用户时返回 -1
     function query_balance(address account) external view returns (uint256){
          return _balance[account];
     }
     /// @dev 开户
     function add_user(address account) external returns (bool){
         balanceKeys.push(account);
         _balance[account] = 0;
         return true;
     }
 }