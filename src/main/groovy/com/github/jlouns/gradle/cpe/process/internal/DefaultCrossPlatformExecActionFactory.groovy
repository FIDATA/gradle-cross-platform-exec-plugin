package com.github.jlouns.gradle.cpe.process.internal

// import com.github.jlouns.gradle.cpe.process.internal.CrossPlatformExecActionFactory
import org.gradle.process.internal.DefaultExecActionFactory
import org.gradle.api.internal.file.FileResolver

public class DefaultCrossPlatformExecActionFactory extends DefaultExecActionFactory implements CrossPlatformExecActionFactory {
    private final FileResolver fileResolver;

    public DefaultCrossPlatformExecActionFactory(FileResolver fileResolver) {
        this.fileResolver = fileResolver;
        super(fileResolver)
    }

    @Override
    public ExecAction newExecAction() {
        return new DefaultCrossPlatformExecAction(fileResolver);
    }
}
