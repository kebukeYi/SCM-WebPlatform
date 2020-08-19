package com.car.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.*;

/**
 * @Author : mmy
 * @Creat Time : 2019/12/30  21:32
 * @Description
 */
public class ReadJSONFile {
    /*
//list转换为json
List<CustPhone> list = new ArrayList<CustPhone>();
String str=JSON.toJSON(list).toString();
//json转换为list
  List<Person> list = new ArrayList<Person>();
  list = JSONObject.parseArray(jasonArray, Person.class);
 */

    public static String readFileContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }


    /*
     * 读取配置文件
     */
    public static String readFile ( InputStream inputStream ) throws IOException {

        StringBuilder builder = new StringBuilder();
        try {
            InputStreamReader reader = new InputStreamReader(inputStream , "UTF-8" );
            BufferedReader bfReader = new BufferedReader( reader );
            String tmpContent = null;
            while ( ( tmpContent = bfReader.readLine() ) != null ) {
                builder.append( tmpContent );
            }
            bfReader.close();
        } catch ( UnsupportedEncodingException e ) {
            // 忽略
        }
        return filter( builder.toString() );
    }
    // 过滤输入字符串, 剔除多行注释以及替换掉反斜杠
    public static String filter ( String input ) {
        return input.replaceAll( "/\\*[\\s\\S]*?\\*/", "" );
    }

    public static void main(String[] args) {
        ClassPathResource classPathResource = new ClassPathResource("static/json/Alarms.json");
        InputStream inputStream = null;
        try {
            inputStream = classPathResource.getInputStream();
            System.out.println(readFile(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
