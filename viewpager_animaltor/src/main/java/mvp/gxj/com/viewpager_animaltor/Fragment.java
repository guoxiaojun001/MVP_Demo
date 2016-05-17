package mvp.gxj.com.viewpager_animaltor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment extends android.support.v4.app.Fragment {
			@Override
			public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				Bundle bundle = getArguments();
				int layout_id = bundle.getInt("layout_id");
				View view = inflater.inflate(layout_id, null);
				return view;
			}
}
