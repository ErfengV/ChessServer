package com.et.chess.domain;

import java.util.concurrent.ConcurrentHashMap;

//记录登录的用户
public class WechatUserList {

    public static ConcurrentHashMap<Integer, WechatUser> root = new ConcurrentHashMap<Integer, WechatUser>();


}
