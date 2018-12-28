package yomii.adaptdptoscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by Yomii on 2017/6/6.
 */

public class CalculateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textView = new TextView(this);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        int padding = getResources().getDimensionPixelOffset(R.dimen.dp_15);
        textView.setPadding(padding, padding, padding, padding);
        setContentView(textView);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.common_icon_back);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        textView.setText("在不使用代码获取 density 值的情况下，知道屏幕尺寸也是可以计算的。\n" +
                "\n" +
                "density = screenDpi / 160;\n" +
                "\n" +
                "160dpi 是 1dp=1px 的基准屏幕\n" +
                "\n" +
                "screenDpi = √(widthPixels * widthPixels + heightPixels * heightPixels) / a\n" +
                "\n" +
                "a 是屏幕对角线长度，也就是俗话说的屏幕尺寸。其实就是勾股定理算了下对角线上的像素点数量，然后除以长度，得到了每长度单位上的像素点数量，就是 density。\n" +
                "\n" +
                "widthDpi 就是用宽度的像素点数量除以 density，得到了在屏幕宽度上有多少个这样的长度单位（dpi），很好理解吧。\n" +
                "\n" +
                "很多公司的UI设计图以IOS为准,比如以 iphone 6/7 为例，" +
                "设备宽高为 750x1334，ppi=dpi=326，那么 widthDpi = 750 / (326 / 160) = 368");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
