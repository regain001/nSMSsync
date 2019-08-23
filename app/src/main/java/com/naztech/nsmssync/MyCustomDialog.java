package com.naztech.nsmssync;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by User on 12/10/2017.
 */

public class MyCustomDialog extends DialogFragment {



    private TextView mNumber, mMessage;
    public static TextView refNumber, refMessage;
    private TextView mActionOk, mActionRetry;

    private static final String TAG = "MyCustomDialog";
    public static TextView refActionRetry;

    public interface OnInputSelected{
        void sendInput(String input);
    }
    public OnInputSelected mOnInputSelected;
    @NonNull
    @Override
    public View onCreateView(final LayoutInflater inflater, @NonNull ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_dialog, container, false);
        mActionOk = view.findViewById(R.id.action_ok);
        refActionRetry = mActionRetry = view.findViewById(R.id.action_retry);
        refMessage = mMessage = view.findViewById(R.id.TVnewMessage);
        refNumber = mNumber = view.findViewById(R.id.TVnewPhoneNo);

        getDataFromInboxFragment();

        mActionRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog");
                getDialog().dismiss();
            }
        });

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: capturing input.");



//                String input = mInput.getText().toString();
//                if(!input.equals("")){
////
////                    //Easiest way: just set the value.
////                    MainFragment fragment = (MainFragment) getActivity().getFragmentManager().findFragmentByTag("MainFragment");
////                    fragment.mInputDisplay.setText(input);
//
//                    mOnInputSelected.sendInput(input);
//                }


                getDialog().dismiss();
            }
        });

        return view;
    }

    private void getDataFromInboxFragment() {
        Bundle args = getArguments();
        String number = args.getString("number");
        String message = args.getString("message");
        String status = args.getString("status");

        mNumber.setText(number);
        mMessage.setText(message);
        mMessage.setMovementMethod(new ScrollingMovementMethod());

        if(status != "Success"){
            refActionRetry.setVisibility(View.VISIBLE);
        }else{
            refActionRetry.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mOnInputSelected = (OnInputSelected) getTargetFragment();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage() );
        }
    }


}

