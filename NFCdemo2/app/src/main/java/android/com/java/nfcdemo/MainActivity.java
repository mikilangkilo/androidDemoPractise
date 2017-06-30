package android.com.java.nfcdemo;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    NfcAdapter nfcAdapter;
    TextView promt;
    Intent newIntent;
    PendingIntent pendingIntent;
    private static final String TAG = "MainActivity";
    IntentFilter[] mFilters;
    String[][] mTechList;
    int mCount = 0;
    NfcA nfcA;
    Ndef ndef;
    MifareUltralight mifareUltralight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG,"onCreate");
        promt = (TextView)findViewById(R.id.promt);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        newIntent = new Intent(this,this.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(
                this, 0, newIntent, 0);
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        try{
            ndef.addDataType("*/*");
        }catch (IntentFilter.MalformedMimeTypeException e){
            Log.e(TAG,"crash = "+e);
        }
        mFilters = new IntentFilter[]{ndef,};
        mTechList = new String[][]{new String[]{NfcA.class.getName()}};
    }

    @Override
    protected void onResume() {
        Log.i(TAG,"onResume");
        super.onResume();

        nfcAdapter.enableForegroundDispatch(
                this, pendingIntent, mFilters, mTechList);
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

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG,"onNewIntent");
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
        for (String tech: tag.getTechList()){
            Log.d(TAG,"tech = "+tech);
        }
        Log.d(TAG,"tag.toString ="+tag.toString());
        Parcelable[] rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (rawMessages != null){
            NdefMessage[] messages = new NdefMessage[rawMessages.length];
            for (int i = 0; i < rawMessages.length; i++){
                messages[i] = (NdefMessage)rawMessages[i];
                Log.d(TAG,"i = "+i+", message = "+messages[i]);
            }
        }
        promt.setText("Discover TAG No."+ ++mCount+" with Intent = "+intent);
        nfcA = NfcA.get(tag);
        Log.d(TAG,"nfcA = "+nfcA);
        Log.d(TAG,"atqa = "+nfcA.getAtqa());
        ndef = Ndef.get(tag);
        Log.d(TAG,"ndef = "+ndef);
        mifareUltralight = MifareUltralight.get(tag);
        Log.d(TAG,"mifareUltralight = "+mifareUltralight);
        if (tag == null){
            Log.e(TAG,"no tag!!!!");
            return;
        }
    }
}
