package handler;

import api.ConnectionManager;
import api.Connnection;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Administrator on 2016/10/25.
 */
public class MyHandler extends ChannelHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel  c = ctx.channel();
        Connnection con = new Connnection();
        con.setChannel(c);
        String str = "链接成功$";
        ConnectionManager.add(con);
        System.out.println("socket id:"+con.getID());
        con.send(str);

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端连接已断开");
        ConnectionManager.remove(ctx.channel());

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       ;
        Channel  c = ctx.channel();
        Connnection con = new Connnection();
        con.setChannel(c);
        System.out.println(con.getID());
        System.out.print(msg);


    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();

    }
}
