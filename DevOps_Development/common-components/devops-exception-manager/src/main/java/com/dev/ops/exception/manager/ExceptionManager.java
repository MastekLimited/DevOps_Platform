package com.dev.ops.exception.manager;

import java.util.ResourceBundle;

import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.exception.types.Severity;
import com.dev.ops.exceptions.WrappedException;

public interface ExceptionManager {

	void logErrorEvent(WrappedException exception);

	void logErrorEvent(WrappedException exception, ContextInfo contextInfo);

	void logErrorEvent(WrappedException exception, ContextInfo contextInfo, Severity severity);

	void logErrorEvent(WrappedException exception, ContextInfo contextInfo, Severity severity, boolean printStacktrace);

	ResourceBundle getMessageResourceBundle();
}
