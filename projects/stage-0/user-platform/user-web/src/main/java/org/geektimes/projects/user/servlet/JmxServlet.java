package org.geektimes.projects.user.servlet;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.management.UserManager;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.management.ManagementFactory;


/**
 * @see @link http://localhost:8080/jolokia/read/jolokia:name=User
 */
public class JmxServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        // 获取平台 MBean Server
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        // 为 UserMXBean 定义 ObjectName
        ObjectName objectName = null;
        try {
//            objectName = new ObjectName("org.geektimes.projects.user.management:type=User");
            objectName = new ObjectName("jolokia:name=User");
            // 创建 UserMBean 实例
            User user = new User();
            mBeanServer.registerMBean(createUserMBean(user), objectName);
            System.out.println("user="+user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object createUserMBean(User user) throws Exception {
        return new UserManager(user);
    }
}
