/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package me.dotter.mobilegrid.action;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import me.dotter.mobilegrid.utils.WebAppUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/")
public class GenerateJSONAction {

    private static Logger logger = Logger.getLogger(GenerateJSONAction.class);
    
    @RequestMapping("/newFile")
    public void generateJSONFile(String fileName,String jsonString, HttpServletRequest request, HttpServletResponse response) {
        boolean flag = false;
        
        String result = "";
        if(null == fileName || null == jsonString || fileName.equals("") || jsonString.equals("")) {
            result = "fileName or jsonResult can not be null !";
        } else {
            flag =   WebAppUtils.createJSONFile(fileName,jsonString,request);
            if(flag) {
                result = "Success to save file!";
            } else {
                result = "Failed to save file!";
            }
        }
        WebAppUtils.outputJSONResult(result, response);
       // return "";
    }
    
    @RequestMapping("/output/{fileName}")
    public String output() {
        return "/page/output";
    }
    
}
