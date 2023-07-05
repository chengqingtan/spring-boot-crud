package com.fisco.app.client;



public interface UserClient {

    boolean login(String username, String password);

    String query_role(String username);


    boolean register(String username, String password);

    int query_balance(String address);

    boolean add_balance(String address, int amount);

    boolean transfer(String from, String to, int amount);

    String query_address_by_username(String username);

}
