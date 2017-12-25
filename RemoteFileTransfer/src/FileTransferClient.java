import com.jcraft.jsch.ChannelSftp;

/**
 * 
 */

/**
 * @author amitb
 *
 */
public class FileTransferClient {

	public static void main(String[] args) {
		String host = "HOST";
		int port = 22;
		String username = "USERNAME";
		String password = "PASSWORD";

		// connects and starts session with remote host
		ChannelSftp sftpChannel = RemoteFileTransferService.connect(host, port, username, password);
		if (sftpChannel != null) {
			String downloadFilePath = "PATH_OF_FILE_TO_DOWNLOAD"; // path on the remote host
			String saveFilePath = "PATH_WHERE_FILE_TO_BE_SAVED"; // local path
			// downloads the file to local path
			RemoteFileTransferService.downloadRemoteFile(downloadFilePath, saveFilePath, sftpChannel);
			
			// disconnects the session
			RemoteFileTransferService.disconnect();
		}
	}
}
