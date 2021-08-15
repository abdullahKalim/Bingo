package com.example.bingo_z;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameActivity extends AppCompatActivity {
ValueEventListener chatListner;
ValueEventListener numberListner;
TextView showChat;
TextView showNumber;
boolean msgSent=false;
    LinearLayout linearLayout;
public DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        showChat=findViewById(R.id.showChat);
        showNumber=findViewById(R.id.showNumber);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dref = database.getReference("Game Data");
        drawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        final String[] userlist = {""};
        String code = intent.getExtras().getString("Code");
        DatabaseReference myGrp=dref.child("Group " + code).getRef();
        myGrp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userlist[0] = snapshot.child("User").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        int ar[] = intent.getExtras().getIntArray("table");
        int tab[][] = new int[5][5];
        int l = 0;
        for (int x = 0; x <= 4; x++) {
            for (int y = 0; y <= 4; y++) {
                tab[x][y] = ar[l];
                l++;
            }
        }
       chatListner= myGrp.child("Chats").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               String chat=snapshot.getValue().toString();
               addChat(chat);

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
        numberListner=myGrp.child("Bingo Number").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String number=snapshot.getValue().toString();
                addNumber(number);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ImageButton sendChat=findViewById(R.id.sent);
        Button sendNumber=findViewById(R.id.sendNumber);
        Button openChats=findViewById(R.id.OpenChat);
        EditText ch=findViewById(R.id.ChatEdit);
        EditText num=findViewById(R.id.editNumber);
        openChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });
        linearLayout=findViewById(R.id.chats);
        sendChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = ch.getText().toString();
                if (!s.equals("")) {
                    TextView textView = new TextView(getBaseContext());
                    textView.setTextColor(getResources().getColor(R.color.black));
                    textView.setBackgroundColor(getResources().getColor(R.color.chatText));
                    textView.setPadding(20, 20, 20, 20);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25f);
                    textView.setBackground(getResources().getDrawable(R.drawable.ic_chat_sent));
                    textView.setText(s);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    params.gravity = Gravity.END;
                    params.setMarginEnd(10);
                    textView.setLayoutParams(params);
                    linearLayout.addView(textView, linearLayout.getChildCount());
                    msgSent=true;
                    myGrp.child("Chats").setValue(ch.getText().toString());
                    ch.setText("");
                }
            }
        });
        sendNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myGrp.child("Bingo Number").setValue(num.getText().toString());
            }
        });


    }
    public void addChat(String chat)
    {
        if(chat.charAt(0)!='{'&& !msgSent)
        {
            TextView textView = new TextView(getBaseContext());
            textView.setTextColor(getResources().getColor(R.color.black));
            textView.setBackgroundColor(getResources().getColor(R.color.chatText));
            textView.setPadding(20,20,20,20);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,25f);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setBackground(getResources().getDrawable(R.drawable.ic_chat_recieved));
            textView.setText(chat);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.START;
            params.setMarginStart(10);
            textView.setLayoutParams(params);
            linearLayout.addView(textView, linearLayout.getChildCount());
            msgSent=false;


        }}
    public void addNumber(String number)
    {
        showNumber.append(number+"\n");
    }


}