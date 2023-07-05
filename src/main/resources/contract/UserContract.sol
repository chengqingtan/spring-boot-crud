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

     function add_balance(address account, uint256 amount) external {
         if(account != address(0)){
             uint n = balanceKeys.length;
             for(uint i = 0; i < n; i++){
                 if(account == balanceKeys[i]) {//如果找到对应的用户
                     _balance[account] += amount;
                 }
             }
         }
     }
     /// @dev 转账
     function transfer(address from,address to,uint256 amount) external{
        uint n = balanceKeys.length;
        for(uint i = 0; i < n; i++){
            if(to == balanceKeys[i]) {//如果找到对应的用户
                _balance[from] -= amount;
                _balance[to] += amount;
            }
        }

     }
     /// @dev 查看当前账户地址余额,余额，没有用户时返回 999999999
     function query_balance(address account) external view returns (uint256){
        uint n = balanceKeys.length;
        for(uint i = 0; i < n; i++){
            if(account == balanceKeys[i]) {//如果找到对应的用户
                return _balance[account];
            }
        }
        return 999999999;
     }
     /// @dev 开户
     function add_user(address account) external{
         balanceKeys.push(account);
         _balance[account] = 0;
     }
 }