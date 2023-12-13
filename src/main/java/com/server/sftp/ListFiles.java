package com.server.sftp;

import com.jcraft.jsch.*;

import java.util.Vector;

public class ListFiles {
	
	public static void main(String[] args) {
        Session session         = null;
        Channel channel         = null;
        ChannelSftp channelSftp = null;

        try {
            JSch jSch = new JSch();
            session = jSch.getSession(SftpInfo.USERNAME, SftpInfo.HOST, SftpInfo.PORT);
            session.setPassword(SftpInfo.PASSWORD);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
            channelSftp.cd(SftpInfo.DIR);
            Vector files = channelSftp.ls(SftpInfo.DIR);

            for (Object file : files) {
                System.out.println(file.toString());

//                ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) file;
//                System.out.println(entry.getFilename());
            }
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
        }
    }

}
