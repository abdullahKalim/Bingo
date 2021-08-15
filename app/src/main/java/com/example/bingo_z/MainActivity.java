package com.example.bingo_z;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int i = 1;
    int p = 0;
    int id = 0;
    String  joincod="285";
    DatabaseReference mygrp=null;
    boolean GrpCreater=false;
    String pre;
    int ar[][]=new int[5][5];
    ValueEventListener valueEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dref = database.getReference("Game Data");

        EditText editText = findViewById(R.id.code);
        Button button = findViewById(R.id.joinButton);
        EditText name = findViewById(R.id.name);
        EditText code = findViewById(R.id.code);
        Button group = findViewById(R.id.createGroup);
        TextView textCode = findViewById(R.id.code);
        Button join = findViewById(R.id.join);
        Button start = findViewById(R.id.start);
        Button button1 = findViewById(R.id.button_1);
        button1.setOnClickListener(this);
        Button button2 = findViewById(R.id.button_2);
        button2.setOnClickListener(this);

        Button button3 = findViewById(R.id.button_3);
        button3.setOnClickListener(this);

        Button button4 = findViewById(R.id.button_4);
        button4.setOnClickListener(this);

        Button button5 = findViewById(R.id.button_5);
        button5.setOnClickListener(this);

        Button button6 = findViewById(R.id.button_6);
        button6.setOnClickListener(this);

        Button button7 = findViewById(R.id.button_7);
        button7.setOnClickListener(this);

        Button button8 = findViewById(R.id.button_8);
        button8.setOnClickListener(this);
        Button button9 = findViewById(R.id.button_9);
        button9.setOnClickListener(this);

        Button button10 = findViewById(R.id.button_10);
        button10.setOnClickListener(this);

        Button button11 = findViewById(R.id.button_11);
        button11.setOnClickListener(this);

        Button button12 = findViewById(R.id.button_12);
        button12.setOnClickListener(this);

        Button button13 = findViewById(R.id.button_13);
        button13.setOnClickListener(this);

        Button button14 = findViewById(R.id.button_14);
        button14.setOnClickListener(this);

        Button button15 = findViewById(R.id.button_15);
        button15.setOnClickListener(this);

        Button button16 = findViewById(R.id.button_16);
        button16.setOnClickListener(this);

        Button button17 = findViewById(R.id.button_17);
        button17.setOnClickListener(this);
        Button button18 = findViewById(R.id.button_18);
        button18.setOnClickListener(this);
        Button button19 = findViewById(R.id.button_19);
        button19.setOnClickListener(this);
        Button button20 = findViewById(R.id.button_20);
        button20.setOnClickListener(this);
        Button button21 = findViewById(R.id.button_21);
        button21.setOnClickListener(this);
        Button button22 = findViewById(R.id.button_22);
        button22.setOnClickListener(this);
        Button button23 = findViewById(R.id.button_23);
        button23.setOnClickListener(this);
        Button button24 = findViewById(R.id.button_24);
        button24.setOnClickListener(this);
        Button button25 = findViewById(R.id.button_25);
        button25.setOnClickListener(this);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setVisibility(View.VISIBLE);
                join.setVisibility(View.VISIBLE);
                code.setVisibility(View.VISIBLE);
            }
        });
        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setVisibility(View.VISIBLE);
                start.setVisibility(View.VISIBLE);

            }
        });
        Button gameStart = findViewById(R.id.GameStart);
        TextView status = findViewById(R.id.status);
        ConvertToContent convert = new ConvertToContent();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                join.setVisibility(View.GONE);
                code.setVisibility(View.GONE);
                start.setVisibility(View.GONE);
                textCode.setVisibility(View.GONE);
                button.setVisibility(View.GONE);
                name.setVisibility(View.GONE);
                group.setVisibility(View.GONE);
                GridLayout gl = findViewById(R.id.buttons);
                gameStart.setVisibility(View.VISIBLE);
                gl.setVisibility(View.VISIBLE);
                TextView textView = findViewById(R.id.joinCode);
                GrpCreater = true;
                Task<DataSnapshot> grp = dref.child("Code").get();
                grp.addOnCompleteListener(task -> {
                    String currentCode = task.getResult().getValue().toString();
                    int currentc = Integer.parseInt(currentCode);
                    currentc = currentc + 5;
                    String cc = String.valueOf(currentc);
                    textCode.setText(cc);
                    joincod=cc;
                    valueEventListener=dref.child("Group " + joincod).child("User").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            changeList(snapshot);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    ContentValues cv = new ContentValues();
                    cv.put("Players", 1);
                    mygrp=dref.child("Group " + cc).getRef();
                    cv.put("User1", name.getText().toString());
                    dref.child("Group " + cc).push().setValue("New Group");
                    dref.child("Group " + cc).child("User").push().setValue(cv.toString());
                    dref.child("Group " + cc).child("Bingo Number").push().setValue("");
                    dref.child("Group " + cc).child("Chats").push().setValue("");
                    dref.child("Group " + cc).child("Winner").push().setValue("");
                    dref.child("Code").setValue(cc);

                });
            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String joiningCode = editText.getText().toString();
                joincod=joiningCode;
                String myName = name.getText().toString();
                join.setVisibility(View.GONE);
                code.setVisibility(View.GONE);
                start.setVisibility(View.GONE);
                textCode.setVisibility(View.GONE);
                button.setVisibility(View.GONE);
                name.setVisibility(View.GONE);
                group.setVisibility(View.GONE);
                GridLayout gl = findViewById(R.id.buttons);
                gl.setVisibility(View.VISIBLE);
                status.setVisibility(View.VISIBLE);
                Task<DataSnapshot> snapshotTask = dref.get();
                snapshotTask.addOnCompleteListener(task -> {
                    String val = "";
                    valueEventListener=dref.child("Group " + joincod).child("User").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            changeList(snapshot);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    dref.child("Group " + joincod).child("Winner").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            startAct();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Iterator<DataSnapshot> data = task.getResult().getChildren().iterator();
                    while (data.hasNext()) {
                        DataSnapshot dataSnapshot = data.next();
                        if (dataSnapshot.getKey().equals("Group " + joiningCode)) {
                            mygrp = dataSnapshot.getRef();
                            joincod =joiningCode;
                            pre = dataSnapshot.child("User").getValue().toString();
                            ContentValues cv = convert.ToContent(pre);
                            String no = cv.get("Players").toString();
                            int noPlayers = Integer.parseInt(no);
                            cv.put("User" + (noPlayers + 1), myName);
                            cv.put("Players", noPlayers + 1);
                            mygrp.child("User").setValue(cv.toString());
                        }

                    }
                    if (mygrp == null) {
                        textCode.setText("no such group exist");
                    } else {
                        textCode.setText(mygrp.getKey());
                    }
                });

            }

        });

            gameStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dref.child("Group " + joincod).child("Winner").setValue("Game Started");
                    startAct();
                }
            });
    }
