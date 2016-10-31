package handler;

import io.netty.channel.Channel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/10/31.
 */
public class HeartTimer {

    public Channel  channel;

    public HeartTimer(Channel  channel){
        this.channel = channel;
    }

    public  void  start(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                Timer  t = new Timer( );
                t.schedule(new HeartTask(),1000*60,1000*60);

            }
        }).start();
    }



    public class  HeartTask extends TimerTask{

        @Override
        public void run() {
            channel.writeAndFlush("000$");
        }
    }

}
