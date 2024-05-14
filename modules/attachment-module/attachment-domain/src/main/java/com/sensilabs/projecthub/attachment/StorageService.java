package com.sensilabs.projecthub.attachment;

import java.io.InputStream;

public interface StorageService {
	String save(InputStream file);

	byte[] get(String path);
}