public void changeList(DataSnapshot data)
{
    ConvertToContent convert=new ConvertToContent();
    TextView list=findViewById(R.id.status);
    list.setVisibility(View.VISIBLE);
    String contentValues = data.getValue().toString();
    ContentValues cv = convert.ToContent(contentValues);
    int noPlayers = cv.getAsInteger("Players");
    String playersName[] = new String[noPlayers];
    String ply = "";
    for (int x = 0; x < noPlayers; x++) {
        playersName[x] = cv.getAsString(("User" + (x + 1)));
        ply = ply + playersName[x] + "\n";
    }
    list.setText(ply);
}
    public void startAct()
    {
        if(p==25) {
            mygrp.child("User").removeEventListener(valueEventListener);
            Intent i = new Intent(getBaseContext(), GameActivity.class);
            i.putExtra("Code", joincod);
            int tempArray[]=new int[25];
            int q=0;
            for(int x=0;x<=4;x++)
            {
                for (int y=0;y<=4;y++)
                {
                    tempArray[q]=ar[x][y];
                    q++;
                }

            }
            i.putExtra("table",tempArray);
            startActivity(i);
        }
    }
    @Override
    public void onClick(View v) {
        String num[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"};
        if (p <= 24) {
            int row=0,column=0;
            switch (v.getId())
            {
                case R.id.button_1:
                {
                    row=0;
                    column=0;
                    break;
                }
                case R.id.button_2:
                {
                    row=0;
                    column=1;
                    break;
                }
                case R.id.button_3:
                {
                    row=0;
                    column=2;
                    break;
                }
                case R.id.button_4:
                {
                    row=0;
                    column=3;
                    break;
                }
                case R.id.button_5:
                {
                    row=0;
                    column=4;
                    break;
                }

                case R.id.button_6:
                {
                    row=1;
                    column=0;
                    break;
                }

                case R.id.button_7:
                {
                    row=1;
                    column=1;
                    break;
                }

                case R.id.button_8:
                {
                    row=1;
                    column=2;
                    break;
                }

                case R.id.button_9:
                {
                    row=1;
                    column=3;
                    break;
                }

                case R.id.button_10:
                {
                    row=1;
                    column=4;
                    break;
                }

                case R.id.button_11:
                {
                    row=2;
                    column=0;
                    break;
                }
                case R.id.button_12:
                {
                    row=2;
                    column=1;
                    break;
                }
                case R.id.button_13:
                {
                    row=2;
                    column=2;
                    break;
                }
                case R.id.button_14:
                {
                    row=2;
                    column=3;
                    break;
                }
                case R.id.button_15:
                {
                    row=2;
                    column=4;
                    break;
                }

                case R.id.button_16:
                {
                    row=3;
                    column=0;
                    break;
                }

                case R.id.button_17:
                {
                    row=3;
                    column=1;
                    break;
                }

                case R.id.button_18:
                {
                    row=3;
                    column=2;
                    break;
                }

                case R.id.button_19:
                {
                    row=3;
                    column=3;
                    break;
                }

                case R.id.button_20:
                {
                    row=3;
                    column=4;
                    break;
                }
                case R.id.button_21:
                {
                    row=4;
                    column=0;
                    break;
                }
                case R.id.button_22:
                {
                    row=4;
                    column=1;
                    break;
                }
                case R.id.button_23:
                {
                    row=4;
                    column=2;
                    break;
                }
                case R.id.button_24:
                {
                    row=4;
                    column=3;
                    break;
                }
                case R.id.button_25:
                {
                    row=4;
                    column=4;
                    break;
                }



            }
            Button b = findViewById(v.getId());
            if (b.getText().equals("")) {
                b.setText(num[p]);
                ar[row][column]=Integer.parseInt(num[p]);
                p++;
                id = v.getId();
            }
        }
    }


}
