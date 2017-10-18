package com.github.jlouns.gradle.cpe.tasks

import org.gradle.api.tasks.AbstractExecTask
import com.github.jlouns.gradle.cpe.process.internal.CrossPlatformExecActionFactory

class CrossPlatformExec extends AbstractExecTask {
  @Inject
  @Override
  protected CrossPlatformExecActionFactory getExecActionFactory() {
    throw new UnsupportedOperationException();
  }
}
