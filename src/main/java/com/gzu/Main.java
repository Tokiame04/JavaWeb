package com.gzu;

import redis.clients.jedis.Jedis;

public class Main {
    public static void main(String[] args) {
        // 1、 创建 Jedis 对象即可
        Jedis jedis = new Jedis("127.0.0.1",6379);
        //清空当前库的所有数据
        jedis.flushDB();
        // 加入数据
        jedis.set("name","Tok1ame");
        jedis.set("age","20");
        jedis.set("high","170");
        // get 输出结果
        System.out.println("name:"+jedis.get("name")+"\nage:"+jedis.get("age")+"\nhigh"+jedis.get("high"));
        // list 列表
        jedis.lpush("list","1","2","3","4");
        System.out.println("list: "+jedis.lrange("list",0,-1));
    }
}