package et.sunhj.com.sandy;

/**
 * Created by Administrator on 2017/12/5.
 * 岗位信息
 */

public class Station {

    private String stationName = "" ;//招聘职位

    private String personNum = "" ;//人数

    private String proAndEduBackGround = "" ;//专业与学历

    private String age = "" ;//年龄

    private String sex = "" ;//性别

    private String wealAndpay = "" ;//薪资与福利待遇

    private String others = ""; //其他


    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getPersonNum() {
        return personNum;
    }

    public void setPersonNum(String personNum) {
        this.personNum = personNum;
    }

    public String getProAndEduBackGround() {
        return proAndEduBackGround;
    }

    public void setProAndEduBackGround(String proAndEduBackGround) {
        this.proAndEduBackGround = proAndEduBackGround;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getWealAndpay() {
        return wealAndpay;
    }

    public void setWealAndpay(String wealAndpay) {
        this.wealAndpay = wealAndpay;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }
}
