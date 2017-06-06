package yomii.adaptdptoscreen;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView argumentsTv = (TextView) findViewById(R.id.metrics_argus);
        StringBuilder sb = new StringBuilder();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int widthDpi = (int) (metrics.widthPixels / metrics.density);
        int heightDpi = (int) (metrics.heightPixels / metrics.density);

        appendArguments(sb, "screen metrics: \n");

        appendArguments(sb, "屏幕Dpi: " + metrics.densityDpi);
        appendArguments(sb, "逻辑密度: " + metrics.density);
        appendArguments(sb, "缩放密度: " + metrics.scaledDensity);
        appendArguments(sb, "显示屏幕宽度: " + metrics.widthPixels);
        appendArguments(sb, "显示屏幕高度: " + metrics.heightPixels);
        appendArguments(sb, "width dpi: " + metrics.widthPixels / metrics.density);

        if (Build.VERSION.SDK_INT >= 17){
            sb.append("\n");
            getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            appendArguments(sb, "真实屏幕宽度: " + metrics.widthPixels);
            appendArguments(sb, "真实屏幕高度: " + metrics.heightPixels);
        }

        argumentsTv.setText(sb.toString());
    }

    private void appendArguments(StringBuilder sb, String argument) {
        sb.append(argument).append("\n");
    }

    public void toCalculatePage(View view) {
        startActivity(new Intent(this,CalculateActivity.class));
    }

    public void toImmersivePage(View view) {
        startActivity(new Intent(this,ImmersiveActivity.class));
    }
}
