package com.github.jlouns.gradle.cpe.process.internal

import org.gradle.process.internal.DefaultExecAction
import org.gradle.internal.file.PathToFileResolver
import org.gradle.process.ExecResult
import org.gradle.process.internal.ExecHandle
import org.gradle.process.internal.streams.StreamsHandler
package org.gradle.process.internal.DefaultExecHandle

public class DefaultCrossPlatformExecAction extends DefaultExecAction {
    public DefaultCrossPlatformExecAction(PathToFileResolver fileResolver) {
        super(fileResolver);
    }
    public ExecHandle build() {
        // Embed new code here
        String executable = getExecutable();
        if (StringUtils.isEmpty(executable)) {
            throw new IllegalStateException("execCommand == null!");
        }

        StreamsHandler effectiveHandler = getEffectiveStreamsHandler();
        return new DefaultExecHandle(getDisplayName(), getWorkingDir(), executable, getAllArguments(), getActualEnvironment(),
                effectiveHandler, listeners, redirectErrorStream, timeoutMillis, daemon);
    }
}
