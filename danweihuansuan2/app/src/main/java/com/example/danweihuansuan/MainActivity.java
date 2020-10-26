package com.example.danweihuansuan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spinner = (Spinner) findViewById(R.id.s1);
        Spinner spinner1 = (Spinner) findViewById(R.id.s2);
        Spinner spinner2 = (Spinner) findViewById(R.id.s3);
        Spinner spinner3 = (Spinner) findViewById(R.id.s4);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.items2, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter1);
        spinner3.setAdapter(adapter1);
        EditText editText2=(EditText)findViewById(R.id.edtext2);
        TextView textView=(TextView)findViewById(R.id.text1);
        TextView textView2=(TextView)findViewById(R.id.text2);
        Button button1=(Button)findViewById(R.id.button1);
        Button button2=(Button)findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double k=0.1,f=0.1,sum=0;
                Spinner spinner = (Spinner) findViewById(R.id.s1);
                Spinner spinner1 = (Spinner) findViewById(R.id.s2);
                EditText editText=(EditText)findViewById(R.id.edtext1);
                TextView textView=(TextView)findViewById(R.id.text1);
                double age = Integer.parseInt(editText.getText().toString());
                String s1 = spinner.getSelectedItem().toString();
                String s2 = spinner.getSelectedItem().toString();
                if(s1.equals("米"))
                {
                    k=1;
                }

                if(s2=="米")
                {
                    f=0.5;
                }
                if(s2.equals("分米"))
                {
                    f=0.2;
                }

                sum=age*k/f;
                textView.setText(String.valueOf(sum));

            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bangzu,menu); //通过getMenuInflater()方法得到MenuInflater对象，再调用它的inflate()方法就可以给当前活动创建菜单了，第一个参数：用于指定我们通过哪一个资源文件来创建菜单；第二个参数：用于指定我们的菜单项将添加到哪一个Menu对象当中。
        return true; // true：允许创建的菜单显示出来，false：创建的菜单将无法显示。
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.bagnzhu:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                final View viewDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.bangzu, null, false);
                builder.setTitle("帮助")
                        .setView(viewDialog)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //既可以使用Sql语句查询，也可以使用方法查询

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.create().show();
                break;
            case R.id.fanhui:

                break;

            default:
                break;
        }

        return true;
    }
}