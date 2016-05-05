package mvp.gxj.com.mvp_demo;

/**
 * Created by gxj on 2016/4/21.
 */
public interface RequestListener {

    void requestSuccess(User user);

    void requestFailed();
}
