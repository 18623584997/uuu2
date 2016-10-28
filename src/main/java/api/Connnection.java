package api;

import io.netty.channel.Channel;

/**
 * Created by Administrator on 2016/10/25.
 */
public class Connnection {
    private Channel channel;


    public void send(Object obj){
        channel.writeAndFlush(obj);
    }

    public String getID(){
        return channel.id().asLongText();
    }



    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
