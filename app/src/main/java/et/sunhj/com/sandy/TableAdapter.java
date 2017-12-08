package et.sunhj.com.sandy;

/**
 * Created by Administrator on 2017/12/6.
 */

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TableAdapter extends BaseAdapter {
    private List<Station> list;
    private LayoutInflater inflater;
    private  int height ;//item 高度

    public TableAdapter(Context context, List<Station> list,int height){
        this.list = list;
        inflater = LayoutInflater.from(context);
        this.height = height ;
    }

    @Override
    public int getCount() {
        return list.size();
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
        Station station = (Station) this.getItem(position);
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item2, parent,false);

            viewHolder.zhaopinzhiwei = (TextView) convertView.findViewById(R.id.zhaopinzhiwei);
            viewHolder.renshu = (TextView) convertView.findViewById(R.id.renshu);
            viewHolder.zhuanyexueli = (TextView) convertView.findViewById(R.id.zhuanyexueli);
            viewHolder.nianlin = (TextView) convertView.findViewById(R.id.nianlin);
            viewHolder.xinbie = (TextView) convertView.findViewById(R.id.xinbie);
            viewHolder.gongzidaiyu = (TextView) convertView.findViewById(R.id.gongzidaiyu);
            viewHolder.qita = (TextView) convertView.findViewById(R.id.qita);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //height  每个item的 高度
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,height);
        convertView.setLayoutParams(layoutParams);
        viewHolder.zhaopinzhiwei.setText(station.getStationName());
        viewHolder.renshu.setText(station.getPersonNum());
        viewHolder.zhuanyexueli.setText(station.getProAndEduBackGround());
        viewHolder.nianlin.setText(station.getAge());
        viewHolder.xinbie.setText(station.getSex());
        viewHolder.gongzidaiyu.setText(station.getWealAndpay());
        viewHolder.qita.setText(station.getOthers());

        return convertView;
    }

    public static class ViewHolder{
        public TextView zhaopinzhiwei;
        public TextView renshu;
        public TextView zhuanyexueli;
        public TextView nianlin;
        public TextView xinbie;
        public TextView gongzidaiyu;
        public TextView qita;

    }

}