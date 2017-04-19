package com.utsoft.jsp;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 一个 servlet 应用。333
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

    @Override
    public void destroy() {
        System.out.println("请求执行完，消毁灭的方法。+ destroy() ");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 这里面重写父类里面的 service() 方法，所以 doGet() 和 doPost() 方法它是一个都不会进的。
        System.out.println("进入了service() 方法里面。");
        // request 和 response 是tomcat 里面的 ，你调用这个方法必须要传入这两个参数。
    }

    // service() 方法会判断你是什么请求在进入到具体的 doGet/Post() 方法里面去。service() 就是做分发的。
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("get 请求方法，调用 post 请求方法。--doGet()");
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("------doPost()");
        super.doPost(req, resp);
    }
    /**
     * 请求是：是 servlet 对象(实例)。调用 init() -- service() 输出响应信息。在调用destroy() 来
     * 完成一次请求。这就是 它的生命周期(一个请求的生命周期)，长连接了？？？
     *
     *
     */
}
