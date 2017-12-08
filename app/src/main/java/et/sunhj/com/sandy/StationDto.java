package et.sunhj.com.sandy;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */

public class StationDto implements Parcelable {

    ListView tableListView = null ;

    List<Station> stationsList = null ;

    public StationDto(){}

    protected StationDto(Parcel in) {
    }

    public static final Creator<StationDto> CREATOR = new Creator<StationDto>() {
        @Override
        public StationDto createFromParcel(Parcel in) {
            return new StationDto(in);
        }

        @Override
        public StationDto[] newArray(int size) {
            return new StationDto[size];
        }
    };

    public ListView getTableListView() {
        return tableListView;
    }

    public void setTableListView(ListView tableListView) {
        this.tableListView = tableListView;
    }

    public List<Station> getStationsList() {
        return stationsList;
    }

    public void setStationsList(List<Station> stationsList) {
        this.stationsList = stationsList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
