package io.github.alexeymartynov.horsecontainers.gui;

public class Util {

	public static String getStringWithoutColor(String string) 
	{
		if(string.contains("§"))
		{
			char[] chars = string.toCharArray();
			for(int i = 0; i < chars.length; i++) 
			{
				if(chars[i] != '§') continue;
				
				chars[i + 1] = '$';
				chars[i] = '$';
				
				i = 0;
			}
			
			return new String(chars).replace("$", "");
		}
		
		return string;
	}
}
