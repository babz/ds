package scheduler;

import scheduler.UserCommand.Cmds;

public class UserCommand {
	public enum Cmds { LOGIN, LOGOUT, LIST, PREPARE, REQUESTENGINE, EXECUTETASK, EXIT}

	private Cmds cmd;
	private String[] args;
	
	public UserCommand(Cmds cmd, String[] args) {
		this.cmd = cmd;
		this.args = args;
	}

	public Cmds command() { 
		return cmd;
	}
	
	public String getArg(int position) {
		//TODO check array length
		return args[position];
	}
}
