package com.deskshop.common.constant;

import com.deskshop.common.link.BankInterface;
import com.deskshop.common.link.ClientInterface;
import com.deskshop.common.link.ServerInterface;
import com.deskshop.common.link.ShopInterface;

public class ServerConstant {
    public static final int PORT  = 4000;
    public static final String IP = "localhost";
    public static BankInterface BANK;
    public static ShopInterface SHOP;
    public static ServerInterface SERVER;
    public static ClientInterface CLIENT;
}
