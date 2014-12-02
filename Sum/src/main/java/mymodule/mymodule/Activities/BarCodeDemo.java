package mymodule.mymodule.Activities;

import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.Intents;

import mymodule.mymodule.sum.R;

public class BarCodeDemo extends Activity {

    private TextView resultTextView;
    private EditText qrStrEditText;
    private ImageView qrImgImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bar_code_activity);

        resultTextView = (TextView) this.findViewById(R.id.tv_scan_result);
        qrStrEditText = (EditText) this.findViewById(R.id.et_qr_string);
        qrImgImageView = (ImageView) this.findViewById(R.id.iv_qr_image);

        Button scanBarCodeButton = (Button) this.findViewById(R.id.btn_scan_barcode);

        scanBarCodeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //打开扫描界面扫描条形码或二维码
                Intent openCameraIntent = new Intent(BarCodeDemo.this,CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //处理扫描结果（在界面上显示）
        if (resultCode == RESULT_OK) {
            //Bundle bundle = data.getExtras();
            //String scanResult = bundle.getString("result");
            String result = data.getStringExtra(Intents.Scan.RESULT);
            resultTextView.setText(result);
            if (!result.startsWith("kkou")) return;
            String[] arr =  result.split(",");
            barCodeResult(arr);
        }
    }
    enum BarCodeKey{
        ITEM_DETAIL,
        BLUE
    }

    void barCodeResult(String[] arr){
        if (arr == null || arr.length < 3) return;

        String key = arr[1];
        String content = arr[2];
        String title = (arr.length == 4) ? arr[3] : "hello";
        if (key.equals(BarCodeKey.ITEM_DETAIL.toString())){
            String show = "key="+key+" content="+content+" title="+title;
            Toast.makeText(this,show,Toast.LENGTH_LONG);
            resultTextView.setText(resultTextView.getText()+" "+show);
        }
    }

    /*
    调用NavUtils.getParentActivityIntent()方法可以获取到跳转至父Activity的Intent，
    然后如果父Activity和当前Activity是在同一个Task中的，则直接调用navigateUpTo()方法进行跳转，
    如果不是在同一个Task中的，则需要借助TaskStackBuilder来创建一个新的Task。
    这样，就按照标准的规范成功实现ActionBar导航的功能了。
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                /*获取到跳转至父Activity的Intent*/
                /*Intent upIntent  = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)){
                    TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities();
                } else {
                    upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    NavUtils.navigateUpTo(this, upIntent);
                }*/
                return true;

            case R.id.action_collection:
                Toast.makeText(this, "action_collection", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
