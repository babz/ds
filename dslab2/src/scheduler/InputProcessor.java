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

		//cmds:	LOGIN, LOGOUT, REQUESTENGINE, EXECUTETASK, INFO, EXIT
		String cmd = cmdArray[0];

		//divide only when there are params
		if(cmdArray.length > 1) {
			String[] args = removeCmd(cmdArray);
			//!login <company> <password>
			if(cmd.equals("!login")) {
				if(args.length != 2) {
					return null;
				}
				return new UserCommand(UserCommand.Cmds.LOGIN, args);
			}
			//!requestEngine <taskId>
			else if(cmd.equals("!requestEngine")) {
				if(args.length != 2) {
					return null;
				}
				return new UserCommand(UserCommand.Cmds.REQUESTENGINE, args);
			}
			//!executeTask <taskId> <startScript>
			else if(cmd.equals("!executeTask")) {
				if(args.length != 2) {
					return null;
				}
				return new UserCommand(UserCommand.Cmds.EXECUTETASK, args);
			}
			//!info <taskId>
			else if(cmd.equals("!info")) {
				if(args.length != 1) {
					return null;
				}
				return new UserCommand(UserCommand.Cmds.INFO, args);
			}
		} 
		//commands with no params
		else {
			//!logout
			if(cmd.equals("!logout")) {
				return new UserCommand(UserCommand.Cmds.LOGOUT, null);
			} 
			//!exit
			else if (cmd.equals("!exit")) {
				return new UserCommand(UserCommand.Cmds.EXIT, null);
			}
		}

		return null;
	}


	private static String[] removeCmd(String[] cmd) {
		return Arrays.copyOfRange(cmd, 1, cmd.length);
	}
}
