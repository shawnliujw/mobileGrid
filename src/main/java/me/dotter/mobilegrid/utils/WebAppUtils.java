/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.dotter.mobilegrid.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Administrator
 */
public class WebAppUtils {

    private static Logger logger = Logger.getLogger(WebAppUtils.class);

    /**
     * Transform and output json data.
     *
     * @param obj
     * @param response
     * @author shawn
     */
    public static void outputJSONResult(Object obj, HttpServletResponse response) {
        PrintWriter pw = null;
        try {
            response.setHeader("ContentType", "text/json");
            response.setCharacterEncoding("utf-8");

            ObjectMapper mapper = new ObjectMapper();
            pw = response.getWriter();
//			 System.out.println(mapper.writeValueAsString(obj));
            if (null != obj && !"".equals(obj)) {

                mapper.writeValue(pw, obj);
            } else {
                mapper.writeValue(pw, "");

            }
//			pw.write(result);
            pw.flush();
            // mapper.
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * Generate json file.
     *
     * @param fileName
     * @author Shawn
     * @return
     */
    public static boolean createJSONFile(String fileName,String jsonData, HttpServletRequest request) {
        boolean flag = false;

        try {
            String path = request.getSession().getServletContext().getRealPath("/data");
            //path += "/" + fileName +".json";
            File file = new File(path , fileName + ".json");
            if (!file.exists()) {
                flag = file.createNewFile();
            }
            
            if(flag) {
                FileWriter fileWriter = new FileWriter(path + "/" +fileName + ".json");
                fileWriter.write(jsonData);
                fileWriter.close();
            }
            
        } catch (IOException ex) {
            //java.util.logging.Logger.getLogger(WebAppUtils.class.getName()).log(Level.SEVERE, null, ex);
            logger.error("create file error!", ex);
        }
        return flag;
    }
    
    /**
     * Read json file .
     * 
     * @param fileName
     * @param request
     * @return 
     */
    public static String readJsonFile(String fileName, HttpServletRequest request){
        String result = "";
        String path = request.getSession().getServletContext().getRealPath("/data");
        
        File f = new File(path + "/" + fileName + ".json");
        if(f.exists()) {
            try {
                FileReader fileReader = new FileReader(f);
               // int count = 
               int count = fileReader.read();
               if(count != -1) {
                   System.out.print((char)count);
               }
               System.out.println();
               System.out.println("----------------------------------------");
               
               BufferedReader br = new BufferedReader(fileReader);
                System.out.println("以行为单位读取文件内容，一次读一整行：");
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = br.readLine()) != null) {
                // 显示行号
                System.out.println("line " + line + ": " + tempString);
                result += tempString;
                line++;
            }
            br.close();

            } catch (FileNotFoundException ex) {
                //java.util.logging.Logger.getLogger(WebAppUtils.class.getName()).log(Level.SEVERE, null, ex);
                logger.error("failed to new FileReader for '"+fileName+".json'",ex);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(WebAppUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else {
            result = "failed to read file '"+fileName +".json', file doesn't exist!";
        }
        
        return result;
    }
    
}
