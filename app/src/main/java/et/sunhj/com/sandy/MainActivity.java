package et.sunhj.com.sandy;

import android.app.Activity;
import android.app.ActivityManager;
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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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


    private AlertDialog mAlertDialog = null; //TV设置对话框
    private AlertDialog infoDialog = null; //信息提示对话框

    //public  String commonpath="smb://yyl:123456@192.168.212.132/test/"; //共享目录
    public  String commonpath="smb://yyl:123456@192.168.0.172/test/"; //共享目录

    private ListView recalllistView = null ;
    private List<Station> recallstation = null ;

    private Timer refreshtimer = new Timer(true); //定时器
    private String lastReadPath = null ; //最后读取路径+文件名称
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // textView =  findViewById(R.id.textView1) ;
       // tableLayout = findViewById(R.id.main_table) ;

        commpany_name = findViewById(R.id.commpany_name) ;//单位名称
        commpany_no = findViewById(R.id.commpany_no) ;//单位编号
        commpany_address = findViewById(R.id.commpany_address) ;//单位地址
        commpany_tel = findViewById(R.id.commpany_tel) ;//联系电话

        View viewDialog = View.inflate(getApplicationContext(), R.layout.dialog_layout, null);

        //初始化设置对话框
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

        //设置信息提示对话框
        infoDialog = new AlertDialog.Builder(MainActivity.this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("信息")
                .setMessage("从局域网获取数据失败！请检查读取文件夹编号")
                .setPositiveButton("确定", null).create();


        //监听button
        findViewById(R.id.btn_set).setOnClickListener(this);

        //开启基础配置 有则读取 无则配置
        readConfig() ;

        //启动定时器 5 秒钟后 每个10 秒执行一次任务
        refreshtimer.schedule(refreshtask, 10*1000, 3*1000);

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

    public Commpany readExcel(){
        Commpany commpany = new Commpany(); ;
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

                    String currentReadPath = lastPath + getFiles.get(0).getName() ;

                    if( Util.isNull(lastReadPath) || !lastReadPath.equals(currentReadPath) ){ //路径变动需要读取

                        lastReadPath = currentReadPath ;

                        commpany.setNewData(true);// 新数据

                        System.out.print("最后读取文件："+lastReadPath);
                        SmbFile smbFile = new SmbFile(lastReadPath);

                                if(!Util.isNull(smbFile) && smbFile.exists()){
                                    Log.i("文件查找","有文件") ;

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
                                                    if( j == 2 )
                                                        commpany.setCommpnayName(val);
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
                                                            station.setProAndEduBackGround(station.getProAndEduBackGround()+","+val);//专业与学历
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

                                        commpany.setNormal(true);//新数据显示正常

                                    } catch (FileNotFoundException e) {
                                        // log.error("！！！！！！！ 未找到文件!文件为: 【"+path+"】");
                                        commpany.setMsg("未找到文件!");
                                        e.printStackTrace();
                                    } catch (InvalidFormatException e) {
                                        //log.error("！！！！！！！ 解析文件出错!文件为: 【"+path+"】");
                                        commpany.setMsg("解析文件出错!");
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        commpany.setMsg("解析文件出错!");
                                        //log.error("！！！！！！！ 解析文件出错!文件为: 【"+path+"】");
                                        e.printStackTrace();
                                    }

                                }else{
                                    commpany.setMsg("该文件夹下没有文件");
                                    Log.i("文件查找","没有文件") ;
                                }

                    }


                }else{
                    lastReadPath = null ;
                    commpany.setMsg("该文件夹下没有文件,请联系管理员添加");
                    Log.i("文件查找","没有文件") ;
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

            if(!Util.isNull(commpany))
                data.putParcelable("commpany",(Parcelable) commpany);
            else
                data.putParcelable("commpany",null);
            //data.putString("value", res);
            msg.setData(data);
            handler.sendMessage(msg);
            //Looper.loop();
        }
    };



    Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Commpany commpany = (Commpany) msg.getData().getParcelable("commpany");

            // UI界面的更新等相关操作
            //判断是老路径还是新路径
            if(Util.isNull(commpany.getMsg())){
                        if(commpany.isNewData()){ //新数据
                                        //判断新数据是否显示正常
                                        if(commpany.isNormal()) {  //更新界面信息
                                            infoDialog.cancel();

                                            commpany_name.setText(commpany.getCommpnayName()); //单位名称
                                            commpany_no.setText(commpany.getCommpanyNo());//单位编号
                                            commpany_address.setText(commpany.getCommpanyAddress());//单位地址
                                            commpany_tel.setText(commpany.getCommpanyTel()); //联系电话

                                            ListView tableListView = (ListView) findViewById(R.id.list);
                                            TableAdapter adapter = new TableAdapter(MainActivity.this, commpany.getStations(),commpany_name.getHeight());
                                            tableListView.setAdapter(adapter);
                                            tableListView.setEnabled(false);
                                            tableListView.setFocusable(false);
                                            //清空原有数据 循环
                                            onDestroy();
                                            //重置显示数据
                                            recalllistView = tableListView ;
                                            recallstation = commpany.getStations() ;
                                            //启动循环显示
                                            new Thread(runnableListViewTask).start() ;

                                        }else{ //提示错误信息
                                            infoDialog.setMessage(commpany.getMsg());
                                            if(!infoDialog.isShowing()) //没显示对话框
                                                infoDialog.show();
                                            //清空显示数据
                                            clearShowData() ;
                                        }
                        }

            }else{
                //提示错误信息
                infoDialog.setMessage(commpany.getMsg());
                if(!infoDialog.isShowing()) //没显示对话框
                    infoDialog.show();
                //清空显示数据
                clearShowData() ;
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

            clearShowData() ;  //清空数据
            lastReadPath = null ;//点击设置清空最后读取路径

            ThreadFileDto threadFileDto = (ThreadFileDto) msg.getData().getParcelable("threadFileDto");
            // UI界面的更新等相关操作
            //textView.setText(val);
            if(Util.isNull(threadFileDto)){
                TextView tv = (TextView) mAlertDialog.findViewById(R.id.tv_dialog_textView);
                tv.setText("读取共享文件夹错误，请检查共享路径和账号后再试。");
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
                        Toast.makeText(MainActivity.this, "您选择了:" + parent.getAdapter().getItem(position),
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

        ListView tableListView = (ListView) findViewById(R.id.list);
        TableAdapter adapter = new TableAdapter(MainActivity.this, new ArrayList<Station>() ,commpany_name.getHeight());
        tableListView.setAdapter(adapter);
    }

    /***
     * 循环listview 开始
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerListView.removeCallbacks(runnableListViewTask);
        isEnd = false;
        //handlerListView = new Handler();
    }
    boolean isEnd = false;
    Handler handlerListView = new Handler();
    Runnable runnableListViewTask = new Runnable() {
        @Override
        public void run() {
            //计算偏移量
            int pos = isEnd ? 0 : recalllistView.getLastVisiblePosition();
            if(pos == 0)
            recalllistView.setSelection(pos);
            else
                recalllistView.setSelection(pos+1);
            //Log.e("TAG","current selected:"+pos);
            isEnd = pos>=recallstation.size()-1;
            handlerListView.postDelayed(this, 3000);
        }
    };
    /***
     * 循环listview 结束
     */

    //定时任务开始
    private TimerTask refreshtask = new TimerTask() {
        public void run() {
            Log.i("定时任务状态：", "对话框："+mAlertDialog.isShowing());

            if(!mAlertDialog.isShowing()){ //设置对话框未打开

                childThread() ; //读取数据

                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("msg","刷新数据中...");
                msg.setData(data);
                refreshhandler.sendMessage(msg);
            }
        }
    };

    private Handler refreshhandler  = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
           // Toast.makeText(MainActivity.this, msg.getData().get("msg").toString(), Toast.LENGTH_SHORT).show();
        }
    };


}
