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

    private String commpanySocialCode ; // 统一社会信用代码

    private String commpanyLinkMan ; //联系人

    private String commpanyAddress ;//公司地址

    private String commpanyTel ;//联系电话

    private List<Station> stations ;//岗位信息

    private boolean isNewData = false ; // 是否为新数据 , true 为新数据 需要显示 false 为老数据或者没有数据 不需要显示

    private String msg ; // 消息显示

    private boolean isNormal = false; //新数据查询是否正常 true 为正常 ，false 为异常

    public Commpany(){}

    protected Commpany(Parcel in) {
        commpnayName = in.readString();
        commpanyNo = in.readString();
        commpanyAddress = in.readString();
        commpanyTel = in.readString();
        isNewData = in.readByte() != 0;
        msg = in.readString();
        isNormal = in.readByte() != 0;
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

    public boolean isNewData() {
        return isNewData;
    }

    public void setNewData(boolean newData) {
        isNewData = newData;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isNormal() {
        return isNormal;
    }

    public void setNormal(boolean normal) {
        isNormal = normal;
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
        dest.writeByte((byte) (isNewData ? 1 : 0));
        dest.writeString(msg);
        dest.writeByte((byte) (isNormal ? 1 : 0));
    }

    public String getCommpanySocialCode() {
        return commpanySocialCode;
    }

    public void setCommpanySocialCode(String commpanySocialCode) {
        this.commpanySocialCode = commpanySocialCode;
    }

    public String getCommpanyLinkMan() {
        return commpanyLinkMan;
    }

    public void setCommpanyLinkMan(String commpanyLinkMan) {
        this.commpanyLinkMan = commpanyLinkMan;
    }
}
