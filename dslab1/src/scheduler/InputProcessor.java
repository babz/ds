package scheduler;

import java.util.Arrays;

import javax.jws.soap.SOAPBinding.Use;

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
		
		String cmd = cmdArray[0];

		//divide only when there are params
		if(cmdArray.length > 1) {
			String[] args = removeCmd(cmdArray);

			if(cmd.equals("!login")) {
				if(args.length != 2) {
					return null;
				}
				return new UserCommand(UserCommand.Cmds.LOGIN, args);
			}
		} else {
			if(cmd.equals("!logout")) {
				return new UserCommand(UserCommand.Cmds.LOGOUT, null);
			} else if (cmd.equals("!list")) {
				return new UserCommand(UserCommand.Cmds.LIST, null);
			} else if (cmd.equals("!exit")) {
				return new UserCommand(UserCommand.Cmds.EXIT, null);
			}
		}

		return null;
	}


	private static String[] removeCmd(String[] cmd) {
		return Arrays.copyOfRange(cmd, 1, cmd.length);
	}
}
