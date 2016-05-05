package mvp.gxj.com.mvp_demo;

/**
 * Created by gxj on 2016/4/21.
 */
public interface IUserLoginView {

    String getUserName();

    String getPassword();

    void setPhone(String phone);

    void setPassWroid(String passWroid);

    void clearUserName();

    void clearPassword();

    void showLoading();

    void hideLoading();

    void setUser(User user);

    void showFailedError();
}
