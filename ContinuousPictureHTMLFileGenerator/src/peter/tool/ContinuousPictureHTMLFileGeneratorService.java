package peter.tool;

import java.io.File;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ContinuousPictureHTMLFileGeneratorService {
	private char[] invalidChars = {'\\', '/', ':', '*', '?', '"', '<', '>', '|'};
	private char[] reservedCharacters = {'!', '*', '\'', '(', ')', ';', ':', '@', '&', '=', '+', '$', ',', '/', '?', '#', '[', ']'};
	private char[] partOfUnreservedCharacters = {'-', '_', '.', '~'};
	
	public File createFolder(PrintWriter pw, String parentFolderPath, String folderName){		
		for(int i = 0 ; i < invalidChars.length ; i++){
			folderName = folderName.replace(invalidChars[i], ' ');
		}
		
		if(parentFolderPath.indexOf(parentFolderPath.length() - 1) != '\\'){
			parentFolderPath += '\\';
		}
		
		File folder = new File(parentFolderPath + folderName + "\\");
		if(!folder.mkdir()){
			System.out.println(new Date() + " " + parentFolderPath + folderName + "\\ " + "資料夾建立失敗或資料夾已存在");
			pw.println(new Date() + " " + parentFolderPath + folderName + "\\ " + "資料夾建立失敗或資料夾已存在");
		}
		return folder;
	}
	
	public String convertNonAsciiToUrlEcoding(String urlStr) throws UnsupportedEncodingException{
		StringBuilder urlSB = new StringBuilder(urlStr);
		
		for(int i = 0 ; i < urlSB.length() ; i++){
			char letter = urlSB.charAt(i);
			
			if(letter == '#'){
				urlSB.replace(i, i + 1, "%23");
				i += 2;
			}else if(checkIsReservedCharacters(letter)){
				continue;
			}else if(checkIsUnreservedCharacters(letter)){
				continue;
			}else if(letter == ' '){
				urlSB.replace(i, i + 1, "%20");
				i += 2;
			}else{
				int before = urlSB.length();
				urlSB.replace(i, i + 1, URLEncoder.encode(String.valueOf(letter), "UTF-8"));
				i = i + (urlSB.length() - before);
			}			
		}
		
		return urlSB.toString();
	}
	
	public boolean checkIsReservedCharacters(char letter){
		boolean result = false;
		
		for(int i = 0 ; i < reservedCharacters.length ; i++){
			if(letter == reservedCharacters[i]){
				result = true;
			}
		}
		
		return result;
	}
	
	public boolean checkIsUnreservedCharacters(char letter){
		boolean result = false;
		
		if(letter >= 'A' && letter <= 'Z'){
			result = true;
		}
		if(letter >= 'a' && letter <= 'z'){
			result = true;
		}
		if(letter >= '0' && letter <= '9'){
			result = true;
		}
		
		for(int i = 0 ; i < partOfUnreservedCharacters.length ; i++){
			if(letter == partOfUnreservedCharacters[i]){
				result = true;
			}
		}
		
		return result;		
	}
	
	public List<String> getFileNameList(String sourceFolderPath){
		ArrayList<String> fileNameList = new ArrayList<>(Arrays.asList(new File(sourceFolderPath).list()));
		Collections.sort(fileNameList, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return Integer.parseInt(s1.substring(0, s1.indexOf('.'))) - Integer.parseInt(s2.substring(0, s2.indexOf('.')));
			}
		});
		return fileNameList;
	}
	
	public void convertFileNameToUrlEcoding(List<String> fileNameList) throws UnsupportedEncodingException{
		for(int i = 0 ; i < fileNameList.size() ; i++){
			fileNameList.set(i, convertNonAsciiToUrlEcoding(fileNameList.get(i)));
		}
	}
	
	public void removeMergedPictureName(List<String> fileNameList){
		fileNameList.remove("all.jpg");
	}
	
	public String getFolderName(String comicFolderPath){
		return new File(comicFolderPath).getName();
	}
	
	public int getFileAmount(List<String> fileNameList){
		return fileNameList.size();
	}
	
	public String convertBackSlashToSlash(String comicFolderPath){
		return comicFolderPath.replace('\\', '/');
	}
	
	public String concatenateOneSlash(String comicFolderPath){
		return comicFolderPath + "/";
	}
	
	public String addFilePrefix(String comicFolderPath){
		return "file:///" + comicFolderPath;
	}
}
