package com.common.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 1. 用来判断包路径是否存在，若不存在，即创建文件夹；
 * 2. 将配好的模板文件写出到指定路径下的文件
 * 3. 从数据库获取表信息
 */
@Component
public class FreeMarkerUtil {

    @Autowired
    Configuration freeMarker;

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 判断包路径是否存在
     */
    private void pathJudgeExist(String path){
        File file = new File(path);
        if(!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 输出到文件
     */
    public  void printFile(Map<String, Object> root, Template template, String filePath, String fileName) throws Exception  {
        pathJudgeExist(filePath);
        File file = new File(filePath, fileName );
        if(!file.exists()) {
            file.createNewFile();
        }
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
        template.process(root,  out);
        out.close();
    }

    /**
     * 首字母大写
     */
    public  String capFirst(String str){
        return str.substring(0,1).toUpperCase()+str.substring(1).toLowerCase();
    }

    /**
     * 下划线命名转为驼峰命名
     */
    public String underlineToHump(String para){
        StringBuilder result=new StringBuilder();
        String[] names=para.split("_");
        for(String s:names){
            if(result.length()==0){
                result.append(s);
            }else{
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 获取类名
     */
    public String getEntityName(String tableName){
        return underlineToHump(capFirst(tableName.toLowerCase()));
    }

    /**
     * 获取首字母小写类名
     */
    public String getEntityNameLower(String tableName){
        return underlineToHump(tableName.toLowerCase());
    }

    /**
     * 将[数据库类型]转换成[Java类型],如果遇到没有写的类型,会出现Undefine,在后面补充即可
     */
    public  String convertToJava(String columnType){
        String result;
        switch (columnType){
            case "VARCHAR":{
                result = "String";
                break;
            }
            case "INT":{
                result = "Integer";
                break;
            }
            case "BIGINT":{
                result = "Long";
                break;
            }
            case "FLOAT":{
                result = "Float";
                break;
            }
            case "DOUBLE":{
                result = "Double";
                break;
            }
            case "DATETIME":{
                result = "Date";
                break;
            }
            case "BIT":{
                result = "Boolean";
                break;
            }
            default:{
                result = "Undefine";
                break;
            }
        }
        return result;
    }

    /**
     * 匹配字符串中的英文字符
     */
    public String matchResult(String str) {
        String regEx2 = "[a-z|A-Z]";
        Pattern pattern = Pattern.compile(regEx2);
        StringBuilder sb = new StringBuilder();
        Matcher m = pattern.matcher(str);
        while (m.find()){
            for (int i = 0; i <= m.groupCount(); i++)
            {
                sb.append(m.group());
            }
        }
        return sb.toString();
    }

    /**
     * 获取表信息
     */
    public  List<Map<String, String>> getDataInfo(String tableName){
        // mysql查询表结构的语句,如果是其他数据库,修改此处查询语句
        String sql = "show columns from "+tableName;
        List<Map<String, Object>> sqlToMap = jdbcTemplate.queryForList(sql);

        List<Map<String, String>> columns = new LinkedList<>();
        for (Map map : sqlToMap) {
            Map<String, String> columnMap = new HashMap<>(16);
            // 字段名称
            String columnName = map.get("Field").toString();
            columnMap.put("columnName", columnName);
            // 字段类型
            String columnType = map.get("Type").toString().toUpperCase();
            columnType = matchResult(columnType).trim();
            columnType = convertToJava(columnType);
            columnMap.put("columnType", columnType);
            // 成员名称
            columnMap.put("entityColumnNo", underlineToHump(columnName));
            columns.add(columnMap);
        }
        return columns;
    }

    /**
     * 生成代码
     */
    public void generate(Map<String, Object> root,String templateName,String saveUrl,String entityName) throws Exception {
        //获取模板
        Template template = freeMarker.getTemplate(templateName);
        //输出文件
        printFile(root, template, saveUrl, entityName);
    }
}