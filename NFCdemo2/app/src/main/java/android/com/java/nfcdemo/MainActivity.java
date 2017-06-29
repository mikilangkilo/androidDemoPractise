package android.com.java.nfcdemo;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    NfcAdapter nfcAdapter;
    TextView promt;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG,"onCreate");
        promt = (TextView)findViewById(R.id.promt);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

    }

    @Override
    protected void onResume() {
        Log.i(TAG,"onResume");
        super.onResume();
        Intent newIntent = new Intent(this,this.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, newIntent, 0);
        nfcAdapter.enableForegroundDispatch(
                this, pendingIntent, null, new String[][] {
                        new String[] { NfcA.class.getName() } });
        if (nfcAdapter == null){
            promt.setText("设备不支持nfc");
            finish();
            return;
        }
        if (!nfcAdapter.isEnabled()){
            promt.setText("在系统设置中启动nfc功能");
            finish();
            return;
        }
        Log.d(TAG,"getIntent().getAction()="+getIntent().getAction());
        Log.d(TAG,"? = "+NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction()));
        Log.d(TAG,"2 = "+NfcAdapter.ACTION_TAG_DISCOVERED.equals(getIntent().getAction()));
        Log.d(TAG,"3 = "+NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction()));
        Log.d(TAG,"4 = "+NfcAdapter.ACTION_ADAPTER_STATE_CHANGED.equals(getIntent().getAction()));

        if ( NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())){
            Log.d(TAG,"tag is ov");
            processIntent(getIntent());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG,"onNewIntent");
//        Toast.makeText(MainActivity.this, "onNewIntent + intent = "+intent,Toast.LENGTH_SHORT).show();
        Log.d(TAG,"new intent = "+intent.getAction());
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())){
            processIntent(intent);
        }
    }

    private String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("0x");
        if (src == null || src.length <= 0){
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++){
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            Log.d(TAG,"buffer = "+buffer);
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
        nfcAdapter.disableForegroundDispatch(this);
    }

    private void processIntent(Intent intent){
        //TODO
        Log.i(TAG,"processIntent");
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Log.d(TAG,"tag.getId = "+tag.getId());
        promt.setText(tag.getId().toString());

        if (tag == null){
            Log.e(TAG,"no tag!!!!");
            return;
        }
        String[] techList = tag.getTechList();
        Parcel oldParcel = Parcel.obtain();
        tag.writeToParcel(oldParcel, 0);
        int len = oldParcel.readInt();
        byte[] id = new byte[0];
        if (len >= 0){
            id = new byte[len];
            oldParcel.readByteArray(id);
        }
        int[] oldTechList = new int[oldParcel.readInt()];
        oldParcel.readIntArray(oldTechList);

    }

}
