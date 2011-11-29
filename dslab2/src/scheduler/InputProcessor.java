package scheduler;

import java.util.Arrays;

/**
 * manages the input coming from the clientSocket
 * @author babz
 *
 */
public class InputProcessor {
	public static UserCommand processInput(String inputCommand) {
		String[] cmdArray = inputCommand.split(" ");
		if(cmdArray.length == 0) {
			return null;
		}

		//cmds:	REQUESTENGINE, EXECUTETASK, EXIT
		String cmd = cmdArray[0];

		//!requestEngine <taskId>
		if(cmd.equals("!requestEngine")) {
			String[] args = removeCmd(cmdArray);
			if(args.length != 2) {
				return null;
			}
			return new UserCommand(UserCommand.Cmds.REQUESTENGINE, args);
		}
		//!exit
		else if (cmd.equals("!exit")) {
			return new UserCommand(UserCommand.Cmds.EXIT, null);
		}

		return null;
	}

	private static String[] removeCmd(String[] cmd) {
		return Arrays.copyOfRange(cmd, 1, cmd.length);
	}
}
