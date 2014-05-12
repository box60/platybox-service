package com.platybox.utils;

import org.apache.commons.codec.digest.DigestUtils;


public final class TokenGenerator {
	public static String generateToken (String token_data){
	    return DigestUtils.md5Hex(token_data + System.nanoTime());
	}
}
