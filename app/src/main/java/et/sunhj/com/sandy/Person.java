package et.sunhj.com.sandy;

/**
 * Created by Administrator on 2017/12/6.
 */

public class Person {
    private String name;
    private String sex;
    private int age;

    public Person() {
        super();
    }

    public Person(String name, String sex, int age) {
        super();
        this.name = name;
        this.sex = sex;
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }


}