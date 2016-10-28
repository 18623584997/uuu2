package servlet;

import api.ConnectionManager;
import api.Connnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static api.ConnectionManager.getAllCon;

/**
 * Created by Administrator on 2016/10/21.
 */
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String content = req.getParameter("content");
        List<Connnection> list =  ConnectionManager.getAllCon();

        for (Connnection c:list) {
            c.send(content+"$");

        }

    }
}
