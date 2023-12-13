package com.server.sftp;

import com.jcraft.jsch.*;

import java.io.File;
import java.io.FileOutputStream;

public class DownloadToLocalDir {
	
	public static void main(String[] args) {
        Session session = null;
        Channel channel = null;
        ChannelSftp sftp = null;

        try {
            String fileName = "first.txt";
            JSch jSch = new JSch();
            session = jSch.getSession(SftpInfo.USERNAME, SftpInfo.HOST, SftpInfo.PORT);
            session.setPassword(SftpInfo.PASSWORD);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;

            sftp.get(fileName, new FileOutputStream(new File(fileName)));

        } catch (JSchException ex) {
            System.out.println("JSch Exception!");
            ex.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception!");
            throw new RuntimeException(e);
        } finally {
            assert sftp != null;
            sftp.disconnect();
            assert channel != null;
            channel.disconnect();
            assert session != null;
            session.disconnect();
            System.out.println("All connections are disconnected");
        }
    }

}
