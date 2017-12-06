package et.sunhj.com.sandy;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */

public class ThreadFileDto implements Parcelable{

    private List<String> fileNameList ;

    public ThreadFileDto(){}

    protected ThreadFileDto(Parcel in) {
        fileNameList = in.createStringArrayList();
    }

    public static final Creator<ThreadFileDto> CREATOR = new Creator<ThreadFileDto>() {
        @Override
        public ThreadFileDto createFromParcel(Parcel in) {
            return new ThreadFileDto(in);
        }

        @Override
        public ThreadFileDto[] newArray(int size) {
            return new ThreadFileDto[size];
        }
    };

    public List<String> getFileNameList() {
        return fileNameList;
    }

    public void setFileNameList(List<String> fileNameList) {
        this.fileNameList = fileNameList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(fileNameList);
    }
}
