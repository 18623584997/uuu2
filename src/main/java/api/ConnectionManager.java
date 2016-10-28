package api;



import io.netty.channel.Channel;

import java.util.*;

/**
 * Created by Administrator on 2016/10/25.
 */
public class ConnectionManager {

    public  static Map<String, Connnection> CONNECT_MAPING = new HashMap<>();

    public static void add(Connnection c){
        CONNECT_MAPING.put(c.getID(),c);
    }
    public static void remove(Channel c){
        String id = c.id().asLongText();
        CONNECT_MAPING.remove(id);

    }
    public static Connnection get(String key){
        return CONNECT_MAPING.get(key);
    }

    public static List<Connnection> getAllCon(){
        Set set = CONNECT_MAPING.keySet();
        Iterator<String> it = set.iterator();
        List<Connnection> list = new ArrayList<Connnection>();
        while (it.hasNext()){
            list.add(CONNECT_MAPING.get(it.next()));
        }
        return list;
    };

}
