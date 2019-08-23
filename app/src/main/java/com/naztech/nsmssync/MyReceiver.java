package com.naztech.nsmssync;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyReceiver extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SmsBroadcastReceiver";
    String msg,phoneNo= "";
    private TextView textView;

    @Override
    public void onReceive(Context context, Intent intent) {

        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
       // throw new UnsupportedOperationException("Not yet implemented");

        Log.i(TAG,"Intent Received: "+ intent.getAction());
        if (intent.getAction()==SMS_RECEIVED)
        {
            Bundle dataBundle = intent.getExtras();
            if (dataBundle!=null)
            {
                Object[] mypdu = (Object[])dataBundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[mypdu.length];

                for (int i = 0; i<mypdu.length;i++)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    {
                        String format = dataBundle.getString("format");
                        messages[i] = SmsMessage.createFromPdu((byte[]) mypdu[i],format);
                    }
                    else
                    {
                        //<API 23
                        messages[i] = SmsMessage.createFromPdu((byte[]) mypdu[i]);

                    }
                    msg = messages[i].getMessageBody();
                    phoneNo = messages[i].getOriginatingAddress();

//                    InboxFragment.getNewPhoneNo = messages[i].getOriginatingAddress();
//                    InboxFragment.getNewMessage = messages[i].getMessageBody();
//                    InboxFragment.getNewDate = messages[i].getUserData().toString();
//                    InboxFragment.getNewStatus = "Success";
                    //InboxFragment inboxFragment = new InboxFragment();

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat dateformat = new SimpleDateFormat("dd MMM");//"dd-MMM-yyyy hh:mm:ss aa"
                    String datetime = dateformat.format(c.getTime());

                    InboxFragment.insertMessageIntoList(messages[i].getMessageBody(),messages[i].getOriginatingAddress(),
                            datetime,"Success!");


                }
                Toast.makeText(context, "Message: "+msg+"\nNumber: "+phoneNo, Toast.LENGTH_LONG).show();
                Log.d("msgResult",msg);
            }
        }
    }
}
