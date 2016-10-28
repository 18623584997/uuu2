package server;

import handler.MyHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/10/24.
 */
public class Server {



    public void start()  {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();




        try{
            ServerBootstrap server = new ServerBootstrap();
            server.group(boss,worker);
            server.channel(NioServerSocketChannel.class);
            server.option(ChannelOption.SO_BACKLOG,1000);
            server.option(ChannelOption.TCP_NODELAY,true);
            server.option(ChannelOption.SO_KEEPALIVE,true);

            server.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();

                    ByteBuf delimiter = Unpooled.copiedBuffer("$".getBytes());
                   // pipeline.addLast(new IdleStateHandler(25,25,30, TimeUnit.SECONDS));
                    pipeline.addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
                    pipeline.addLast(new StringDecoder(Charset.forName("UTF-8")));
                    pipeline.addLast(new StringEncoder(Charset.forName("UTF-8")));
                    pipeline.addLast(new MyHandler());


                }
            });
            ChannelFuture  fu = server.bind(9000).sync();
            System.out.println("netty启动成功");
            fu.channel().closeFuture().sync();
        }catch (Exception e){

        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }



}
