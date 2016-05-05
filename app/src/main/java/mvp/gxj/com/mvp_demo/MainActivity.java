package mvp.gxj.com.mvp_demo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by gxj on 2016/4/21.
 *
 * http://mp.weixin.qq.com/s?__biz=MzA3ODg4MDk0Ng==&mid=403539764&idx=1&sn=d30d89e6848a8e13d4da0f5639100e5f#rd
 */
public class MainActivity extends Activity implements View.OnClickListener,IUserLoginView{
    private EditText login_phone, login_password;
    private Button login_delete_phone, login_delete_password,
            login_login, login_forget_password;

    ProgressDialog dialog ;
    private UserLoginPresenter uPresenter;
    private User user ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        uPresenter = new UserLoginPresenter(this);
    }

    private void initView() {
        login_phone = (EditText) findViewById(R.id.login_phone);
        login_password = (EditText) findViewById(R.id.login_password);
        login_delete_phone = (Button) findViewById(R.id.login_delete_phone);
        login_delete_password = (Button) findViewById(R.id.login_delete_password);
        login_login = (Button) findViewById(R.id.login_login);
        login_forget_password = (Button) findViewById(R.id.login_forget_password);

        login_delete_phone.setOnClickListener(this);
        login_delete_password.setOnClickListener(this);

        login_login.setOnClickListener(this);
        login_forget_password.setOnClickListener(this);
    }

    @Override
    public void setPhone(String phone) {
        login_phone.setText(phone);
    }

    @Override
    public void setPassWroid(String passWroid) {
        login_password.setText(passWroid);
    }

    @Override
    public String getUserName() {
        return  login_phone.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return login_password.getText().toString().trim();
    }

    @Override
    public void clearUserName() {
        login_phone.setText("");
    }

    @Override
    public void clearPassword() {
        login_password.setText("");
    }

    @Override
    public void showLoading() {
        if(null == dialog){
            dialog = new ProgressDialog(this);
        }

        dialog.setTitle("正在登录");
        dialog.show();

    }

    @Override
    public void hideLoading() {
        if(null != dialog){
            dialog.dismiss();
        }
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void showFailedError() {
        Toast.makeText(this,"登录失败！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_delete_phone:
                uPresenter.deletePhoneClick();
                break;

            case R.id.login_delete_password:
                uPresenter.deletePassWordClick();
                break;

            case R.id.login_login:
                uPresenter.login();
                break;

            case R.id.login_forget_password:
                break;
        }
    }
}
