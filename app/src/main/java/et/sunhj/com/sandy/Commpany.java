package et.sunhj.com.sandy;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2017/12/5.
 * 公司信息
 */

public class Commpany implements Parcelable {

    private String commpnayName ;//公司名称

    private String commpanyNo ;//公司编号

    private String commpanyAddress ;//公司地址

    private String commpanyTel ;//联系电话

    private List<Station> stations ;//岗位信息

    public Commpany(){

    }

    protected Commpany(Parcel in) {
        commpnayName = in.readString();
        commpanyNo = in.readString();
        commpanyAddress = in.readString();
        commpanyTel = in.readString();
    }

    public static final Creator<Commpany> CREATOR = new Creator<Commpany>() {
        @Override
        public Commpany createFromParcel(Parcel in) {
            return new Commpany(in);
        }

        @Override
        public Commpany[] newArray(int size) {
            return new Commpany[size];
        }
    };

    public String getCommpnayName() {
        return commpnayName;
    }

    public void setCommpnayName(String commpnayName) {
        this.commpnayName = commpnayName;
    }

    public String getCommpanyNo() {
        return commpanyNo;
    }

    public void setCommpanyNo(String commpanyNo) {
        this.commpanyNo = commpanyNo;
    }

    public String getCommpanyAddress() {
        return commpanyAddress;
    }

    public void setCommpanyAddress(String commpanyAddress) {
        this.commpanyAddress = commpanyAddress;
    }

    public String getCommpanyTel() {
        return commpanyTel;
    }

    public void setCommpanyTel(String commpanyTel) {
        this.commpanyTel = commpanyTel;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(commpnayName);
        dest.writeString(commpanyNo);
        dest.writeString(commpanyAddress);
        dest.writeString(commpanyTel);
    }
}
