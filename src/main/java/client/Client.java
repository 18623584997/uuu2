package client;

import handler.MyClientHandler;
import handler.MyHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/10/25.
 */
public class Client {


    private EventLoopGroup worker = new NioEventLoopGroup();


    public void connect(){
        try{
            Bootstrap c = new Bootstrap();
            c.group(worker);
            c.channel(NioSocketChannel.class);
            c.option(ChannelOption.TCP_NODELAY,true);
            c.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast(new IdleStateHandler(0,50,0, TimeUnit.SECONDS));
                    ByteBuf delimiter = Unpooled.copiedBuffer("$".getBytes());
                    // pipeline.addLast(new IdleStateHandler(25,25,30, TimeUnit.SECONDS));
                    pipeline.addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
                    pipeline.addLast(new StringDecoder(Charset.forName("UTF-8")));
                    pipeline.addLast(new StringEncoder(Charset.forName("UTF-8")));
                    pipeline.addLast(new MyClientHandler());
                }
            });

            ChannelFuture f = c.connect("127.0.0.1",9000).sync();
            f.channel().closeFuture().sync();
        }catch (Exception e){
            worker.shutdownGracefully();

        }

    }

}
