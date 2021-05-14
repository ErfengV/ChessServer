package com.et.chess.controllers;

import com.et.chess.service.ProtocolServer;
import com.et.chess.service.ProtocolServerImpl;
import com.et.chess.util.ChessUtils;
import com.google.gson.Gson;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/chess")
public class AIRestController {
    private static Logger logger = LoggerFactory.getLogger(AIRestController.class);

//    @Autowired
//    private AIService aiService;

    // 记录在线AI数量
    private static int onlineCount = 0;

    //房间号 + List-->用户和AI
    public static ConcurrentHashMap<String, ProtocolServer> root = new ConcurrentHashMap<String, ProtocolServer>();

    // private List<>
    //创建房间,建立连接
    @RequestMapping(value = "/aihouse/join")
    public CompletableFuture<String> join(@RequestParam("protocol") String protocol) {
        // static CompletableFuture<U> supplyAsync(Supplier<U> supplier)
        //   Supplier就是一个接口
        //    接口中的方法:   T get();
        //非阻塞式异步编程
        // 方法。因为在web ui的微服务对rest api的调用中将使用这种高并发的编程方法，所以为了保证与调用端保持同步，这里也使用这种方法.
        return CompletableFuture.supplyAsync(() -> {
            Map<String, Object> map = new HashMap<>();
            String[] args = protocol.split(ChessUtils.RULE);

            System.out.println(protocol + "========>" + args.length);
            if (args.length < 2) {
                map.put("code", 0);
                map.put("msg", "协议传输有误");
                return new Gson().toJson(map);
            }
            ProtocolServerImpl ai = new ProtocolServerImpl();
            root.put(args[0], ai);

            addOnlineCount();

            map.put("code", 1);
            map.put("msg", "连接创建成功");
            return new Gson().toJson(map);
        });
    }


    @RequestMapping(value = "/aihouse/move")
    public CompletableFuture<String> getToMove(@RequestParam("protocol") String protocol) {
        // static CompletableFuture<U> supplyAsync(Supplier<U> supplier)
        //   Supplier就是一个接口
        //    接口中的方法:   T get();
        //非阻塞式异步编程方法。因为在web ui的微服务对rest api的调用中将使用这种高并发的编程方法，所以为了保证与调用端保持同步，这里也使用这种方法.
        return CompletableFuture.supplyAsync(() -> {
            Map<String, Object> map = new HashMap<>();
            String[] args = protocol.split(ChessUtils.RULE);
            System.out.println(args.length);
            if (args.length < 3) {
                map.put("code", 0);
                map.put("msg", "协议传输有误");
                return new Gson().toJson(map);
            }
            String[] moves = args[2].split(ChessUtils.MOVEXY);
            //public String getToMoveSend(String command, int num, String user, String idx, int x, int y);
            String[] getMove = root.get(args[0]).getToMoveSend("move", 0, args[1],
                    moves[0], Integer.parseInt(moves[1]), Integer.parseInt(moves[2])).split(",");
            map.put("code", 1);
            map.put("idx", getMove[0]);
            map.put("x", getMove[1]);
            map.put("y", getMove[2]);
            //map.put("idx", pic);
            //map.put("msg","");
            return new Gson().toJson(map);
        });
    }

    @RequestMapping(value = "/aihouse/lose")
    public CompletableFuture<String> lose(@RequestParam("protocol") String protocol) {
        // static CompletableFuture<U> supplyAsync(Supplier<U> supplier)
        //   Supplier就是一个接口
        //    接口中的方法:   T get();
        //非阻塞式异步编程方法。因为在web ui的微服务对rest api的调用中将使用这种高并发的编程方法，所以为了保证与调用端保持同步，这里也使用这种方法.
        return CompletableFuture.supplyAsync(() -> {
            Map<String, Object> map = new HashMap<>();
            String[] args = protocol.split(ChessUtils.RULE);
            if (args.length < 2) {
                map.put("code", 0);
                map.put("msg", "协议传输有误");
                return new Gson().toJson(map);
            }
            root.remove(args[0]);
            subOnlineCount();
            //@TODO:释放AI
            map.put("code", 1);
            map.put("msg", "认输成功");

            return new Gson().toJson(map);
        });
    }

    @RequestMapping(value = "/aihouse/regret")
    public CompletableFuture<String> regret(@RequestParam("protocol") String protocol) {
        return CompletableFuture.supplyAsync(() -> {
            Map<String, Object> map = new HashMap<>();
            int isagree = 6;
            String[] args = protocol.split(ChessUtils.RULE);
            if (args.length < 2) {
                map.put("code", 0);
                map.put("msg", "协议传输有误");
                return new Gson().toJson(map);
            }
            //public boolean isAgree(String command, int num, String user)
            map.put("code", 1);
            if (root.get(args[0]).isAgree("regret", 0, "")) isagree = 4;
            map.put("msg", isagree);
            return new Gson().toJson(map);
        });
    }

    @RequestMapping(value = "/aihouse/succ")
    public CompletableFuture<String> succ(@RequestParam("protocol") String protocol) {
        return CompletableFuture.supplyAsync(() -> {
            Map<String, Object> map = new HashMap<>();
            String[] args = protocol.split(ChessUtils.RULE);
            if (args.length < 2) {
                map.put("code", 0);
                map.put("msg", "协议传输有误");
                return new Gson().toJson(map);
            }
            //public boolean isAgree(String command, int num, String user)
            root.remove(args[0]);
            subOnlineCount();
            map.put("code", 1);
            map.put("msg", "游戏结束");
            return new Gson().toJson(map);
        });
    }

    /**
     * 加锁防止并发出现数目问题
     */
    private static synchronized int getOnlineCount() {
        return onlineCount;
    }

    private static synchronized void addOnlineCount() {
        onlineCount++;
    }

    private static synchronized void subOnlineCount() {
        onlineCount--;
    }


}
