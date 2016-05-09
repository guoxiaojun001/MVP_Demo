package mvp.gxj.com.mycustomscrollview;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    CustomScrollView scrollView;
    List<String> item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        item = new ArrayList<String>();
        for(int a = 0;a < 5; a++){
            item.add("QQ" + a);
        }

        scrollView = (CustomScrollView) findViewById(R.id.scrollView);

        for (final String str : item){
            TextView textView = new TextView(this);
            textView.setText(str);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(50);
            textView.setBackgroundColor(Color.GREEN);

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

            textView.setLayoutParams(layoutParams);
            scrollView.addView(textView);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,"点击了" + str,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
