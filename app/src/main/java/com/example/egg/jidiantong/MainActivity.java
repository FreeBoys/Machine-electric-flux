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

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity
{
    ArrayList globwealist;
    TextView weartxt;
    ListView newPaperls;
    String[] URLS = new String[14];
    ArrayAdapter<String> newpaperlistAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final Weather weather = new Weather();
        final Newpaper newpaper = new Newpaper();
        weartxt = (TextView)findViewById(R.id.weatxt);
        newPaperls = (ListView)findViewById(R.id.list1);


        //list监听
        newPaperls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // Log.i("TAG",i+"");
                switch(i){
                    case 0:
                    {
                        openWebview(URLS[i]);
                    }
                    break;
                    case 1:
                    {
                        openWebview(URLS[i]);
                    }
                    break;
                    case 2:
                    {
                        openWebview(URLS[i]);
                    }
                    break;
                    case 3:
                    {
                        openWebview(URLS[i]);
                    }
                    break;
                    case 4:
                    {
                        openWebview(URLS[i]);
                    }
                    break;
                    case 5:
                    {
                        openWebview(URLS[i]);
                    }
                    break;
                    case 6:
                    {
                        openWebview(URLS[i]);
                    }
                    break;
                    case 7:
                    {
                        openWebview(URLS[i]);
                    }
                    break;
                    case 8:
                    {
                        openWebview(URLS[i]);
                    }
                    break;
                    case 9:
                    {
                        openWebview(URLS[i]);
                    }
                    break;
                    case 10:
                    {
                        openWebview(URLS[i]);
                    }
                    break;
                    case 11:
                    {
                        openWebview(URLS[i]);
                    }
                    break;
                    case 12:
                    {
                        openWebview(URLS[i]);
                    }
                    break;
                    case 13:
                    {
                        openWebview(URLS[i]);
                    }
                    break;
                    case 14:
                    {
                        openWebview("http://scemi.com/xwzx/xyyw.htm");
                    }
                }
            }


        });


        final Handler mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case 0x111: {
                        for (int i = 0; i < globwealist.size(); i++) {
                            String tianqi = (String) globwealist.get(0);
                            String zuidiwendu = (String) globwealist.get(1);
                            String zuigaowendu = (String) globwealist.get(2);
                            String fengli = (String) globwealist.get(3);
                            weartxt.setText("今天： " + tianqi + "    " + zuigaowendu + "~" + zuidiwendu + "    " + "   " + fengli);
                        }
                    }
                    break;

                    case 0x222:
                    {
                        newPaperls.setAdapter(newpaperlistAdapter);
                    }
                    break;
                }

            }
        };

        //天气更新线程
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
        },500);

        //新闻更新线程
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    newpaperlistAdapter = newpaper.getNewpaper();
                    mHandler.sendEmptyMessage(0x222);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        },500);

        //新闻路径更新线程
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                URLS = newpaper.getNewpaperHref();
            }
        },1000);

    }

      /*
        *
        * 开启webviewActivity
        *
        * */
        public void openWebview(String URL)
        {
            Intent intent = new Intent(MainActivity.this,Webview.class);
            intent.putExtra("URL_String",URL);
            startActivity(intent);
        }


    /*
* 新闻列表获取
* */
    class Newpaper
    {
        Document doc;
        String url = "http://scemi.com/";
        public ArrayAdapter<String> getNewpaper()
        {
            int i =0;
            String[] newPaper = new String[15];

            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements newPaperdata = (doc.select("div.list_con"));
            for(;i<newPaperdata.size();i++)
            {
                //System.out.println(newPaperdata.eq(i).text());
                newPaper[i] = newPaperdata.eq(i).text();
            }
            newPaper[14] = "更多新闻>>>>>>";
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                    (MainActivity.this,android.R.layout.simple_dropdown_item_1line,newPaper);
            //System.out.println(newPaper[14]);
            return arrayAdapter;
        }

        //获取新闻URL
        public String[] getNewpaperHref()
        {
            String[] URLs = new String[14];
            int i = 0;
            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements links = (doc.select("div.list_con>a[href]"));
            for (Element link : links) {
                URLs[i] = link.attr("abs:href");
                i++;
                //System.out.println(URL);
            }
            return URLs;
        }

    }



}


//天气获取类
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




