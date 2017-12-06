package et.sunhj.com.sandy;

/**
 * Created by Administrator on 2017/12/5.
 * 数据传递
 */

public class CommpanyDto {

    private boolean read = false ;

    private Commpany commpany ;

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Commpany getCommpany() {
        return commpany;
    }

    public void setCommpany(Commpany commpany) {
        this.commpany = commpany;
    }
}
