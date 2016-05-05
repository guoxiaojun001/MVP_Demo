package mvp.gxj.com.mvp_demo;

/**
 * Created by gxj on 2016/4/21.
 */
public class LoginModule {


    public void login(final String username, final String password,
                      final RequestListener requestListener)   {
        //模拟子线程耗时操作
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                //模拟登录成功
                if ("zhy".equals(username) && "123".equals(password))
                {
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    requestListener.requestSuccess(user);
                } else {
                    requestListener.requestFailed();
                }
            }
        }.start();
    }
}
