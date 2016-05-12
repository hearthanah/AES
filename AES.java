import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class AES {
	
	public static int Nr;
	public static int Nk; //the interval
	public static final int Nb = 4;
//	public static boolean encrypt;
	public char[][] state;
	public char[][] key;
	public char[][] expandedKey;
	
	// -----------
	// Constructor
	// -----------
	public AES(int nr, int nk, int ekSize)
	{
		//initialize based on arguments
		this.Nr = nr;
		this.Nk = nk;
		expandedKey = new char[4][ekSize * 4];
		key = new char[4][this.Nk];
	}
	
	// -----------
	// main method
	// -----------
	public static void main(String [] args) throws IOException
	{
		File keyFile;
		File inputFile;
		BufferedReader br;
		BufferedReader key_br;
		BufferedReader expKey_br;
		int AESnum;
		AES aes;
		String option = args[0];
		
		if (args.length == 3)
		{
			// Because of the extra credit, three arguments means AES-128
			keyFile = new File(args[1]);
			inputFile = new File(args[2]);
			aes = new AES(10, 4, 11);
		}
		else if (args.length == 6)
		{
			keyFile = new File(args[4]);
			inputFile = new File(args[5]);
			AESnum = Integer.parseInt(args[2]);					
			
			switch (AESnum)
			{
			case 256:
				aes = new AES(14, 8, 15);
				break;
				
			case 192:
				aes = new AES(12, 6, 13);
				break;
				
			default: // AES default is AES-128
				aes = new AES(10, 4, 11);
				break;
			}
		}	
		else
		{
			System.out.println("Error: Incorrect number of arguments.");
			return;
		}
		
		br = new BufferedReader(new FileReader(inputFile));
		key_br = new BufferedReader(new FileReader(keyFile));
		expKey_br = new BufferedReader(new FileReader(keyFile));
		
		AESutilities u = new AESutilities();
		
		// Get the key from keyFile
		aes.getCipherKey(key_br);
		
		// also input keyFile into beginning of Key Expansion
		aes.initializeExpandedKey(expKey_br);
		char[][] expKEY = aes.expandedKey;
		System.out.println("\n");
		u.expandKey_256(expKEY);
		aes.printArray(expKEY);
		
		// before we can call runAES we need the keyExpansion
		
		aes.runAES(br);


	}
	
	private void runAES(BufferedReader b) throws IOException
	{
		String currentLine;

		while((currentLine = b.readLine()) != null)
		{
			this.createStateMatrix(currentLine);
		}
		
	}
	
	private void initializeExpandedKey(BufferedReader b) throws IOException {
		String line = b.readLine();
		StringBuilder sb;
		int index = 0;
		
		for (int col = 0; col < this.Nk; ++col)
		{
			for (int row = 0; row < 4; ++row)
			{
				sb = new StringBuilder();
				sb.append(line.charAt(index));
				index++;
				sb.append(line.charAt(index));
				index++;
				
				String hex = sb.toString();
				int temp = Integer.parseInt(hex, 16);
				this.expandedKey[row][col] = (char)temp;
			}
		}
//		System.out.println("\n");
//		this.printArray(this.expandedKey);
		
	}


	
	private void createStateMatrix(String currentLine)
	{		
		// initialize STATE to a new 4x4 matrix
		state = new char[4][4];
		
		StringBuilder sb;
		String line = "";
		int index = 0;
		
		// assert that the line is a length of 32 values
		line = this.padLine(currentLine); 
		
		// update STATE with hex characters from inputFile
		for (int col = 0; col < 4; ++col)
		{
			for(int row = 0; row < 4; ++row)
			{
				//TODO: check if the character is valid 
				
				// build each hex pair using stringbuilder
				sb = new StringBuilder();
				sb.append(line.charAt(index));
				index++;
				sb.append(line.charAt(index));
				index++;
				// this will show the hex characters that go into each array slot
						
				String hex = sb.toString();
				int temp = Integer.parseInt(hex, 16);
				
				this.state[row][col] = (char)temp;
			}
		}
	}
	
	private void getCipherKey (BufferedReader b) throws IOException
	{
		String line = b.readLine();
		StringBuilder sb;
		int index = 0;
		
		for (int col = 0; col < this.Nk; ++col)
		{
			for (int row = 0; row < 4; ++row)
			{
				sb = new StringBuilder();
				sb.append(line.charAt(index));
				index++;
				sb.append(line.charAt(index));
				index++;
				
				String hex = sb.toString();
				int temp = Integer.parseInt(hex, 16);
				this.key[row][col] = (char)temp;
			}
		}
	}
	
	
	
	public void encrypt()
	{
		
	}
	
	public void decrypt()
	{
		
	}
	
	
	private String padLine(String s) 
	{
		if(s.length() != 32)
		{
			if(s.length() < 32)
			{
				for(int i = s.length(); i<32; ++i)
					s = s + "0";
			}
			else 
				s = s.substring(0, 32);
		}
		return s;
	}
	
	// -----------
	// print array
	// -----------
	private void printArray(char[][] array)
	{
		for (int row = 0; row < array.length; ++row)
		{
			for (int col = 0; col < array[0].length; ++col)
			{
				System.out.print(Integer.toHexString( ((int)array[row][col]) ) + " ");
			}
			System.out.println();
		}
		System.out.println("\n");
	}
		
}
