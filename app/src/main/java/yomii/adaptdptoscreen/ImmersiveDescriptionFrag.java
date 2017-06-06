package yomii.adaptdptoscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Yomii on 2017/6/6.
 */

public class ImmersiveDescriptionFrag extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.immersive_description_frag, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView caption = (TextView) view.findViewById(R.id.title_tv_caption);
        caption.setText(R.string.immersive_title);
    }
}
