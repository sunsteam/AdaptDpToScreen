package yomii.adaptdptoscreen;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Yomii on 2017/6/6.
 */

public class ImmersiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        ViewPager viewPager = new ViewPager(this);
        viewPager.setId(android.R.id.tabcontent);
        viewPager.setAdapter(new FragAdapter(getSupportFragmentManager()));
        setContentView(viewPager);
    }

    private class FragAdapter extends FragmentPagerAdapter{

        FragAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0)
                return new ImmersiveDescriptionFrag();
            else
                return new ImmersiveImageFrag();
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    public void moveBack(View v){
        onBackPressed();
    }
}
