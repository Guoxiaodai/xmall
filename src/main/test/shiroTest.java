import com.guoxiaodai.shiro.MyRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class shiroTest {
/*    SimpleAccountRealm realm =new SimpleAccountRealm();

    @Before
    public void addUser(){
        realm.addAccount("xbc","123456");
    }*/
    @Test
    public void testAuthentication(){
        /*Scanner scanner=new Scanner(System.in);
        String username=scanner.nextLine();
        String password=scanner.nextLine();
*/
            MyRealm myRealm=new MyRealm();

            //1、构造SecurityManager环境
            DefaultSecurityManager defaultSecurityManager =new DefaultSecurityManager();
            defaultSecurityManager.setRealm(myRealm);

            //2、主体提交认证请求
            SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token =new UsernamePasswordToken("admin","11");
        subject.login(token);
        System.out.println("1---------"+subject.isAuthenticated());

    }
}
