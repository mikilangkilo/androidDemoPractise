package highwho.com.bluetoothprintdemo;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static highwho.com.bluetoothprintdemo.Util.connectDevice;
import static highwho.com.bluetoothprintdemo.Util.getPairedDevices;
import static highwho.com.bluetoothprintdemo.Util.isBluetoothOn;
import static highwho.com.bluetoothprintdemo.Util.openBluetooth;
import static highwho.com.bluetoothprintdemo.Util.printTest;

public class MainActivity extends AppCompatActivity {
    private int mCheckPosition = -1;
    private MyListAdapter mMyAdapter;
    private ProgressDialog mProgressDialog;
    private AsyncTask mConnectTask;
    private BluetoothSocket mSocket;
    final static int TASK_TYPE_CONNECT = 1;
    final static int TASK_TYPE_PRINT = 2;
    @BindView(R.id.bluetooth_device_list)ListView mBlueToothList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
        mMyAdapter = new MyListAdapter(this);
        mBlueToothList.setAdapter(mMyAdapter);
        LogUtils.e(getPairedDevices());
        mMyAdapter.AddAll(getPairedDevices());
        mBlueToothList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCheckPosition = position;
                mMyAdapter.notifyDataSetChanged();
            }
        });
    }
    @OnClick(R.id.bluetooth_setting)
    public void onSettingClick(){
        startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
    }
    @OnClick(R.id.bluetooth_conntect)
    public void onConntectClick(){
        if (mCheckPosition < 0){
            Toast.makeText(this, "NO SELECTION!",Toast.LENGTH_SHORT).show();
        }else {
            BluetoothDevice device = mMyAdapter.getItem(mCheckPosition);
            if (device != null && checkBluetoothState()){
                mConnectTask = new ConnectBluetoothTask(TASK_TYPE_CONNECT).execute(device);
            }
        }
    }
    @OnClick(R.id.bluetooth_print)
    public void onPrintClick(){
        if (mCheckPosition < 0){
            Toast.makeText(this, "NO SELECTION!",Toast.LENGTH_SHORT).show();
        }else {
            BluetoothDevice device = mMyAdapter.getItem(mCheckPosition);
            if (device != null && checkBluetoothState()){
                mConnectTask = new ConnectBluetoothTask(TASK_TYPE_PRINT).execute(device);
            }
        }
    }
    public boolean checkBluetoothState() {
        if (isBluetoothOn()) {
            return true;
        } else {
            openBluetooth(this);
            return false;
        }
    }
    protected void showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.setMessage(message);
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }







    public class MyListAdapter extends BaseAdapter{
        private List<BluetoothDevice> mRecordList = new ArrayList<>();
        private Context mContext;
        public MyListAdapter(Context context) {
            super();
            mContext = context;
        }
        public void AddAll(List<BluetoothDevice> list){
            mRecordList.clear();
            mRecordList.addAll(list);
            notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            return mRecordList.size();
        }

        @Override
        public BluetoothDevice getItem(int position) {
            return mRecordList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, null);
                viewHolder.deviceName = (TextView)convertView.findViewById(R.id.device_name);
                viewHolder.deviceCheck = (CheckBox) convertView.findViewById(R.id.device_check);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            LogUtils.e(mRecordList);
            viewHolder.deviceName.setText(mRecordList.get(position).getName());
            viewHolder.deviceCheck.setChecked(position == mCheckPosition);
            return convertView;
        }

        public class ViewHolder{
            TextView deviceName;
            CheckBox deviceCheck;

        }
    }
    class ConnectBluetoothTask extends AsyncTask<BluetoothDevice, Integer, BluetoothSocket> {
        int mTaskType;

        public ConnectBluetoothTask(int taskType) {
            this.mTaskType = taskType;
        }

        @Override
        protected void onPreExecute() {
            showProgressDialog("请稍候...");
            super.onPreExecute();
        }

        @Override
        protected BluetoothSocket doInBackground(BluetoothDevice... params) {
            if(mSocket != null){
                try {
                    mSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mSocket = connectDevice(params[0]);;
            if (mTaskType == TASK_TYPE_PRINT){
                onConnected(mSocket);
            }
            return mSocket;
        }

        @Override
        protected void onPostExecute(BluetoothSocket socket) {
            mProgressDialog.dismiss();
            if (socket == null || !socket.isConnected()) {
                toast("连接打印机失败");
            } else {
                toast("成功！");
            }
            super.onPostExecute(socket);
        }
    }
    private void toast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
    public void onConnected(BluetoothSocket socket) {
        printTest(socket);
    }

}
