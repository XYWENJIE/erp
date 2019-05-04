package com.benjamin.erp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.wicketstuff.rest.contenthandling.IObjectSerialDeserial;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDesSer implements IObjectSerialDeserial<String>{
	
	private Logger logger = LogManager.getLogger();
	
	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String serializeObject(Object target, String mimeType) {
		try {
			logger.info("序列化参数");
			return objectMapper.writeValueAsString(target);
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}

	@Override
	public <E> E deserializeObject(String source, Class<E> targetClass, String mimeType) {
		logger.info("反序列化参数");
		return null;
	}

}
