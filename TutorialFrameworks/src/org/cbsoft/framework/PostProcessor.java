package org.cbsoft.framework;

import java.io.IOException;

public interface PostProcessor {
	abstract byte[] postProcess(byte[] bytes) throws IOException;

}
