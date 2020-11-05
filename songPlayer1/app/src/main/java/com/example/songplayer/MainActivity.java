package com.example.songplayer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class MainActivity extends AppCompatActivity {
    private ListView lview;
    private  MediaPlayer m ;
    private SeekBar bar;
    private TextView playingsong;
    private int[] flag = {0};
    private int[] sf={0};
    private int[] f={0};
    private int[] sounds={R.raw.aiyaotao,R.raw.guangnianzhiwai,R.raw.haungzhongren,R.raw.renlongchuanshuo};
    private int[] index={1,2,3,4};
    private String[] id={"1","2","3","4"};
    private String[] songs={"爱要逃","光年之外","黄种人","人龙传说"};
    private int[] num={0,0,0,0};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lview=(ListView)findViewById(R.id.list);
        playingsong=(TextView)findViewById(R.id.song);
        registerForContextMenu(lview);
        m=MediaPlayer.create(this,R.raw.aiyaotao);
        setLview(getAll());
        playingsong.setText("正在播放：爱要逃");
        sf[0]=1;
        bar = (SeekBar) findViewById(R.id.seek);
        bar.setMax(m.getDuration());
        new  myThread().start();
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    m.seekTo(progress);
                }
            }


            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textId =(TextView)view.findViewById(R.id.songid);
                String strid= textId.getText().toString();
                int a= Integer.parseInt(strid);
                if(a==3){
                    m.reset();
                    m=MediaPlayer.create(MainActivity.this,sounds[2]);
                    playingsong.setText("正在播放：黄种人");
                    m.start();
                    bar.setMax(m.getDuration());
                    sf[0]=3;
                }else if(a==2){
                    m.reset();
                    m=MediaPlayer.create(MainActivity.this,sounds[1]);
                    playingsong.setText("正在播放：光年之外");
                    m.start();
                    bar.setMax(m.getDuration());
                    sf[0]=2;
                }else if(a==4){
                    m.reset();
                    m=MediaPlayer.create(MainActivity.this,sounds[3]);
                    playingsong.setText("正在播放：人龙传说");
                    m.start();
                    bar.setMax(m.getDuration());
                    sf[0]=4;
                } else {
                    m.reset();
                    m=MediaPlayer.create(MainActivity.this,sounds[0]);
                    playingsong.setText("正在播放：爱要逃");
                    m.start();
                    bar.setMax(m.getDuration());
                    sf[0]=1;
                }
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.sp);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.items, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);
        spinner.setSelection(flag[0]);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    f[0]=0;
                }else{
                    f[0]=1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button shang=(Button)findViewById(R.id.shang);
        Button bofang=(Button)findViewById(R.id.bofang);
        Button xia=(Button)findViewById(R.id.next);
        //上一首
        shang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sf[0]>1){
                    for(int i=sf[0]-2;i>=0;i--){
                        if(index[i]>0){
                            sf[0]=i+1;
                            m.reset();
                            m=MediaPlayer.create(MainActivity.this,sounds[i]);
                            playingsong.setText("正在播放："+songs[i]);
                            m.start();
                            bar.setMax(m.getDuration());
                            break;
                        }
                    }
                }else {
                    sf[0]=4;
                    m.reset();
                    m=MediaPlayer.create(MainActivity.this,sounds[3]);
                    playingsong.setText("正在播放：人龙传说");
                    m.start();
                    bar.setMax(m.getDuration());
                }
            }
        });

        //播放/暂停
        bofang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(m!=null&&!m.isPlaying()){
                    m.start();
                }else{
                    m.pause();
                }
            }
        });

        //下一首
        xia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(sf[0]<4){
                   for(int i=sf[0];i<4;i++){
                       if(index[i]>0){
                           sf[0]=i+1;
                           m.reset();
                           m=MediaPlayer.create(MainActivity.this,sounds[i]);
                           playingsong.setText("正在播放："+songs[i]);
                           m.start();
                           bar.setMax(m.getDuration());
                           break;
                       }
                   }
               }else {
                   sf[0]=1;
                   m.reset();
                   m=MediaPlayer.create(MainActivity.this,R.raw.aiyaotao);
                   playingsong.setText("正在播放：爱要逃");
                   m.start();
                   bar.setMax(m.getDuration());
               }
            }
        });
        m.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            public void onCompletion(MediaPlayer mp) {
              autoplay();
            }
        });



        m.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return true;
            }
        });

        Button btn=(Button)findViewById(R.id.jia);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit=(EditText)findViewById(R.id.edit);

                if(edit.getText() == null){

                }else{
                    String s=edit.getText().toString();
                    int b=Integer.parseInt(s);
                    index[b-1]=b;
                    setLview(getAll());
                    num[b-1]=0;
                    TextView v1=(TextView)findViewById(R.id.nosong);
                    String str="";
                    for(int j=0;j<num.length;j++){
                        if(num[j]==1){
                            str=str+"   "+"1.爱要逃";
                        }else if(num[j]==2){
                            str=str+"   "+"2.光年之外";
                        }else if(num[j]==3){
                            str=str+"   "+"3.黄种人";
                        }else if(num[j]==4){
                            str=str+"   "+"4.人龙传说";
                        }
                    }
                    v1.setText(str);
                }

            }
        });

    }
    public ArrayList<Map<String, Object>> getAll(){
        ArrayList<Map<String, Object>> list=new ArrayList<>();
        for(int i=0;i<index.length;i++) {
            if(index[i]>0){
                Map<String,Object> item=new HashMap<String,Object>();
                item.put("id", id[i]);
                item.put("songs", songs[i]);
                list.add(item);
            }
        }
        return list;
    }
    private void setLview(ArrayList<Map<String, Object>> items){
        SimpleAdapter adapter=new SimpleAdapter(this,items,R.layout.item,
                new String[]{"id","songs"},
                new int[]{R.id.songid,R.id.songname});
        lview.setAdapter(adapter);
    }




    //删除音乐
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.del, menu);
    }
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info=null;
        View itemView=null;
        TextView textId=null;
        TextView text=null;
        switch (item.getItemId()){
            //删除
            case R.id.del:
                info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                itemView=info.targetView;
                textId =(TextView)itemView.findViewById(R.id.songid);
                text=(TextView)itemView.findViewById(R.id.songname);
                final String strid= textId.getText().toString();
                final String t= text.getText().toString();
                final int a= Integer.parseInt(strid);
                new AlertDialog.Builder(this)
                        .setTitle("删除歌曲").setMessage("是否真的删除歌曲?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                for(int j=0;j<index.length;j++){
                                    if(index[j]==a){
                                        index[j]=0;
                                    }
                                }
                                setLview(getAll());
                                num[a-1]=a;
                                TextView v=(TextView)findViewById(R.id.nosong);
                                String str="";
                                for(int j=0;j<num.length;j++){
                                    if(num[j]==1){
                                        str=str+"   "+"1.爱要逃";
                                    }else if(num[j]==2){
                                        str=str+"   "+"2.光年之外";
                                    }else if(num[j]==3){
                                        str=str+"   "+"3.黄种人";
                                    }else if(num[j]==4){
                                        str=str+"   "+"4.人龙传说";
                                    }
                                }
                                v.setText(str);
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create().show();

                break;

        }
        return true;
    }
    protected void onDestroy() {
        super.onDestroy();
        if (m != null) {
            m.stop();
            m.release();
        }

    }
    public void autoplay(){
        if(f[0]==0){
            if(sf[0]<4){
                for(int i=sf[0];i<4;i++){
                    if(index[i]>0){
                        sf[0]=i+1;
                        m.reset();
                        m=MediaPlayer.create(MainActivity.this,sounds[i]);
                        playingsong.setText("正在播放："+songs[i]);
                        m.start();
                        bar.setMax(m.getDuration());
                        break;
                    }
                }
            }else {
                sf[0]=1;
                m.reset();
                m=MediaPlayer.create(MainActivity.this,R.raw.aiyaotao);
                playingsong.setText("正在播放：爱要逃");
                m.start();
                bar.setMax(m.getDuration());
            }
        }else{
            int a= (int) (1+Math.random()*4);
            int[] b=new int[4];
            for(int i=0;i<4;i++){
                if(index[i]>0&&index[i]!=sf[0]){

                }
            }
        }

    }

    class  myThread extends Thread{
        @Override
        public void run() {
            super.run();
            //判断当前播放位置是否小于总时长
            while (bar.getProgress()<=bar.getMax()) {
                //设置进度条当前位置为音频播放位置
                bar.setProgress(m.getCurrentPosition());
            }
        }
    }
}