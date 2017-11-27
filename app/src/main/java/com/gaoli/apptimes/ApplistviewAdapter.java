package com.gaoli.apptimes;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoli on 2017/11/17.
 */

public class ApplistviewAdapter extends BaseAdapter {

    private List<String> appnamelist = new ArrayList<String>();
    private LayoutInflater layoutInflater;
    private Context context;

    public ApplistviewAdapter(Context context, /*List<Bitmap> imagelist,*/List<String> thenamelist) {
        //传入的data，就是我们要在listview中显示的信息
        this.context = context;
        this.appnamelist=thenamelist;
        //this.imagelist=imagelist;
        this.layoutInflater = LayoutInflater.from(context);
    }
    //这里定义了一个类，用来表示一个item里面包含的东西，像我的就是一个imageView和三个TextView，按自己需要来
    public class Info {
        //public ImageView image;
        public TextView app_name;
    }
    //所有要返回的数量，Id，信息等，都在data里面，从data里面取就好
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return appnamelist.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return appnamelist.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    //跟actvity中的oncreat()差不多，目的就是给item布局中的各个控件对应好，并添加数据
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ApplistviewAdapter.Info info = new ApplistviewAdapter.Info();
        convertView = layoutInflater.inflate(R.layout.appitems, null);
        //info.image = (ImageView) convertView.findViewById(R.id.downedimageView);
        info.app_name = (TextView) convertView
                .findViewById(R.id.appname);
        Log.i("YY",position+"");
        //设置数据
        //if(imagelist!=null&&position<imagelist.size())info.image.setImageBitmap((Bitmap) imagelist.get(position));
        if(position<appnamelist.size())info.app_name.setText((String) appnamelist.get(position));
        //notifyDataSetChanged();
        return convertView;
    }
    public void mynotifyDataSetChanged(){
        Log.i("SlidingListAdapter","params.hashCode: "+appnamelist.hashCode());
        notifyDataSetChanged();
    }
    public void setAppnamelist(List<String> theapps){
        appnamelist.clear();
        appnamelist.addAll(theapps);
    }
}
