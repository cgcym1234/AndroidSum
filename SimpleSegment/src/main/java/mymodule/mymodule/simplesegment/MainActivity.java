package mymodule.mymodule.simplesegment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import mymodule.mymodule.actionbartest.BadgeView;


public class MainActivity extends Activity {

    Button textView;
    BadgeView badgeView;
    BadgeView badgeView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (Button) findViewById(R.id.badge_view);
        badgeView1 = (BadgeView) findViewById(R.id.badge_view1);
        badgeView1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);

        badgeView = new BadgeView(this, textView);
        //badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        //badgeView.setBadgeBackgroundColor(Color.parseColor("#A4C639"));
        badgeView.setText(" ");
        badgeView.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
