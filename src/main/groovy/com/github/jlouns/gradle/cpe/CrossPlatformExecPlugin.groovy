package com.github.jlouns.gradle.cpe

import com.github.jlouns.gradle.cpe.process.internal.DefaultCrossPlatformExecAction
import com.github.jlouns.gradle.cpe.tasks.CrossPlatformExec
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.process.ExecResult
import static org.gradle.util.ConfigureUtil.configureUsing

/**
 * Created on 1/26/15
 *
 * @author jlouns
 */
class CrossPlatformExecPlugin implements Plugin<Project> {

	@Override
	void apply(Project project) {
		addMethod(project, 'crossPlatformExec', crossPlatformExec)
		addTask(project, CrossPlatformExec)
	}

	static ExecResult crossPlatformExec(Object action) {
		if (action instanceof Closure) {
		  action = configureUsing(closure)
		}
		// TODO: return getProcessOperations().exec(action);
		ExecAction execAction = new DefaultCrossPlatformExecAction(fileResolver /* TODO */) // TODO
		action.execute(execAction)
		return execAction.execute()
	}

	static void addMethod(Project project, String methodName, Closure method) {
		// project.ext[methodName] = method
		project.extensions.extraProperties.set(methodName, method)
	}

	static void addTask(Project project, Class type) {
		project.extensions.extraProperties.set(type.getSimpleName(), type)
	}

}
