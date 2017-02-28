package kankan.wheel.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by TianWei on 2017/2/17.
 */

public class MainActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.text_view_1).setOnClickListener(this);
        findViewById(R.id.text_view_2).setOnClickListener(this);
        findViewById(R.id.text_view_3).setOnClickListener(this);
        findViewById(R.id.text_view_4).setOnClickListener(this);
        findViewById(R.id.text_view_5).setOnClickListener(this);
        findViewById(R.id.text_view_6).setOnClickListener(this);
        findViewById(R.id.text_view_7).setOnClickListener(this);
        findViewById(R.id.text_view_8).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.text_view_1:
                startActivity(new Intent(this,CitiesActivity.class));
                break;
            case R.id.text_view_2:
                startActivity(new Intent(this,DateActivity.class));
                break;
            case R.id.text_view_3:
                startActivity(new Intent(this,PasswActivity.class));
                break;
            case R.id.text_view_4:
                startActivity(new Intent(this,SlotMachineActivity.class));
                break;
            case R.id.text_view_5:
                startActivity(new Intent(this,SpeedActivity.class));
                break;
            case R.id.text_view_6:
                startActivity(new Intent(this,CitiesActivity.class));
                break;
            case R.id.text_view_7:
                startActivity(new Intent(this,TimeActivity.class));
                break;
            case R.id.text_view_8:
                startActivity(new Intent(this,Time2Activity.class));
                break;

        }
    }
}
