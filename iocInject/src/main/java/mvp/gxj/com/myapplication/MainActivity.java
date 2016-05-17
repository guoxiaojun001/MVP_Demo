package mvp.gxj.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import mvp.gxj.com.inject.ContentView;
import mvp.gxj.com.inject.ViewInject;
import mvp.gxj.com.inject.ViewInjectUtils;

@ContentView(value = R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.tv1)
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewInjectUtils.inject(this);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"注解成功！",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
