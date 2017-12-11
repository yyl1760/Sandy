package et.sunhj.com.sandy;

/**
 * Created by Administrator on 2017/12/11.
 */
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class TimerTaskForListViewRolling extends TimerTask {
    private ListView listView;
    private int smoothBy = 1;
    private Context context;
    private List<Station> list;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            listView.smoothScrollBy(smoothBy, 0);
        };
    };

    public TimerTaskForListViewRolling(ListView listView, Context context, List<Station> list) {
        this.listView = listView;
        this.context = context;
        this.list = list;

        listView.setAdapter(new MyBaseAdapter());
    }
    @Override
    public void run() {
        Message msg = handler.obtainMessage();
        handler.sendMessage(msg);
    }

    private class MyBaseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
            //return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Station station = (Station) this.getItem(position%list.size());

            Log.i("定时任务之心：","大小："+position) ;

            if(convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item2, null);;
            }

//找到listView中的item， 并且给每个item设置值
            TextView zhaopinzhiwei = (TextView) convertView.findViewById(R.id.zhaopinzhiwei);
            TextView renshu = (TextView) convertView.findViewById(R.id.renshu);
            TextView zhuanyexueli = (TextView) convertView.findViewById(R.id.zhuanyexueli);
            TextView nianlin = (TextView) convertView.findViewById(R.id.nianlin);
            TextView xinbie = (TextView) convertView.findViewById(R.id.xinbie);
            TextView gongzidaiyu = (TextView) convertView.findViewById(R.id.gongzidaiyu);
            TextView qita = (TextView) convertView.findViewById(R.id.qita);

            //Map<String, String> map = sponsorList.get(position%sponsorList.size());

            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,100);
            convertView.setLayoutParams(layoutParams);
            zhaopinzhiwei.setText(station.getStationName());
            renshu.setText(station.getPersonNum());
            zhuanyexueli.setText(station.getProAndEduBackGround());
            nianlin.setText(station.getAge());
            xinbie.setText(station.getSex());
            gongzidaiyu.setText(station.getWealAndpay());
            qita.setText(station.getOthers());

            return convertView;
        }

    }

}
