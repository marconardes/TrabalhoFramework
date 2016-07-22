package org.cbsoft.framework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompositePostProcessor implements PostProcessor {

	private List<PostProcessor> processors;

	
	
	public CompositePostProcessor(PostProcessor... lista) {
		 processors = new ArrayList<PostProcessor>();
		 for(PostProcessor p: lista)
		 {
			processors.add(p);
		 }

	}



	@Override
	public byte[] postProcess(byte[] bytes) throws IOException {
		byte[] current = bytes;
		
		for(PostProcessor p:  processors)
		{
			bytes = p.postProcess(bytes);
		}
		
		return bytes;
	}

}
