package mvp.gxj.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

@ContentView(value = R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.btn)
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewInjectUtils.inject(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"注解成功！",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
