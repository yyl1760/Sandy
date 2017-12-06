package et.sunhj.com.sandy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/12/6.
 * 文件夹名称
 */

public class ThreadFileNameDto implements Parcelable {

    private String fileName ;


    public ThreadFileNameDto(){

    }

    protected ThreadFileNameDto(Parcel in) {
        fileName = in.readString();
    }

    public static final Creator<ThreadFileNameDto> CREATOR = new Creator<ThreadFileNameDto>() {
        @Override
        public ThreadFileNameDto createFromParcel(Parcel in) {
            return new ThreadFileNameDto(in);
        }

        @Override
        public ThreadFileNameDto[] newArray(int size) {
            return new ThreadFileNameDto[size];
        }
    };

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fileName);
    }
}
