package test.common;


import java.io.FileNotFoundException;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.util.Log4jConfigurer;



@ContextConfiguration({
	"/spring/applicationContext-*.xml"})
public abstract class BaseTest extends AbstractJUnit4SpringContextTests{
	
	static { 
	      try { 
	        Log4jConfigurer.initLogging("classpath:properties/log4j.xml"); 
	      } catch (FileNotFoundException ex) { 
	        System.err.println("Cannot Initialize log4j"); 
	      } 
	    } 
	
}
