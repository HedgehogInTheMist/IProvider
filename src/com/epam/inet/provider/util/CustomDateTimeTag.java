package com.epam.inet.provider.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Hedgehog on 01.06.2016.
 */
public class CustomDateTimeTag extends TagSupport {
	
	private String format;
	
	private static final Logger LOGGER = LogManager.getLogger(CustomDateTimeTag.class);
    
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     *  Custom tag performs printing current date and time within the header area
     */
    public int doStartTag() {
        JspWriter out=pageContext.getOut(); //returns the instance of JspWriter
        try{
            Date d = new Date();
            SimpleDateFormat date = new SimpleDateFormat(" dd.MM.yyyy");
            SimpleDateFormat time = new SimpleDateFormat("/ H:mm");
            out.println(date.format(d)); //25.02.2013
            out.println(time.format(d)); //25.02.2013 16:03
        }catch(Exception e){
            System.out.println(e);
        }
        return SKIP_BODY;//will not evaluate the body content of the tag
    }
    
    public int doEndTag() {
        return EVAL_PAGE;
    }
}
