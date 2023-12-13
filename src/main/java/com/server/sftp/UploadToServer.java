package com.server.sftp;

import com.jcraft.jsch.*;

import java.io.File;
import java.io.FileInputStream;

public class UploadToServer {
	
	 public static void main(String[] args) {
	        Session session         = null;
	        Channel channel         = null;
	        ChannelSftp channelSftp = null;

	        try {
	            JSch jSch = new JSch();
	            session = jSch.getSession(SftpInfo.USERNAME, SftpInfo.HOST, SftpInfo.PORT);
	            session.setPassword(SftpInfo
	            		.PASSWORD);
	            session.setConfig("StrictHostKeyChecking", "no");
	            session.connect();
	            if (session.isConnected())
	                System.out.println("Session has connect!");

	            channel = session.openChannel("sftp");
	            channel.connect();
	            if (channel.isConnected())
	                System.out.println("Channel has connect!");
	            channelSftp = (ChannelSftp) channel;

	            String fileName = "test.txt";
	            File file = new File(fileName);
	            if (file.createNewFile())
	                System.out.println("File created.");

	            channelSftp.put(new FileInputStream(file), fileName);
	        } catch (JSchException ex) {
	            System.out.println("JSch Exception!");
	            ex.printStackTrace();
	        } catch (Exception e) {
	            System.out.println("Exception!");
	            e.printStackTrace();
	            throw new RuntimeException(e);
	        } finally {
	            assert channelSftp != null;
	            channelSftp.disconnect();
	            assert channel != null;
	            channel.disconnect();
	            assert session != null;
	            session.disconnect();
	            System.out.println("All connections are disconnected");
	        }
	    }

}
