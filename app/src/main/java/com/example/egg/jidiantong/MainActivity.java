package com.example.egg.jidiantong;
import java.lang.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ArrayList globwealist;
    TextView weartxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final Weather weather = new Weather();
        weartxt = (TextView)findViewById(R.id.weatxt);

        final Handler mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == 0x111)
                {
                    for(int i=0;i<globwealist.size();i++)
                    {
                        String tianqi = (String)globwealist.get(0);
                        String zuidiwendu = (String)globwealist.get(1);
                        String zuigaowendu = (String)globwealist.get(2);
                        String fengli = (String)globwealist.get(3);
                        weartxt.setText("今天： "+tianqi+"    "+zuigaowendu+"/"+zuidiwendu+"    "+"   "+fengli);

                    }
                }

            }
        };

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {

                   globwealist = weather.getWeather();
                    mHandler.sendEmptyMessage(0x111);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },1200);



    }



}



















class Weather
{
   public ArrayList getWeather() throws Exception
   {
       Document doc = Jsoup.connect("http://www.weather.com.cn/weather/101270201.shtml").get();

       //获取天拥有title属性的第一个匹配的属性值，即得到天气
       //String tianqi = (doc.select("p.wea")).attr("title");
       //直接得到第一个元素的值
       String tianqi = (doc.select("p.wea")).eq(0).text();
       /*温度
       * 最高温度
       * 最低温度
       * */
       String zuidiwendu = doc.select("p.tem > i").eq(0).text();
       String  zuigaowendu = doc.select("p.tem > span").eq(0).text();
       //风力
       String fengli = doc.select("p.win > i").eq(0).text();
       ArrayList<String> lis = new ArrayList();
       lis.add(tianqi);
       lis.add(zuidiwendu);
       lis.add(zuigaowendu);
       lis.add(fengli);
       return lis;
   }
}