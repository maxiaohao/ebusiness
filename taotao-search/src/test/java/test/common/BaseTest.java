package test.common;


import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;



@ContextConfiguration({
	"/spring/applicationContext-*.xml"})
public class BaseTest extends AbstractJUnit4SpringContextTests{
	
	
	
}
