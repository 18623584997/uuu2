package servlet;

import server.Server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by Administrator on 2016/10/25.
 */
public class TomcatListen implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        try{

            new Thread(new Runnable(){
                @Override
                public void run() {
                    Server s = new Server();
                    s.start();

                }
            }).start() ;


        }catch (Exception e){
            e.printStackTrace();
        }



    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
