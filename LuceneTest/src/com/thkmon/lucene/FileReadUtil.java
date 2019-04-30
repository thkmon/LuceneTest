package com.thkmon.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileReadUtil {
	
	/**
	 * 파일 읽어서 ArrayList 로 리턴
	 * 
	 * @param file
	 * @param encode
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static ArrayList<String> readFile(File file, String encode) throws IOException, Exception {
		if (file == null || !file.exists()) {
			return null;
		}

		ArrayList<String> resultList = null;

		FileInputStream fileInputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;

		try {
			fileInputStream = new FileInputStream(file);
			inputStreamReader = new InputStreamReader(fileInputStream, encode);
			bufferedReader = new BufferedReader(inputStreamReader);

			String oneLine = null;
			while ((oneLine = bufferedReader.readLine()) != null) {
				if (resultList == null) {
					resultList = new ArrayList<String>();
				}

				resultList.add(oneLine);
			}

		} catch (IOException e) {
			throw e;

		} catch (Exception e) {
			throw e;

		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (Exception e) {
				bufferedReader = null;
			}

			try {
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
			} catch (Exception e) {
				inputStreamReader = null;
			}

			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (Exception e) {
				fileInputStream = null;
			}
		}

		return resultList;
	}
	
	
	/**
	 * 파일 읽어서 String 으로 리턴
	 * 
	 * @param file
	 * @param encode
	 * @param delimiter
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String readFileToString(File file, String encode, String delimiter) throws IOException, Exception {
		StringBuffer buff = new StringBuffer();
		
		ArrayList<String> strList = readFile(file, encode);
		if (strList != null && strList.size() > 0) {
			int listCount = strList.size();
			for (int i=0; i<listCount; i++) {
				buff.append(strList.get(i));
				buff.append(delimiter);
			}
		}
		
		return buff.toString();
	}
}