package com.github.jlouns.gradle.cpe.tasks

import org.gradle.api.tasks.AbstractExecTask
import org.gradle.api.tasks.TaskAction

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class CrossPlatformExec extends AbstractExecTask {
	private static final def windowsExtensions = ['bat', 'cmd', 'exe'];
	private static final def unixExtensions = [null, 'sh'];

	private boolean windows;

	public CrossPlatformExec() {
		super(CrossPlatformExec.class);
		String os = System.getProperty('os.name').toLowerCase();
		windows = os.contains('windows');
	}

	@Override
	@TaskAction
	protected void exec() {
		List<String> commandLine = this.getCommandLine();

		if (!commandLine.isEmpty()) {
			commandLine[0] = findCommand(workingDir, commandLine[0], windows);
		}

		if (windows) {
			if (!commandLine.isEmpty() && commandLine[0]) {
				commandLine
			}
			commandLine.add(0, '/c');
			commandLine.add(0, 'cmd');
		}

		this.setCommandLine(commandLine);

		super.exec();
	}

	private static String findCommand(File workingDir, String command, boolean windows) {
		command = normalizeCommandPaths(command);
		def extensions = windows ? windowsExtensions : unixExtensions;

		return extensions.findResult(command) { extension ->
			Path commandFile
			if (extension) {
				commandFile = Paths.get(command + '.' + extension);
			} else {
				commandFile = Paths.get(command);
			}

			return resolveCommandFromFile(workingDir, commandFile, windows);
		};
	}

	private static String resolveCommandFromFile(File workingDir, Path commandFile, boolean windows) {
		if (!Files.isExecutable(commandFile)) {
			return null;
		}

		Path cwd = Paths.get(workingDir.absolutePath).toAbsolutePath().normalize();

		String resolvedCommand = cwd.relativize(commandFile.toAbsolutePath().normalize());

		if (!windows && !resolvedCommand.startsWith('.')) {
			resolvedCommand = '.' + File.separator + resolvedCommand;
		}

		return resolvedCommand;
	}

	private static String normalizeCommandPaths(String command) {
		// need to escape backslash so it works with regex
		String backslashSeparator = '\\\\';

		String forwardSlashSeparator = '/';

		// escape separator if it's a backslash
		char backslash = '\\';
		String separator = File.separatorChar == backslash ? backslashSeparator : File.separator

		return command
			// first replace all of the backslashes with forward slashes
			.replaceAll(backslashSeparator, forwardSlashSeparator)
			// then replace all forward slashes with whatever the separator actually is
			.replaceAll(forwardSlashSeparator, separator);
	}
}
