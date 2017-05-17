package com.utsoft.jsp;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 上下文对象。
 *
 *
 */
@WebServlet(name="sendMsg",urlPatterns={"/room/send.do","/room/Donnie.html"})
public class SendMsgServlet02 extends HttpServlet {

    private ServletContext application ;

    @Override
    public void init() throws ServletException {
        System.out.println("请求初始化方法。--init()");

        // ?? 为什么放在这里面。
        //第一次访问的时候，创一个ArrayList存入上下文，用于存储用户的多条聊天消息
        //获取上下文的对象 上下文对象是在服务器中开辟的一块共享的内存区域
        application = this.getServletContext();//牵涉到后期 jsp中的隐式对象
        // 然后将消息存放到这个共享内存里。
        List<String> msgList = new ArrayList<>();
        application.setAttribute("msgList",msgList);
    }

    @Override
    public void destroy() {
        System.out.println("请求执行完，消毁灭的方法。+ destroy() ");
    }

    //@Override
    //protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //    // 这里面重写父类里面的 service() 方法，所以 doGet() 和 doPost() 方法它是一个都不会进的。
    //    System.out.println("进入了service() 方法里面。");
    //    // request 和 response 是tomcat 里面的 ，你调用这个方法必须要传入这两个参数。
    //
    //
    //    System.out.println("999：" + request.getContextPath()); // 获取工程名字。就是你的项目名。查API 里面还有很多的方法的。
    //    System.out.println("44:" + request.getQueryString()); // 获取你请求的参数。
    //    System.out.println("获取你请求的URL：" + request.getRequestURI() // 获取请求路径。
    //    );
    //}



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("get 请求方法，调用 post 请求方法。--doGet()");
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("------doPost()");
        //获取前台界面提交的参数msg
        String msg = req.getParameter("msg");
        if(null==msg){
            msg = "";
        }
        String face = req.getParameter("face");
        if(null==face){
            face = "";
        }

        // 获取客户端IP
        String ip = req.getRemoteAddr();
        //System.out.println(ip);
        StringBuilder str = new StringBuilder(ip);
        str.append("Say:");
        str.append(msg);
        str.append("<img src='../faces/");
        str.append(face);
        str.append(".gif'>");
        System.out.println("ddd：" + str);

        // 在把 共享里面的数据拿出来。
        List<String> msgs = (List<String>) application.getAttribute("msgList");
        // 把获取客服端的消息 存放在这个共享的区域去。
        msgs.add(str.toString());


        /**
         *  ServletConfig 过滤张话
         */
        ServletConfig config = this.getServletConfig();
        // 它是 web.xml 配置的
       String keyword = config.getInitParameter("keyword");
       String[] keywords = keyword.split(",");
        for (String s : keywords) {
            msg = msg.replaceAll(s,"***");
        }



        // 在把输出到页面去。
        PrintWriter writer = resp.getWriter();
        writer.println("<html>");
        writer.println("<head>");
        // 页面自动刷新。
        writer.println("<meta http-equiv='refresh' content='1'>");
        writer.println("</head>");
        writer.println("<body>");
        writer.println(str);
        writer.println("</body>");
        writer.println("</html>");


        // request 以获取以上这些信息。respons 就可以设置这些信息。
    }
}
