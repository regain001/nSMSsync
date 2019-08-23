package com.naztech.nsmssync;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class InboxFragment extends Fragment {

    private ArrayList<MessageItem> messageList;
    View view;
    private RecyclerView mRecyclerView;
    private MessageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static MessageAdapter staticAdapter;
    public static ArrayList<MessageItem> statciMessageList;




    private static final int MY_PERMISSON_REQUEST_RECEIVE_SMS = 0;
//    public static String getNewMessage = "";
//    public static String getNewPhoneNo = "";
//    public static String getNewStatus = "";
//    public static String getNewDate = "";

    public InboxFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_inbox, container, false);

        getActivity().setTitle("Inbox");

        createMessageList();
        buildRecyclerView();

        smsPermission();


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode)
        {
            case MY_PERMISSON_REQUEST_RECEIVE_SMS:
            {
                Log.d("resultT",""+grantResults[0]+ "   "+PackageManager.PERMISSION_GRANTED+" "+requestCode);
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(getActivity(), "Thanks for permitting", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "Can't do anything until you permit me", Toast.LENGTH_LONG).show();
                }

            }
        }
    }

    private void smsPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.RECEIVE_SMS))
            {

            }
            else
            {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.RECEIVE_SMS}, MY_PERMISSON_REQUEST_RECEIVE_SMS);
            }
        }
    }


    private void createMessageList() {
        statciMessageList = messageList = new ArrayList<>();
        messageList.add(new MessageItem("In this video we start building a RecyclerView, which contains CardViews with 1 ImageView and 2 TextViews each." +
                "In this video we start building a RecyclerView, which contains CardViews with 1 ImageView and 2 TextViews each." +
                "In this video we start building a RecyclerView, which contains CardViews with 1 ImageView and 2 TextViews each." +
                "In this video we start building a RecyclerView, which contains CardViews with 1 ImageView and 2 TextViews each." +
                "In this video we start building a RecyclerView, which contains CardViews with 1 ImageView and 2 TextViews each.",
                "+01554540265","12 May","Success"));
        messageList.add(new MessageItem("In this video we start building a RecyclerView, which contains CardViews with 1 ImageView and 2 TextViews each.",
                "+01554540265","12 May","Failed"));
        messageList.add(new MessageItem("In this video we start building a RecyclerView, which contains CardViews with 1 ImageView and 2 TextViews each.",
                "+01554540265","12 May","Success"));
        messageList.add(new MessageItem("In this video we start building a RecyclerView, which contains CardViews with 1 ImageView and 2 TextViews each.",
                "+01554540265","12 May","Failed"));

    }

    public void buildRecyclerView() {
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        staticAdapter = mAdapter = new MessageAdapter(messageList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        new ItemTouchHelper(callback).attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        OnItemClick();
    }

    private void OnItemClick() {

        mAdapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String message = messageList.get(position).getMessage();
                String number = messageList.get(position).getNumber();
                String status = messageList.get(position).getStatus();
                MyCustomDialog dialog = new MyCustomDialog();

                Bundle args = new Bundle();
                args.putString("message",message);
                args.putString("number",number);
                args.putString("status",status);

                dialog.setArguments(args);
                dialog.setTargetFragment(InboxFragment.this, 1);
                dialog.show(getFragmentManager(), "MyCustomDialog");


            }
        });
    }

    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            messageList.remove(viewHolder.getAdapterPosition());
            mAdapter.notifyDataSetChanged();
        }
    };

    private void ItemClicked(int position, String s) {

        String status = messageList.get(position).getStatus();
        messageList.get(position).changeMessage(s);
        //mAdapter.notifyItemChanged(position);
    }

    public static void insertMessageIntoList(String getNewMessage, String getNewPhoneNo, String getNewDate, String getNewStatus){
        int position = 0;
        statciMessageList.add(position,new MessageItem(getNewMessage,getNewPhoneNo,getNewDate,getNewStatus));
        staticAdapter.notifyDataSetChanged();
        getNewMessage = "";
        getNewPhoneNo = "";
        getNewDate = "";
        getNewStatus = "";

    }


}
