package com.utsoft.jsp;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 *
 * 一个 servlet 应用。333
 *
 * 请求的提交与响应。
 *
 * Servlet
 *本次课：
 *1.继承于HttpServlet
 *2.重写init destory doGet doPost 4个方法
 *3.在web.xml中配置这个servlet
 *4.通过的注解的方式配置servlet ，web.xml 的配置。
 *
 *
 */
// 注解的方式访问 servlet 服务。
@WebServlet(name="sendMsg",urlPatterns={"/room/send.do","/room/Donnie.html"})
public class SendMsgServlet extends HttpServlet {
    //Servlet在容器中只会有一个实例 一次初始化后续不再初始化 由容器管理的,servlet 实例是在tomcat 容器里面，是tomcat 负责管理的。
    // 所以只会在你启动了tomcat 第一次访问时，会打印 init() 里面的
    // 后台在访问它只会 打印 doGet() 里面的东东。

    @Override
    public void init() throws ServletException {
        System.out.println("请求初始化方法。--init()");
    }

    // 输出了响应信息后，就会调用这个 消毁，你在关闭 tomcat 时也会执行这个方法。
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



    // service() 方法会判断你是什么请求在进入到具体的 doGet/Post() 方法里面去。service() 就是做分发的。
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("get 请求方法，调用 post 请求方法。--doGet()");

        // 获取单个参数
        //System.out.println(req.getParameter("name"));
        //System.out.println(req.getParameter("set"));

        // 获取多个参数
        //for(int i=0; i< req.getParameterValues("name").length; i++){
        //    System.out.println(req.getParameterValues("name")[i]+"\t");
        //}
        // 获取参数列表。就是获取参数的哪个 key
        //Enumeration<String> paraNames =  req.getParameterNames();
        //String tmpName;
        ////遵循Java编程规范  获取了提交的参数列表
        //while(paraNames.hasMoreElements()){
        //    tmpName = paraNames.nextElement();
        //    System.out.println(tmpName);
        //}

        // 获取请求头信息。(报头) 这个key 可以 填写  debug工具里面的所有的报头信息。
        //System.out.println(req.getHeader("User-Agent"));
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

        // 响应都是通过 io 流。来完成的。
        //IO 字节流 和字符流  输入流 和 输出流  输出流
        /****
         * 1.响应字符流
         * PrintWriter writer = response.getWriter();
         * 2.响应字节流
         * OutputStream os = response.getOutputStream();
         * 3.设置报头
         * response.setHeader();
         */

        // 在把输出到页面去。
        PrintWriter writer = resp.getWriter();
        writer.println("<html>");
        writer.println("<body>");
        writer.println(str);
        writer.println("</body>");
        writer.println("</html>");


        // request 以获取以上这些信息。respons 就可以设置这些信息。


    }
    /**
     * 请求是：是 servlet 对象(实例)。调用 init() -- service() 输出响应信息。在调用destroy() 来
     * 完成一次请求。这就是 它的生命周期(一个请求的生命周期)，长连接了？？？
     *
     * 第一次请求时：tomcat 会判断有没有这个 servlet
     * 实例，没有就调用 init() 方法，然后 调用 service()
     * 方法。service() 方法来判断是调用 doGet() 还是 doPost()
     * 方法。所以第一次访问时会调用init()方法创建实例
     * 当第二次访问时你已经有了 servlet 实例了它就直接调用
     * service() 方法 来判断是进行 doGet() 还是 doPost()
     * 方法。 当响应完了 tomcat 会自己 调用 destroy() 来
     * 时放资源的。
     *
     * tomcat 就相当于是 main() 方法样。在javaSE 你第写
     * 一个必须要用 main() 来启动了，而 javaEE 就需要 tomcat
     * 来启动。是tomcat 来创建 servlet 实例的。
     *
     *  注：tomcat 就是 main() 方法。然后根据你访问的路径来
     *  决定你访问什么资源。
     *
     *  request：它会把请求所有的东东，全部放在 request 里面
     *  respons：它会把响应的所有东东，全部放在 respons 里面。
     *
     * request ：对象 创建与回收都是由 tomcat 来完成的。
     *
     * requet 对象里面可以调用很多方法来获取 客服端的信息。
     *
     *
     */
}
