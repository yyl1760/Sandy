package et.sunhj.com.sandy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;





public class MainActivity extends Activity implements View.OnClickListener{

    TextView textView = null;
    TableLayout tableLayout = null;
    Button btn_set = null ;

    TextView commpany_name = null ;//单位名称
    TextView commpany_no = null ;//单位编号
    TextView commpany_address = null ;//单位地址
    TextView commpany_tel = null ;//联系电话

    TextView stationName_01 = null ;//岗位名称
    TextView personNum_01 = null ;//人数
    TextView proAndEduBackGround_01 = null ;//专业与学历
    TextView age_01 = null ;//年龄
    TextView sex_01 = null ;//性别
    TextView wealAndpay_01 = null ;//薪资与福利待遇
    TextView others_01 = null ; //其他

    TextView stationName_02 = null ;//岗位名称
    TextView personNum_02 = null ;//人数
    TextView proAndEduBackGround_02 = null ;//专业与学历
    TextView age_02 = null ;//年龄
    TextView sex_02 = null ;//性别
    TextView wealAndpay_02 = null ;//薪资与福利待遇
    TextView others_02 = null ; //其他

    TextView stationName_03 = null ;//岗位名称
    TextView personNum_03 = null ;//人数
    TextView proAndEduBackGround_03 = null ;//专业与学历
    TextView age_03 = null ;//年龄
    TextView sex_03 = null ;//性别
    TextView wealAndpay_03 = null ;//薪资与福利待遇
    TextView others_03 = null ; //其他

    TextView stationName_04 = null ;//岗位名称
    TextView personNum_04 = null ;//人数
    TextView proAndEduBackGround_04 = null ;//专业与学历
    TextView age_04 = null ;//年龄
    TextView sex_04 = null ;//性别
    TextView wealAndpay_04 = null ;//薪资与福利待遇
    TextView others_04 = null ; //其他

    TextView stationName_05 = null ;//岗位名称
    TextView personNum_05 = null ;//人数
    TextView proAndEduBackGround_05 = null ;//专业与学历
    TextView age_05 = null ;//年龄
    TextView sex_05 = null ;//性别
    TextView wealAndpay_05 = null ;//薪资与福利待遇
    TextView others_05 = null ; //其他


    private AlertDialog mAlertDialog = null; //TV设置对话框

    //public  String commonpath="smb://yyl:123456@192.168.212.132/test/"; //共享目录
    public  String commonpath="smb://yyl:123456@192.168.0.172/test/"; //共享目录

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // textView =  findViewById(R.id.textView1) ;
        tableLayout = findViewById(R.id.main_table) ;


        commpany_name = findViewById(R.id.commpany_name) ;//单位名称
        commpany_no = findViewById(R.id.commpany_no) ;//单位编号
        commpany_address = findViewById(R.id.commpany_address) ;//单位地址
        commpany_tel = findViewById(R.id.commpany_tel) ;//联系电话

         stationName_01 = findViewById(R.id.stationName_01) ;//岗位名称
         personNum_01 = findViewById(R.id.personNum_01) ;//人数
         proAndEduBackGround_01 = findViewById(R.id.proAndEduBackGround_01) ;//专业与学历
         age_01 = findViewById(R.id.age_01) ;//年龄
         sex_01 = findViewById(R.id.sex_01) ;//性别
         wealAndpay_01 = findViewById(R.id.wealAndpay_01) ;//薪资与福利待遇
         others_01 = findViewById(R.id.others_01) ; //其他

         stationName_02 = findViewById(R.id.stationName_02) ;//岗位名称
         personNum_02 = findViewById(R.id.personNum_02) ;//人数
         proAndEduBackGround_02 = findViewById(R.id.proAndEduBackGround_02) ;//专业与学历
         age_02 = findViewById(R.id.age_02) ;//年龄
         sex_02 = findViewById(R.id.sex_02) ;//性别
         wealAndpay_02 = findViewById(R.id.wealAndpay_02) ;//薪资与福利待遇
         others_02 = findViewById(R.id.others_02) ; //其他

        stationName_03 = findViewById(R.id.stationName_03) ;//岗位名称
        personNum_03 = findViewById(R.id.personNum_03) ;//人数
        proAndEduBackGround_03 = findViewById(R.id.proAndEduBackGround_03) ;//专业与学历
        age_03 = findViewById(R.id.age_03) ;//年龄
        sex_03 = findViewById(R.id.sex_03) ;//性别
        wealAndpay_03 = findViewById(R.id.wealAndpay_03) ;//薪资与福利待遇
        others_03 = findViewById(R.id.others_03) ; //其他

