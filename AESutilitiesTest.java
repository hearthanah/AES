import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class AESutilitiesTest {

	private AESutilities utils;
	
	@Before
	public void setup() {
		utils = new  AESutilities();
	}

	@Test
	public void testRotation() {
		char[] bytes = new char[] {65, 66, 67, 68};
		char[] expectedbytes = new char[] {66, 67, 68, 65};
		
		//char[] actualBytes = utils.rotateWord(bytes);
		char[] actualBytes = utils.rotateWord(bytes);
		
		assertArrayEquals(expectedbytes, actualBytes);
	}
	
	@Test
	public void testCoreSchedule()
	{
		char[] bytes = new char[] {0x0, 0x1, 0x2, 0x3};
		//rotate
		char[] rotatebytes = new char[] {0x1, 0x2, 0x3, 0x0};
		//sbox
		char[] sboxbyte = new char[] {};
		//rcon
		char[] expectedbytes = new char[] {};
		
		char[] actualBytes = this.utils.coreSchedule();
		assertArrayEquals(expectedbytes, actualBytes);
	}
	
	public void testSBox()
	{
		String s = "3E";
		
		char[] bytes; 
	}
	
	

}
