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

		if(cmdArray.length > 1) {
			String[] args = removeCmd(cmdArray);

			if(cmdArray[0].equals("!login")) {
				if(args.length != 2) {
					return null;
				}
				return new UserCommand(UserCommand.Cmds.LOGIN, args);
			}
		}

		return null;
	}


	private static String[] removeCmd(String[] cmd) {
		return Arrays.copyOfRange(cmd, 1, cmd.length);
	}
}