        stationName_04 = findViewById(R.id.stationName_04) ;//岗位名称
        personNum_04 = findViewById(R.id.personNum_04) ;//人数
        proAndEduBackGround_04 = findViewById(R.id.proAndEduBackGround_04) ;//专业与学历
        age_04 = findViewById(R.id.age_04) ;//年龄
        sex_04 = findViewById(R.id.sex_04) ;//性别
        wealAndpay_04 = findViewById(R.id.wealAndpay_04) ;//薪资与福利待遇
        others_04 = findViewById(R.id.others_04) ; //其他

        stationName_05 = findViewById(R.id.stationName_05) ;//岗位名称
        personNum_05 = findViewById(R.id.personNum_05) ;//人数
        proAndEduBackGround_05 = findViewById(R.id.proAndEduBackGround_05) ;//专业与学历
        age_05 = findViewById(R.id.age_05) ;//年龄
        sex_05 = findViewById(R.id.sex_05) ;//性别
        wealAndpay_05 = findViewById(R.id.wealAndpay_05) ;//薪资与福利待遇
        others_05 = findViewById(R.id.others_05) ; //其他

        View viewDialog = View.inflate(getApplicationContext(), R.layout.dialog_layout, null);

        //初始化对话框
        mAlertDialog = new AlertDialog.Builder(this)
                .setView(viewDialog)
                /*
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAlertDialog.cancel();
                    }
                })
                */
                .create();
        mAlertDialog.setTitle("设置电视对应文件夹：");
        mAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mAlertDialog.cancel();
            }
        });
        /*
        mAlertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                mAlertDialog.cancel();
                return false;
            }
        });
        */

        //监听button
        findViewById(R.id.btn_set).setOnClickListener(this);
        findViewById(R.id.btn_refresh).setOnClickListener(this);

        //开启基础配置 有则读取 无则配置
        readConfig() ;

    }

    /**
     * 首次登录或者修改TV 配置
     * tvConfig
     */
    public void initConfig(String fileName){

        if(Util.isNull(fileName)){//调用配置TV文件夹配置
            findViewById(R.id.btn_set).callOnClick() ; //打开配置
        }else{
            //获取SharedPreferences对象
            SharedPreferences tvConfig = getSharedPreferences("tvConfig", Context.MODE_PRIVATE);
            //在SharedPreference中写入数据需要使用Editor
            SharedPreferences.Editor editor = tvConfig.edit();
            //存入键值对数据，注意此处的put[type]("key",value);
            //editor.putString("STRING_KEY", "string");
            //editor.putInt("INT_KEY", 0);
            //editor.putBoolean("BOOLEAN_KEY", true);
            editor.putString("TVFILE",fileName);
            //editor.apply();
            editor.commit(); //提交保存

            readConfig() ;
        }


    }

    public void readConfig(){

        //获取SharedPreferences对象
        SharedPreferences tvConfig = getSharedPreferences("tvConfig", Context.MODE_PRIVATE);
        //Log.i("tvConfig","tvConfig is :"+tvConfig) ;
        //检查当前键是否存在
        boolean isContains = tvConfig.contains("TVFILE");
        Log.i("isContains","isContains is :"+isContains) ;

        //不存在开启配置
        if(!isContains){
            initConfig(null) ;
        }else{ //存在 开始读取数据
            //通过连接配置TV 界面的 保存按钮 触发 子线程 //通过配置的 TV 编号配置 拉取局域网路径
            childThread() ;
        }
        //使用getAll可以返回所有可用的键值
        //Map<String,?> allMaps=myPreference.getAll();
        //String tvnum = tvConfig.getString("TVNUM","");
        //Log.i("TVNUM",tvnum ) ;

    }

    public String readFile(){
        String res =" 没有读取到局域网文件信息 ！";
        try {
            /*
            remoteUrl说明:
            如果是无需密码的共享，则类似如下格式：
               smb://ip/sharefolder（例如：smb://192.168.0.77/test）
            如果需要用户名、密码，则类似如下格式：
               Smb://username:password@ip/sharefolder（例如：smb://chb:123456@192.168.0.1/test）
             */
            String resx = "读取到局域网文件信息：" ;
           // SmbFile smbFile = new SmbFile("smb://yyl:123456@192.168.0.172/test/1.txt");

            SharedPreferences tvConfig = getSharedPreferences("tvConfig", Context.MODE_PRIVATE);
            //检查当前键是否存在
            boolean isContains = tvConfig.contains("TVFILE");
            //不存在开启配置
            if(!isContains){
                initConfig(null) ;
            }else{ //存在 开始读取数据
                String tvfile = tvConfig.getString("TVFILE","");
                String lastPath = commonpath + tvfile +"/" ;
                System.out.print("最后读取路径："+lastPath);
            }

            SmbFile smbFile = new SmbFile("smb://yyl:123456@192.168.212.132/test/1.txt");
            if(smbFile.exists()){
                Log.i("文件查找","有文件") ;

                int length = smbFile.getContentLength();//得到文件的大小
                if(length>0){
                    Log.i("文件长度","长度为 :"+length) ;
                    byte buffer[] = new byte[length];
                    SmbFileInputStream in = new SmbFileInputStream(smbFile); //建立smb文件输入流
                    while ((in.read(buffer)) != -1) {
                        /*
                        new  AlertDialog.Builder(this)
                                .setTitle("确认" )
                                .setMessage("确定吗？"+buffer )
                                .setPositiveButton("是" ,  null )
                                .setNegativeButton("否" , null)
                                .show();
                        */
                         System.out.write(buffer);
                         System.out.println(buffer.length);
                        Log.i("FileInfo","buffer is :"+buffer) ;
                    }

                    resx += Util.convertStreamToString(smbFile.getInputStream()) ;
                    in.close();
                }else{
                    Log.i("文件长度","长度为 :0") ;
                }
            }else{
                Log.i("文件查找","没有文件") ;
            }

            res = resx ;

        } catch (Exception e) {
            //e.printStackTrace();

        }
        return res ;
    }


    public Commpany readExcel(){
        Commpany commpany = null ;
        try {
                /*
                    remoteUrl说明:
                    如果是无需密码的共享，则类似如下格式：
                    smb://ip/sharefolder（例如：smb://192.168.0.77/test）
                    如果需要用户名、密码，则类似如下格式：
                    Smb://username:password@ip/sharefolder（例如：smb://chb:123456@192.168.0.1/test）
                */

           // SmbFile smbFile = new SmbFile("smb://yyl:123456@192.168.0.172/test/1.xls");
            // SmbFile smbFile = new SmbFile("smb://yyl:123456@192.168.0.172/test/3.xls");
            SharedPreferences tvConfig = getSharedPreferences("tvConfig", Context.MODE_PRIVATE);
            //检查当前键是否存在
            boolean isContains = tvConfig.contains("TVFILE");
            //不存在开启配置
            if(!isContains){
                initConfig(null) ;
            }else{ //存在 开始读取数据
                String tvfile = tvConfig.getString("TVFILE","");
                String lastPath = commonpath + tvfile +"/" ;
                System.out.print("最后读取路径："+lastPath);
                List<SmbFile> getFiles = Util.readLastSortFile(lastPath) ;
                if( !Util.isNull(getFiles) && getFiles.size()>0){

                    String lastReadPath = lastPath + getFiles.get(0).getName() ;
                    System.out.print("最后读取文件："+lastReadPath);

                    SmbFile smbFile = new SmbFile(lastReadPath);

                    if(!Util.isNull(smbFile) && smbFile.exists()){
                        Log.i("文件查找","有文件") ;
                        /**
                         * 解析excel文件 ，并把数据放入数组中  格式 xlsx xls
                         */
                        try {

                            Workbook workbook = WorkbookFactory.create(smbFile.getInputStream()); //这种方式 Excel 2003/2007/2010 都是可以处理的
                            Sheet sheet = workbook.getSheetAt(0);//得到excel第一页的内容

                            List<Station> stationList = new ArrayList<Station>() ;//组装岗位信息

                            for (int i = 3; i < sheet.getPhysicalNumberOfRows(); i++) {
                                //从第三行开始读取 循环行数
                                Row row = sheet.getRow(i);
                                //获取当前行的列长度
                                int cols_length = row.getPhysicalNumberOfCells();
                                //设置当前数组长度 5
                                String[] str = new String[cols_length];

                                Station station = new Station() ;//组装岗位
                                for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                                    Cell cell = row.getCell(j);
                                    String val = Util.getCellValue(cell) ;
                                    if( i == 3  ){ //第三行 第四行读取 2 4 的值 设置公司名称 公司编号
                                        if( j == 2 ){
                                            commpany = new Commpany();
                                            commpany.setCommpnayName(val);
                                        }
                                        if( j == 8 )
                                            commpany.setCommpanyNo(val);
                                    }else if( i == 4 ){
                                        if( j == 2 )
                                            commpany.setCommpanyAddress(val);
                                        if( j == 8 )
                                            commpany.setCommpanyTel(val);
                                    }else{  //列表行
                                        if( i >= 6 ){

                                            if( j == 1 )
                                                station.setStationName(val); //岗位名称
                                            if( j == 2 )
                                                station.setPersonNum(val);//人数
                                            if( j == 3 )
                                                station.setProAndEduBackGround(val);//专业与学历
                                            if( j == 4 )
                                                station.setProAndEduBackGround(station.getProAndEduBackGround()+"-"+val);//专业与学历
                                            if( j == 5 )
                                                station.setAge(val);//年龄
                                            if( j == 6 )
                                                station.setSex(val);//性别
                                            if( j == 7 )
                                                station.setWealAndpay(val);//工资与福利待遇
                                            if( j == 8 )
                                                station.setOthers(val);//其他

                                        }

                                    }

                                }
                                if(i >= 6)
                                    stationList.add(station) ;
                            }

                            commpany.setStations(stationList);//设置岗位

                            //log.info("文件"+path.substring(path.lastIndexOf("\\")+1)+"解析完毕!");
                        } catch (FileNotFoundException e) {
                            // log.error("！！！！！！！ 未找到文件!文件为: 【"+path+"】");
                            e.printStackTrace();
                        } catch (InvalidFormatException e) {
                            //log.error("！！！！！！！ 解析文件出错!文件为: 【"+path+"】");
                            e.printStackTrace();
                        } catch (IOException e) {
                            //log.error("！！！！！！！ 解析文件出错!文件为: 【"+path+"】");
                            e.printStackTrace();
                        }

                 /*
                jxl.Workbook readwb = null;
                //构建Workbook对象, 只读Workbook对象
                //直接从本地文件创建Workbook
                //InputStream instream = new FileInputStream("F:/红楼人物.xls");
                readwb = Workbook.getWorkbook(smbFile.getInputStream());
                //Sheet的下标是从0开始
                //获取第一张Sheet表
                Sheet readsheet = readwb.getSheet(0);
                //获取Sheet表中所包含的总列数
                int rsColumns = readsheet.getColumns();
                //获取Sheet表中所包含的总行数
                int rsRows = readsheet.getRows();
                //获取指定单元格的对象引用

                for (int i = 0; i < rsRows; i++)
                {
                                    for (int j = 0; j < rsColumns; j++)
                    {
                        Cell cell = readsheet.getCell(j, i);
                        System.out.print(cell.getContents() + " ");
                        resx+= cell.getContents() + " " ;
                    }
                    resx +="/n" ;
                    System.out.println();
                }
                */

                    }else{
                        Log.i("文件查找","没有文件") ;
                    }


                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return commpany ;
    }

    /**
     * 网络操作相关的子线程
     */
    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            // 在这里进行 http request.网络请求相关操作
           // Looper.prepare();
            Commpany commpany  =  readExcel() ;//readExcel() ; // readFile() ;

            Message msg = new Message();
            Bundle data = new Bundle();

            if(!Util.isNull(commpany)){
                data.putParcelable("commpany",(Parcelable) commpany);
            }else{
                data.putParcelable("commpany",null);
            }

            //data.putString("value", res);
            msg.setData(data);
            handler.sendMessage(msg);
            //Looper.loop();
        }
    };



    Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            /*
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("结果：", "请求结果为-->" + val);
            */
            Commpany commpany = (Commpany) msg.getData().getParcelable("commpany");

            // UI界面的更新等相关操作
            //textView.setText(val);
            if(Util.isNull(commpany)){
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setTitle("错误信息")
                        .setMessage("从局域网获取数据失败！请监察读取文件夹编号后点击刷新")
                        .setPositiveButton("确定", null)
                        .show();
                clearShowData() ;
            }else{//更新界面信息
                commpany_name.setText(commpany.getCommpnayName()); //单位名称
                commpany_no.setText(commpany.getCommpanyNo());//单位编号
                commpany_address.setText(commpany.getCommpanyAddress());//单位地址
                commpany_tel.setText(commpany.getCommpanyTel()); //联系电话
                for( int i = 0 ; i < commpany.getStations().size() ; i++ ){

                    if(i == 0){
                        stationName_01.setText(commpany.getStations().get(i).getStationName()) ;//岗位名称
                        personNum_01.setText(commpany.getStations().get(i).getPersonNum()); //人数
                        proAndEduBackGround_01.setText(commpany.getStations().get(i).getProAndEduBackGround()); //专业与学历
                        age_01.setText(commpany.getStations().get(i).getAge()); //年龄
                        sex_01.setText(commpany.getStations().get(i).getSex());//性别
                        wealAndpay_01.setText(commpany.getStations().get(i).getWealAndpay()); //薪资与福利待遇
                        others_01.setText(commpany.getStations().get(i).getOthers()); //其他

                    }

                    if(i == 1){
                        stationName_02.setText(commpany.getStations().get(i).getStationName()) ;//岗位名称
                        personNum_02.setText(commpany.getStations().get(i).getPersonNum()); //人数
                        proAndEduBackGround_02.setText(commpany.getStations().get(i).getProAndEduBackGround()); //专业与学历
                        age_02.setText(commpany.getStations().get(i).getAge()); //年龄
                        sex_02.setText(commpany.getStations().get(i).getSex());//性别
                        wealAndpay_02.setText(commpany.getStations().get(i).getWealAndpay()); //薪资与福利待遇
                        others_02.setText(commpany.getStations().get(i).getOthers()); //其他
                    }

                    if(i == 2){
                        stationName_03.setText(commpany.getStations().get(i).getStationName()) ;//岗位名称
                        personNum_03.setText(commpany.getStations().get(i).getPersonNum()); //人数
                        proAndEduBackGround_03.setText(commpany.getStations().get(i).getProAndEduBackGround()); //专业与学历
                        age_03.setText(commpany.getStations().get(i).getAge()); //年龄
                        sex_03.setText(commpany.getStations().get(i).getSex());//性别
                        wealAndpay_03.setText(commpany.getStations().get(i).getWealAndpay()); //薪资与福利待遇
                        others_03.setText(commpany.getStations().get(i).getOthers()); //其他
                    }

                    if(i == 3){
                        stationName_04.setText(commpany.getStations().get(i).getStationName()) ;//岗位名称
                        personNum_04.setText(commpany.getStations().get(i).getPersonNum()); //人数
                        proAndEduBackGround_04.setText(commpany.getStations().get(i).getProAndEduBackGround()); //专业与学历
                        age_04.setText(commpany.getStations().get(i).getAge()); //年龄
                        sex_04.setText(commpany.getStations().get(i).getSex());//性别
                        wealAndpay_04.setText(commpany.getStations().get(i).getWealAndpay()); //薪资与福利待遇
                        others_04.setText(commpany.getStations().get(i).getOthers()); //其他
                    }

                    if(i == 4){
                        stationName_05.setText(commpany.getStations().get(i).getStationName()) ;//岗位名称
                        personNum_05.setText(commpany.getStations().get(i).getPersonNum()); //人数
                        proAndEduBackGround_05.setText(commpany.getStations().get(i).getProAndEduBackGround()); //专业与学历
                        age_05.setText(commpany.getStations().get(i).getAge()); //年龄
                        sex_05.setText(commpany.getStations().get(i).getSex());//性别
                        wealAndpay_05.setText(commpany.getStations().get(i).getWealAndpay()); //薪资与福利待遇
                        others_05.setText(commpany.getStations().get(i).getOthers()); //其他
                    }

                }
            }


        }
    };

    public void childThread(){
        //通过配置的 TV 编号配置 拉取局域网路径
        new Thread(networkTask).start(); // 开启一个子线程，进行网络操作，等待有返回结果，使用handler通知UI
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_set){
            mAlertDialog.show();
            readFilesThread() ;
        }

        if(v.getId() == R.id.btn_refresh){
            childThread() ;
            new AlertDialog.Builder(this)
                    .setTitle("信息：")
                    .setMessage("开始连接...")
                    .setPositiveButton("确定", null)
                    .show();
        }

    }

    /**
     * 读取文件夹目录子线程
     */
    Runnable readFileTask = new Runnable() {

        @Override
        public void run() {
            // 在这里进行 http request.网络请求相关操作
            ThreadFileDto threadFileDto = new ThreadFileDto() ;//数据传递
            List<String> stringList = Util.readFileDirectory(commonpath) ; //读取结果

            Message msg = new Message();
            Bundle data = new Bundle();

            if(!Util.isNull(stringList) && stringList.size() > 0){
                threadFileDto.setFileNameList(stringList);
                //传递文件目录：
                data.putParcelable("threadFileDto",(Parcelable) threadFileDto);
            }else{
                data.putParcelable("threadFileDto",null);
            }
            msg.setData(data);
            readFileTaskHandler.sendMessage(msg);

        }
    };

    Handler readFileTaskHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            ThreadFileDto threadFileDto = (ThreadFileDto) msg.getData().getParcelable("threadFileDto");
            // UI界面的更新等相关操作
            //textView.setText(val);
            if(Util.isNull(threadFileDto)){
                TextView tv = (TextView) mAlertDialog.findViewById(R.id.tv_dialog_textView);
                tv.setText("读取共享文件夹错误，请检查共享路径和账号后再试。");
                clearShowData() ;
                Log.i("错误信息：",tv.getText().toString()) ;
            }else{//更新界面对话框文件夹信息
                TextView tv = (TextView) mAlertDialog.findViewById(R.id.tv_dialog_textView);

                String showText ="文件夹目录读取成功！" ;
                //读取当前对应目录：
                SharedPreferences tvConfig = getSharedPreferences("tvConfig", Context.MODE_PRIVATE);
                //检查当前键是否存在
                boolean isContains = tvConfig.contains("TVFILE");
                //不存在开启配置
                if(isContains){
                    showText+="当前对应文件夹："+ tvConfig.getString("TVFILE","") ;
                }else{
                    showText+="当前对应文件夹： 无" ;
                }
                tv.setText(showText);
                //tv.setText(DateFormat.format("yyyy-MM-dd hh:mm:ss", System.currentTimeMillis()).toString()+ " ,读取成功！");
                ListView listView = (ListView) mAlertDialog.findViewById(R.id.tv_dialog_listView);
                //listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.activity_list_item,threadFileDto.getFileNameList()));
                listView.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, threadFileDto.getFileNameList()));//这里使用的android自带的android.R.layout.simple_list_item_1
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(MainActivity.this, "Item clicked, position is:" + parent.getAdapter().getItem(position),
                                Toast.LENGTH_SHORT).show();
                        initConfig(parent.getAdapter().getItem(position).toString()) ;
                        mAlertDialog.cancel();
                    }
                });
                Log.i("日志信息：",tv.getText().toString()) ;
            }


        }

    };

    public void readFilesThread(){
        //通过配置的 TV 编号配置 拉取局域网路径
        new Thread(readFileTask).start(); // 开启一个子线程，进行网络操作，等待有返回结果，使用handler通知UI
    }



    //清空显示数据
    public void clearShowData(){
        String val ="" ;
        commpany_name.setText(val); //单位名称
        commpany_no.setText(val);//单位编号
        commpany_address.setText(val);//单位地址
        commpany_tel.setText(val); //联系电话

                stationName_01.setText(val) ;//岗位名称
                personNum_01.setText(val); //人数
                proAndEduBackGround_01.setText(val); //专业与学历
                age_01.setText(val); //年龄
                sex_01.setText(val);//性别
                wealAndpay_01.setText(val); //薪资与福利待遇
                others_01.setText(val); //其他

                stationName_02.setText(val) ;//岗位名称
                personNum_02.setText(val); //人数
                proAndEduBackGround_02.setText(val); //专业与学历
                age_02.setText(val); //年龄
                sex_02.setText(val);//性别
                wealAndpay_02.setText(val); //薪资与福利待遇
                others_02.setText(val); //其他

                stationName_03.setText(val) ;//岗位名称
                personNum_03.setText(val); //人数
                proAndEduBackGround_03.setText(val); //专业与学历
                age_03.setText(val); //年龄
                sex_03.setText(val);//性别
                wealAndpay_03.setText(val); //薪资与福利待遇
                others_03.setText(val); //其他

                stationName_04.setText(val) ;//岗位名称
                personNum_04.setText(val); //人数
                proAndEduBackGround_04.setText(val); //专业与学历
                age_04.setText(val); //年龄
                sex_04.setText(val);//性别
                wealAndpay_04.setText(val); //薪资与福利待遇
                others_04.setText(val); //其他

                stationName_05.setText(val) ;//岗位名称
                personNum_05.setText(val); //人数
                proAndEduBackGround_05.setText(val); //专业与学历
                age_05.setText(val); //年龄
                sex_05.setText(val);//性别
                wealAndpay_05.setText(val); //薪资与福利待遇
                others_05.setText(val); //其他
    }
}
