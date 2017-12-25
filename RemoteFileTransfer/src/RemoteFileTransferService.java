import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * @author amitb
 *
 */
public class RemoteFileTransferService {

	private static Session sshSession;

	public static ChannelSftp connect(String host, int port, String username, String password) {
		ChannelSftp sftpChannel = null;
		JSch jsch = new JSch();

		try {
			// host login info
			sshSession = jsch.getSession(username, host, port);
			sshSession.setPassword(password);

			Properties sshConfiguration = new Properties();
			sshConfiguration.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfiguration);

			// connects to the remote host
			sshSession.connect();

			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftpChannel = (ChannelSftp) channel;
		} catch (JSchException e) {
			// problem connecting remote host
			e.printStackTrace();
		}

		return sftpChannel;
	}

	public static void disconnect() {
		if (sshSession != null) {
			sshSession.disconnect();
		}
	}

	public static void downloadRemoteFile(String downloadFilePath, String saveFilePath, ChannelSftp sftpChannel) {
		FileOutputStream fileOutputStream = null;
		try {
			File outputFile = new File(saveFilePath);
			fileOutputStream = new FileOutputStream(outputFile);
			sftpChannel.get(saveFilePath, fileOutputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		} finally {
			if(fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
