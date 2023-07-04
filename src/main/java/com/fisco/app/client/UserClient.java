package com.fisco.app.client;



public interface UserClient {

    public boolean user_login(String username, String password);

    public boolean admin_login(String username, String password);

    public boolean register(String username, String password);

    public int query_balance(String address);

    public boolean add_balance(String address, int amount);

    public boolean transfer(String from, String to, int amount);

    String query_address_by_username(String username);

}
