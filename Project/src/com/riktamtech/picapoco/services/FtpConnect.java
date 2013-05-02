package com.riktamtech.picapoco.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTPClient;

public class FtpConnect {

	private FTPClient ftpClient;

	/**
	 * Upload multiple files
	 * 
	 * @param userName
	 * @param password
	 * @param serverUrl
	 * @param targetDirectoryPath
	 * @param localFilePath
	 * @param targetFileName
	 * @param files
	 */
	public FtpConnect(String userName, String password, String serverUrl,
			String targetDirectoryPath, String localFilePath,
			String targetFileName, ArrayList<String> files) {

		try {
			FTPClient ftp = new FTPClient();
			ftp.connect("ftp.example.com");
			ftp.login("admin", "secret");
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);

			for (String file : files) {
				InputStream in = new FileInputStream(new File(file));
				ftp.storeFile(new File(file).getName(), in);
				in.close();
			}

			ftp.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
