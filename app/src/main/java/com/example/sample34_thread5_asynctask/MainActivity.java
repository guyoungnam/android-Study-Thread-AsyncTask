package com.example.sample34_thread5_asynctask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    static ProgressDialog dialog;
    MyDialogFragment xxx;
    int pValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void send(View v){
        new MyAsyncTask().execute(100);
    }//end send

    // Void
    class MyAsyncTask extends AsyncTask<Integer,Integer,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("MyTag" , "onPreExecute: 작업이 시작하기전에 호출,초기화작업");
            xxx = MyDialogFragment.getInstance();
            xxx.show(getSupportFragmentManager(),"MyTag");
        }
        // 작업스레드가 수행한다.
        @Override
        protected String doInBackground(Integer... integers) {
            Log.i("MyTag" , "doInBackground: 작업스레드가 수행,");
            while (pValue <integers[0]){
                pValue++;
                publishProgress(pValue);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }//end while
            //  //onProgressUpdate가 받는다.
            return "작업완료";
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.i("MyTag" , "onProgressUpdate: publishProgress메서드에 의해호출");
            dialog.setProgress(values[0]);
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("MyTag" , "onPostExecute: doInBackground메서드 수행후에 호출");
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            xxx.dismiss();
        }
    }//end MyAsyncTask

    ///////////////////////////////////
    public static class MyDialogFragment extends DialogFragment {
        public static MyDialogFragment getInstance(){
            return new MyDialogFragment();
        }
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            dialog = new ProgressDialog(getActivity());
            dialog.setTitle("타이틀");
            dialog.setMessage("메시지");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            return dialog;
        }
    }//end MyDialogFragment
    ///////////////////////////////////

}//end class
