
public class AESutilities {

	public int keyNum=0;
	
	// -----------
	// key expansion methods
	// -----------
	public char[] rotateWord(char[] word)
	{
		char hold = word[0]; // goes to the end
		char[] newWord = new char[word.length];
		
		for(int i= 0; i<newWord.length-1; i++)
			newWord[i] = word[i+1];
		
		newWord[word.length-1] = hold;

		return newWord;
	}
	
	public char[] subWord(char[] arr)
	{
		char[] result = new char[4];
		for(int i= 0; i < arr.length; ++i)
		{
			char sbox = sboxLookup(arr[i]);
			result[i] = sbox;
		}
		return result;
	}
	
	public char[] xorRcon(char[] arr)
	{
		//call rCon on every odd number of 4x4 key
		char firstByte = arr[0];
		char xorValue = (char) (firstByte ^ rconLookup(keyNum));
		arr[0] = xorValue;
		
		return arr;
	}
	
	public void expandKey_256(char[][] e)
	{
		int keyNum = 0;		
		int index = 8;
		while(index != 60)
		{
			char[] word = new char[4];
			char[] w = new char[4];
			char[] newCol = new char[4];
			
			// getting column we need
			for (int row = 0; row < 4; ++row)
			{
				word[row] = e[row][index - 1]; 				
			}
			
			if (index % 8 == 0)
			{
				// rotate the word
				word = rotateWord(word);
				
				// use S-box substitution on word
				word = subWord(word);
				
				// rcon 
				word = xorRcon(word);
				
				// xor with a column
				for (int row = 0; row < 4; ++row)
				{
					w[row] = e[row][index - 8];
				}
	
				word = xorColumn(word, w);
				// Now enter word back into the Expanded Key
				for (int row = 0; row < 4; ++row)
				{
					e[row][index] = word[row];
				}
				
			}
			else 
			{
				if (index % 4 == 0)
				{
					// do subWord
					word = subWord(word);
				}
				
				for (int row = 0; row < 4; ++row)
				{
					w[row] = e[row][index - 8];
				}
				word = xorColumn(word, w);
				
				// Now enter word back into the Expanded Key
				for (int row = 0; row < 4; ++row)
				{
					e[row][index] = word[row];
				}
				
			}
			index++;
		}
	}
	
	private char[] xorColumn(char[] word, char[] w) 
	{
		char[] result = new char[4];
		
		for (int i = 0; i < word.length; ++i)
		{
			char byte1 = word[i];
			char byte2 = w[i];
			char xorValue = (char) (byte1 ^ byte2);
			result[i] = xorValue;
		}
		return result;
	}

	public char[] coreSchedule() {
		return null;
	}
	
	public char sboxLookup(char c)
	{
		Constants con = new Constants();
		char value = con.sbox[(int) c];
		return value;
	}
	
	public char rconLookup(int keyNum)
	{
		Constants con = new Constants();
		char value = con.rcon[keyNum];
		return value;
	}
	
	public int hexToCharNum(String s)
	{
		String hex = s;
		int charNum = Integer.parseInt(hex, 16);
		return charNum;
	}
	
	public void subBytes()
	{
		
	}
	
	public void shiftRows()
	{
		
	}
	
	public void mixColumns()
	{
		
	}
	
	public void addRoundKey()
	{
		
	}

}
