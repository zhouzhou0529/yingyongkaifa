package com.example.myapplication0;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.BreakIterator;

import static java.lang.Long.toBinaryString;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button[] buttons = new Button[28];
    private int[] ids = new int[]{R.id.bt1,R.id.bt2,R.id.bt3,R.id.bt4,R.id.bt5,R.id.bt6,R.id.bt7,
            R.id.bt8,R.id.bt9,R.id.bt10,R.id.bt11,R.id.bt12,R.id.bt13,R.id.bt14,R.id.bt15,R.id.bt16,R.id.bt17,R.id.bt18,
            R.id.bt19,R.id.bt20,R.id.bt21,R.id.bt22,R.id.bt23,R.id.bt24,R.id.bt25,R.id.bt26,R.id.bt27,R.id.bt28
    };

    private TextView textView;
    private String expression = "";
    private boolean end = false;
    private int countOperate=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        for(int i=0;i<ids.length;i++){
            buttons[i] = (Button)findViewById(ids[i]);
            buttons[i].setOnClickListener(this);
        }
        textView = (TextView)findViewById(R.id.shuzhi);
    }
    //点击事件，强制排错
    public void onClick(View view) {
        int id = view.getId();
        Button button = (Button)view.findViewById(id);
        String current = button.getText().toString();
        if(end){ //如果上一次算式已经结束，则先清零
            expression = "";
            end = false;
        }
        if(current.equals("清除")){ //如果为CE则清零
            expression = "";
            countOperate=0;
        }else if(current.equals("后退")){ //如果点击退格
            if(expression.length()>1){ //算式长度大于1
                expression = expression.substring(0,expression.length()-1);//退一格
                int i = expression.length()-1;
                char tmp = expression.charAt(i); //获得最后一个字符
                char tmpFront = tmp;
                for(;i>=0;i--){ //向前搜索最近的 +-*/和.，并退出
                    tmpFront = expression.charAt(i);
                    if(tmpFront=='.'||tmpFront=='+'||tmpFront=='-'||tmpFront=='*'||tmpFront=='/'){
                        break;
                    }
                }
                //    Toast.makeText(this, "tmp = "+tmp, Toast.LENGTH_SHORT).show();
                if(tmp>='0'&&tmp<='9'){ //最后一个字符为数字，则识别数赋值为0
                    countOperate=0;
                }else if(tmp==tmpFront&&tmpFront!='.') countOperate=2; //如果为+-*/，赋值为2
                else if(tmpFront=='.') countOperate=1; //如果前面有小数点赋值为1
            }else if(expression.length()==1){
                expression = "";
            }
        }else if(current.equals(".")){
            if(expression.equals("")||countOperate==2){
                expression+="0"+current;
                countOperate = 1;  //小数点按过之后赋值为1
            }
            if(countOperate==0){
                expression+=".";
                countOperate = 1;
            }
        }else if(current.equals("+")||current.equals("-")||current.equals("*")||current.equals("/")){
            if(countOperate==0){
                expression+=current;
                countOperate = 2;  //  +-*/按过之后赋值为2
            }
        }else if(current.equals("=")){ //按下=时，计算结果并显示
            double result = (double) Math.round(count()*100)/100;
            expression+="="+result+"(结果只保留两位小数)";
            end = true; //此次计算结束
        }
        else if(current.equals("sin"))
        {
            EditText editText=(EditText)findViewById(R.id.shuzhi);
            double sum = Integer.parseInt(editText.getText().toString());
            double c=Math.sin(Math.toRadians(sum));
            expression="="+c;

        }
        else if(current.equals("cos"))
        {
            EditText editText=(EditText)findViewById(R.id.shuzhi);
            double sum = Integer.parseInt(editText.getText().toString());
            Log.v("tag",sum+"");
            Log.v("tag",Math.toRadians(sum)+"");
            double c=Math.cos(Math.toRadians(sum));
            expression="="+c;

        }
        else if(current.equals("tan"))
        {
            EditText editText=(EditText)findViewById(R.id.shuzhi);
            double sum = Integer.parseInt(editText.getText().toString());
            double c=Math.tan(Math.toRadians(sum));
            expression="="+c;

        }
        else if(current.equals("tan(x/2)"))
        {
            EditText editText=(EditText)findViewById(R.id.shuzhi);
            double sum = Integer.parseInt(editText.getText().toString())/2;
            double c=Math.tan(Math.toRadians(sum));
            expression="="+c;

        }
        else if(current.equals("长度"))
        {
            EditText editText=(EditText)findViewById(R.id.shuzhi);
            double sum = Integer.parseInt(editText.getText().toString());

expression=sum+"微米"+"="+sum/1000+"毫米"+"="+sum/10000*100+"厘米"+"="+sum/100000*100+"分米"+"="+sum/1000000+"米"+"="+sum/1000000000+"千米";

        }
        else if(current.equals("体积"))
        {
            EditText editText=(EditText)findViewById(R.id.shuzhi);
           double sum = Integer.parseInt(editText.getText().toString());
expression=sum+"立方毫米"+"="+sum/1000+"立方厘米"+"="+sum/1000000+"立方分米"+"="+sum/1000000000+"立方米"+"="+sum/1000000+"升";

        }
        else if(current.equals("10转2进制"))
        {
            EditText editText=(EditText)findViewById(R.id.shuzhi);
            long sum = Integer.parseInt(editText.getText().toString());

            expression="="+Long.toBinaryString(sum);
        }
        else if(current.equals("10转16进制"))
        {
            EditText editText=(EditText)findViewById(R.id.shuzhi);
            long sum = Integer.parseInt(editText.getText().toString());
            expression="="+Long.toHexString(sum);

        }
        else if(current.equals("2转10进制"))
        {
            EditText editText=(EditText)findViewById(R.id.shuzhi);
            int x=Integer.parseInt(editText.getText().toString());
            double sum=binaryToDecimal(x);
            expression="="+sum;

        }
        else if(current.equals("2转16进制"))
        {
            EditText editText=(EditText)findViewById(R.id.shuzhi);
            int x=Integer.parseInt(editText.getText().toString());
            long sum=binaryToDecimal(x);
            expression="="+Long.toHexString(sum);
        }


        else{//此处是当退格出现2+0时，用current的值替代0
            if(expression.length()>=1){
                char tmp1 = expression.charAt(expression.length()-1);
                if(tmp1=='0'&&expression.length()==1){
                    expression = expression.substring(0,expression.length()-1);
                }
                else if(tmp1=='0'&&expression.length()>1){
                    char tmp2 = expression.charAt(expression.length()-2);
                    if(tmp2=='+'||tmp2=='-'||tmp2=='*'||tmp2=='/'){
                        expression = expression.substring(0,expression.length()-1);
                    }
                }
            }
            expression+=current;
            if(countOperate==2||countOperate==1) countOperate=0;
        }
        //    Toast.makeText(this, "countOperate:"+countOperate, Toast.LENGTH_SHORT).show();
        textView.setText(expression); //显示出来
    }
    //计算逻辑，求expression表达式的值
    private double count(){
        double result=0;
        double tNum=1,lowNum=0.1,num=0;
        char tmp=0;
        int operate = 1; //识别+-*/，为+时为正数，为-时为负数，为×时为-2/2,为/时为3/-3;
        boolean point = false;
        for(int i=0;i<expression.length();i++){ //遍历表达式
            tmp = expression.charAt(i);
            if(tmp=='.'){ //因为可能出现小数，此处进行判断是否有小数出现
                point = true;
                lowNum = 0.1;
            }else if(tmp=='+'||tmp=='-'){
                if(operate!=3&&operate!=-3){ //此处判断通用，适用于+-*
                    tNum *= num;
                }else{ //计算/
                    tNum /= num;
                }
                //    Toast.makeText(this, "tNum = "+tNum, Toast.LENGTH_SHORT).show();
                if(operate<0){ //累加入最终的结果
                    result -= tNum;
                }else{
                    result += tNum;
                }
                operate = tmp=='+'?1:-1;
                num = 0;
                tNum = 1;
                point = false;
            }else if(tmp=='*'){
                if(operate!=3&&operate!=-3){
                    tNum *= num;
                }else{
                    tNum /= num;
                }
                operate = operate<0?-2:2;
                point = false;
                num = 0;
            }else if(tmp=='/'){
                if(operate!=3&&operate!=-3){
                    tNum *= num;
                }else{
                    tNum /= num;
                }
                operate = operate<0?-3:3;
                point = false;
                num = 0;
            }else{
                //读取expression中的每个数字，doube型
                if(!point){
                    num = num*10+tmp-'0';
                }else{
                    num += (tmp-'0')*lowNum;
                    lowNum*=0.1;
                }
            }
        }
        //循环遍历结束，计算最后一个运算符后面的数
        if(operate!=3&&operate!=-3){
            tNum *= num;
        }else{
            tNum /= num;
        }
        //    Toast.makeText(this, "tNum = "+tNum, Toast.LENGTH_SHORT).show();
        if(operate<0){
            result -= tNum;
        }else{
            result += tNum;
        }
        //返回最后的结果
        return result;
    }
    public static int binaryToDecimal(int binaryNumber) {

        int decimal = 0;
        int p = 0;
        while (true) {
            if (binaryNumber == 0) {
                break;
            } else {
                int temp = binaryNumber % 10;
                decimal += temp * Math.pow(2, p);
                binaryNumber = binaryNumber / 10;
                p++;
            }
        }
        return decimal;
    }
}