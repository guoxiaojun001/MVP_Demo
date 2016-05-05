package mvp.gxj.com.mvp_demo;

import android.os.Handler;

/**
 * Created by gxj on 2016/4/21.
 */
public class UserLoginPresenter {

    private LoginModule module01;
    private IUserLoginView userLoginView;
    private Handler mHandler = new Handler();

    public UserLoginPresenter(IUserLoginView userLoginView) {
        this.userLoginView = userLoginView;
        this.module01 = new LoginModule();
    }

    public void deletePhoneClick() {
        userLoginView.clearUserName();
    }

    public void deletePassWordClick() {
        userLoginView.clearPassword();
    }


    public void login() {
        userLoginView.showLoading();
        module01.login(userLoginView.getUserName(),
                userLoginView.getPassword(), new RequestListener() {
            @Override
            public void requestSuccess(final User user) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        userLoginView.setUser(user);
                        userLoginView.hideLoading();
                    }
                });
            }

            @Override
            public void requestFailed() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        userLoginView.showFailedError();
                        userLoginView.hideLoading();
                    }
                });
            }

        });
    }

}
