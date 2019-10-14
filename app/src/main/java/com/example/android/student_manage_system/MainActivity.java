package com.example.android.student_manage_system;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText ename, eroll_no, emarks;
    Button add, view, viewall, Show1, delete, modify;
    SQLiteDatabase db;//database is stored here


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ename = (EditText) findViewById(R.id.editText7);
        eroll_no = (EditText) findViewById(R.id.editText6);
        emarks = (EditText) findViewById(R.id.editText8);
        add = (Button) findViewById(R.id.button);
        view = (Button) findViewById(R.id.button4);
        viewall = (Button) findViewById(R.id.button5);
        Show1 = (Button) findViewById(R.id.button6);
        delete = (Button) findViewById(R.id.button2);
        modify = (Button) findViewById(R.id.button3);

        db = openOrCreateDatabase("Student_manage_system", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno INTEGER,name VARCHAR,marks INTEGER);");

        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (eroll_no.getText().toString().trim().length() == 0 ||
                        ename.getText().toString().trim().length() == 0 ||
                        emarks.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Please Enter all the values");
                    return;
                }
                db.execSQL("INSERT INTO student VALUES( '" + eroll_no.getText() + "','" + ename.getText() + "','" +
                        emarks.getText() + "');");
                showMessage("Success", "Record added successfully");
                clearText();

            }
        });


		delete.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View v) {
            if(eroll_no.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter Rollno");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+eroll_no.getText()+"'", null);
            if(c.moveToFirst())
            {
                db.execSQL("DELETE FROM student WHERE rollno='"+eroll_no.getText()+"'");
                showMessage("Success", "Record Deleted");
            }
            else
            {
                showMessage("Error", "Invalid Rollno");
            }
            clearText();
        }
    });
		modify.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View v) {

            if(eroll_no.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter Rollno");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+eroll_no.getText()+"'", null);
            if(c.moveToFirst())
            {
                db.execSQL("UPDATE student SET name='"+ename.getText()+"',marks='"+emarks.getText()+
                        "' WHERE rollno='"+eroll_no.getText()+"'");
                showMessage("Success", "Record Modified");
            }
            else
            {
                showMessage("Error", "Invalid Rollno");
            }
            clearText();
        }
    });
		view.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View v) {
            if(eroll_no.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter Rollno");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+eroll_no.getText()+"'", null);
            if(c.moveToFirst())
            {
                ename.setText(c.getString(1));
                emarks.setText(c.getString(2));
            }
            else
            {
                showMessage("Error", "Invalid Rollno");
                clearText();
            }
        }
    });
		viewall.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View v) {
            Cursor c=db.rawQuery("SELECT * FROM student", null);
            if(c.getCount()==0)
            {
                showMessage("Error", "No records found");
                return;
            }
            StringBuffer buffer=new StringBuffer();
            while(c.moveToNext())
            {
                buffer.append("Rollno: "+c.getString(0)+"\n");
                buffer.append("Name: "+c.getString(1)+"\n");
                buffer.append("Marks: "+c.getString(2)+"\n\n");
            }
            showMessage("Student Details", buffer.toString());
        }
    });
		Show1.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View v) {
            showMessage("Student Management Application", "Developed By Ishneet Kaur");
        }
    });

}
    public void showMessage(String title,String message)
    {
        Builder builder=new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText()
    {
        eroll_no.setText("");
        ename.setText("");
        emarks.setText("");
        eroll_no.requestFocus();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.student_main, menu);
        return true;
    }

}
