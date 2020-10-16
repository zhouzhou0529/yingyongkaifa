package com.example.shiyan2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private  String TAG1;
    WordsDBHelper mDbHelper;
    WordsDBHelper helper;
    private SQLiteDatabase dbread;
    private ListView listview;
    private SimpleAdapter simp_adapter;
    private List<Map<String, Object>> dataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //为ListView注册上下文菜单

        listview = (ListView) findViewById(R.id.listview);
        registerForContextMenu(listview);
        dataList = new ArrayList<Map<String, Object>>();


        //创建SQLiteOpenHelper对象，注意第一次运行时，此时数据库并没有被创建
        mDbHelper = new WordsDBHelper(this);
        dbread = mDbHelper.getReadableDatabase();
        setWordsListView();
        //在列表显示全部单词
        Button buttonadd = (Button) findViewById(R.id.button1);
        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertDialog();
            }
        });
        Button buttoncha = (Button) findViewById(R.id.button2);
        buttoncha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchDialog();
            }
        });
        Button buttonxian = (Button) findViewById(R.id.button3);
        buttonxian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWordsListView();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDbHelper.close();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bangzu,menu); //通过getMenuInflater()方法得到MenuInflater对象，再调用它的inflate()方法就可以给当前活动创建菜单了，第一个参数：用于指定我们通过哪一个资源文件来创建菜单；第二个参数：用于指定我们的菜单项将添加到哪一个Menu对象当中。
        return true; // true：允许创建的菜单显示出来，false：创建的菜单将无法显示。
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.bagnzhu:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                final View viewDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.bangzhu, null, false);
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
            default:
                break;
        }

        return true;
    }
    private void setWordsListView(){
         SQLiteDatabase db;
        db = mDbHelper.getReadableDatabase();
        String PRODUCT="product";
        String ID="id";
        String PRICE="price";
        String CONFIGURATION="configuration";
        Cursor cursor = db.rawQuery("select * from words",null);
        cursor.moveToFirst();    //移动到第一行
        int size=cursor.getCount();
        String id[] = new String[size];
        String word[] = new String[size];
        String meaning[] = new String[size];
        String sample[] = new String[size];
        for(int i=0;i<size;i++){
            id[i] = cursor.getString(cursor.getColumnIndex("_id"));
            word[i] = cursor.getString(cursor.getColumnIndex("word"));
            meaning[i] = cursor.getString(cursor.getColumnIndex("meaning"));
            sample[i] = cursor.getString(cursor.getColumnIndex("sample"));
            cursor.moveToNext();
        }
        List<Map<String,Object>> items=new ArrayList<Map<String,Object>>();
        Log.v("tag",size+"");
        for(int k=0;k<size;k++) {
            Map<String,Object> item=new HashMap<String,Object>();
            item.put(ID, id[k]);
            item.put(PRODUCT, word[k]);
            item.put(PRICE, meaning[k]);
            item.put(CONFIGURATION, sample[k]);
            Log.v("tag",item+"");
            items.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,items, R.layout.item,
                        new String[]{ID,PRODUCT,PRICE,CONFIGURATION},
                        new int[]{R.id.textId,R.id.textViewWord, R.id.textViewMeaning, R.id.textViewSample});

                ListView list = (ListView) findViewById(R.id.listview);
                list.setAdapter(adapter);
            }
    private void SearchDialog (){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View viewDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.serch, null, false);
        builder.setTitle("查询单词")
                .setView(viewDialog)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText editText =(EditText) viewDialog.findViewById(R.id.searchWord);
                        String txtSearchWord=editText.getText().toString();
                        setWordsListView2(txtSearchWord);
                        //既可以使用Sql语句查询，也可以使用方法查询

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.create().show();
    }
            private void InsertDialog () {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                final View viewDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.insert, null, false);
                //从布局文件生成

                builder.setTitle("新增单词")
                        .setView(viewDialog)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String strWord = ((EditText) viewDialog.findViewById(R.id.txtWord)).getText().toString();
                                String strMeaning = ((EditText) viewDialog.findViewById(R.id.txtMeaning)).getText().toString();
                                String strSample = ((EditText) viewDialog.findViewById(R.id.txtSample)).getText().toString();
                                Insert(strWord, strMeaning, strSample);
                                setWordsListView();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.create().show();
            }

            private void Insert (String strWord, String strMeaning, String strSample){

                //Gets the data repository in write mode*/
                SQLiteDatabase db = mDbHelper.getWritableDatabase();

                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put(Words.Word.COLUMN_NAME_WORD, strWord);
                values.put(Words.Word.COLUMN_NAME_MEANING, strMeaning);
                values.put(Words.Word.COLUMN_NAME_SAMPLE, strSample);

                // Insert the new row, returning the primary key value of the new row
                long newRowId;
                newRowId = db.insert(
                        Words.Word.TABLE_NAME,
                        null,
                        values);
            }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        TextView textId=null;
        TextView textWord=null;
        TextView textMeaning=null;
        TextView textSample=null;
        AdapterView.AdapterContextMenuInfo info=null;
        View itemView=null;

        switch (item.getItemId()){
            case R.id.action_delete:
                //删除单词
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                itemView = info.targetView;
                textId = (TextView) itemView.findViewById(R.id.textId);
                String strId2 = textId.getText().toString();
                Log.v("tag",strId2+"1");
                if(textId!=null) {
                    String strId = textId.getText().toString();
                    DeleteDialog(strId);
                }
                break;
            case R.id.action_update:
                //修改单词
                info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                itemView=info.targetView;
                textId =(TextView)itemView.findViewById(R.id.textId);
                textWord =(TextView)itemView.findViewById(R.id.textViewWord);
                textMeaning =(TextView)itemView.findViewById(R.id.textViewMeaning);
                textSample =(TextView)itemView.findViewById(R.id.textViewSample);
                if(textId!=null && textWord!=null && textMeaning!=null && textSample!=null){
                    String strId=textId.getText().toString();
                    String strWord=textWord.getText().toString();
                    String strMeaning=textMeaning.getText().toString();
                    String strSample=textSample.getText().toString();
                    UpdateDialog(strId, strWord, strMeaning, strSample);
                }
                break;

        }
        return true;
    }
    private void UpdateDialog(final String strId, final String strWord, final String strMeaning, final String strSample) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View viewDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.insert, null, false);
        ((EditText)viewDialog.findViewById(R.id.txtWord)).setText(strWord);
        ((EditText)viewDialog.findViewById(R.id.txtMeaning)).setText(strMeaning);
        ((EditText)viewDialog.findViewById(R.id.txtSample)).setText(strSample);
        new AlertDialog.Builder(this)
                .setTitle("修改单词")//标题
                .setView(viewDialog)//设置视图
                //确定按钮及其动作
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String strNewWord = ((EditText) viewDialog.findViewById(R.id.txtWord)).getText().toString();
                        String strNewMeaning = ((EditText) viewDialog.findViewById(R.id.txtMeaning)).getText().toString();
                        String strNewSample = ((EditText) viewDialog.findViewById(R.id.txtSample)).getText().toString();

                        //既可以使用Sql语句更新，也可以使用使用update方法更新
                        UpdateUseSql(strId, strNewWord, strNewMeaning, strNewSample);
                        //  Update(strId, strNewWord, strNewMeaning, strNewSample);
                        setWordsListView();
                    }
                })
                //取消按钮及其动作
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create()//创建对话框
                .show();//显示对话框
    }
    private void UpdateUseSql(String strId,String strWord, String strMeaning, String strSample) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String sql="update words set word=?,meaning=?,sample=? where _id=?";
        db.execSQL(sql, new String[]{strWord, strMeaning, strSample,strId});
    }
        private void DeleteDialog(final String strId){
            new AlertDialog.Builder(this).setTitle("删除单词").setMessage("是否真的删除单词?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //既可以使用Sql语句删除，也可以使用使用delete方法删除
                    DeleteUseSql(strId);
                    //Delete(strId);
                    setWordsListView();
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).create().show();
        }
    private void DeleteUseSql(String strId) {
        String sql="delete from words where _id='"+strId+"'";

        //Gets the data repository in write mode*/
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        db.execSQL(sql);
    }
    private void setWordsListView2(String string){
        SQLiteDatabase db;
        db = mDbHelper.getReadableDatabase();
        String PRODUCT="product";
        String ID="id";
        String PRICE="price";
        String CONFIGURATION="configuration";
        String sql="select * from words where word like ? order by word desc";
        Cursor c=db.rawQuery(sql,new String[]{"%"+string+"%"});
        c.moveToFirst();    //移动到第一行
        int size=c.getCount();
        String id[] = new String[size];
        String word[] = new String[size];
        String meaning[] = new String[size];
        String sample[] = new String[size];
        for(int i=0;i<size;i++){
            id[i] = c.getString(c.getColumnIndex("_id"));
            word[i] = c.getString(c.getColumnIndex("word"));
            meaning[i] = c.getString(c.getColumnIndex("meaning"));
            sample[i] = c.getString(c.getColumnIndex("sample"));
            c.moveToNext();
        }
        List<Map<String,Object>> items=new ArrayList<Map<String,Object>>();
        for(int k=0;k<size;k++) {
            Map<String,Object> item=new HashMap<String,Object>();
            item.put(ID, id[k]);
            item.put(PRODUCT, word[k]);
            item.put(PRICE, meaning[k]);
            item.put(CONFIGURATION, sample[k]);
            items.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,items, R.layout.item,
                new String[]{PRODUCT,PRICE,CONFIGURATION},
                new int[]{R.id.textViewWord, R.id.textViewMeaning, R.id.textViewSample});
        ListView list = (ListView) findViewById(R.id.listview);
        list.setAdapter(adapter);
    }
}