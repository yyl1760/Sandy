package et.sunhj.com.sandy;

import android.util.Log;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

/**
 * Created by Administrator on 2017/12/1.
 */

public class Util {


    /**
     * 获取ip地址
     * @return
     */
    public static String getHostIP() {

        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            Log.i("yao", "SocketException");
            e.printStackTrace();
        }
        return hostIp;

    }


    /**
     * 获取IP地址
     * @return
     */
    public static String getNetIp() {
        URL infoUrl = null;
        InputStream inStream = null;
        String line = "";
        try {
            infoUrl = new URL("http://pv.sohu.com/cityjson?ie=utf-8");
            URLConnection connection = infoUrl.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inStream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
                StringBuilder strber = new StringBuilder();
                while ((line = reader.readLine()) != null)
                    strber.append(line + "\n");
                inStream.close();
                // 从反馈的结果中提取出IP地址
                int start = strber.indexOf("{");
                int end = strber.indexOf("}");
                String json = strber.substring(start, end + 1);
                if (json != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        line = jsonObject.optString("cip");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    /**
     * byte 转数组
     */
    public static String byte2String(byte[]byteArray){
        char []result=new char[byteArray.length*2];
        for(int i=0;i<byteArray.length;i++){
            char temp= (char)(((byteArray[i]&0xf0)>>4)&0x0f);
            result[i*2]=(char)(temp>9?'A'+temp-10:'0'+temp);
            temp=(char)(byteArray[i]&0x0f);
            result[i*2+1]=(char) (temp>9?'A'+temp-10:'0'+temp);
        }
        return new String(result);
    }


    public static String convertStreamToString(InputStream is) {
        /*
          * To convert the InputStream to String we use the BufferedReader.readLine()
          * method. We iterate until the BufferedReader return null which means
          * there's no more data to read. Each line will appended to a StringBuilder
          * and returned as String.
          */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    /** 验证是否为空开始****/
    public static boolean isNull(String val) {
        return ((val == null) || (val.trim().length() <= 0));
    }
    public static boolean isNull(Object val) {
        return (val == null);
    }
    public static boolean isNull(List val) {
        return (val == null || val.size() <= 0);
    }


    //解决excel类型问题，获得数值
    public static String getCellValue(Cell cell) {
        String value = "";
        if(null==cell){
            return value;
        }
        switch (cell.getCellType()) {
            //数值型
            case Cell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    //如果是date类型则 ，获取该cell的date值
                    Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    value = format.format(date);;
                }else {// 纯数字
                    BigDecimal big=new BigDecimal(cell.getNumericCellValue());
                    value = big.toString();
                    //解决1234.0  去掉后面的.0
                    if(null!=value&&!"".equals(value.trim())){
                        String[] item = value.split("[.]");
                        if(1<item.length&&"0".equals(item[1])){
                            value=item[0];
                        }
                    }
                }
                break;
            //字符串类型
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue().toString();
                break;
            // 公式类型
            case Cell.CELL_TYPE_FORMULA:
                //读公式计算值
                value = String.valueOf(cell.getNumericCellValue());
                if (value.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串
                    value = cell.getStringCellValue().toString();
                }
                break;
            // 布尔类型
            case Cell.CELL_TYPE_BOOLEAN:
                value = " "+ cell.getBooleanCellValue();
                break;
            // 空值
            case Cell.CELL_TYPE_BLANK:
                value = "";
                //LogUtil.getLogger().error("excel出现空值");
                break;
            // 故障
            case Cell.CELL_TYPE_ERROR:
                value = "";
                //LogUtil.getLogger().error("excel出现故障");
                break;
            default:
                value = cell.getStringCellValue().toString();
        }
        if("null".endsWith(value.trim())){
            value="";
        }
        return value;
    }


    public static List<String>  readFileDirectory(String commonpath)  {

        //smb://xxx:xxx@192.168.2.188/testIndex/
        //xxx:xxx是共享机器的用户名密码
        List<String> stringList = null ;//new ArrayList<String>() ;
        try {
            SmbFile file = new SmbFile(commonpath);
            if( !Util.isNull(file) &&  file.exists() ){
                SmbFile[] files = file.listFiles();
                stringList = new ArrayList<String>() ;
                for(SmbFile f : files){
                    if(f.isDirectory()){//文件夹
                        stringList.add(f.getName().substring(0,f.getName().length()-1)) ;
                        Log.i("读取到目录文件：",f.getName().substring(0,f.getName().length()-1)) ;
                    }
                }
            }
        }catch (Exception e){
            Log.e("读取共享目录文件夹失败",e.getMessage()) ;
        }
        return stringList ;
    }


    public static List<SmbFile>  readLastSortFile(String LastPath)  {
        List<SmbFile> refiles = null ;
        try {
            SmbFile file = new SmbFile(LastPath);
            if( !Util.isNull(file) &&  file.exists() ){
                SmbFile[] files = file.listFiles();
                refiles = getFileSortByModified(files) ;
                /*
                for(SmbFile f : files){
                    if(!f.isDirectory()){//文件夹
                        Calendar cal = Calendar.getInstance();
                        long time = f.getLastModified();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        cal.setTimeInMillis(time);
                        System.out.println("文件名称："+f.getName()+",修改时间:" + formatter.format(cal.getTime()));
                    }
                }
                */
            }
        }catch (Exception e){
            Log.e("读取共享目录文件夹失败",e.getMessage()) ;
        }

        return refiles ;
    }

    /**
     * 获取目录下所有文件(按修改时间排序)
     * @param files
     * @return
     */
    public static List<SmbFile> getFileSortByModified(SmbFile [] files) {

          List<SmbFile> simFileList = Arrays.asList(files) ;
                //开始排序
              Collections.sort( simFileList, new Comparator<SmbFile>() {
                      public int compare(SmbFile file, SmbFile newFile) {
                          try {
                              if (file.lastModified() < newFile.lastModified()) {
                                  return 1;
                              } else if (file.lastModified() == newFile.lastModified()) {
                                  return 0;
                              } else {
                                  return -1;
                              }
                          } catch (SmbException e) {
                              e.printStackTrace();
                          }
                          return 1;
                      }
              });
        return simFileList;
    }

    /**
     *
     * 获取目录下所有文件
     *
     * @param realpath
     * @param files
     * @return

    public static List<SmbFile> getFiles(String realpath, List<SmbFile> files) {

        SmbFile realFile = new SmbFile(realpath);
        if (realFile.isDirectory()) {
            SmbFile[] subfiles = realFile.listFiles();
            for (SmbFile file : subfiles) {
                if (file.isDirectory()) {
                    getFiles(file.getAbsolutePath(), files);
                } else {
                    files.add(file);
                }
            }
        }
        return files;
    }
     */

}
