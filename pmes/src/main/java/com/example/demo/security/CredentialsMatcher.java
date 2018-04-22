package com.example.demo.security;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

public class CredentialsMatcher extends HashedCredentialsMatcher{
	
	private CredentialsMatcher(String hashAlgorithmName){
		this.setHashAlgorithmName(hashAlgorithmName);
	}
    
	private static volatile CredentialsMatcher instance=null;
	
    public static CredentialsMatcher getInstance(String hashAlgorithmName){
            synchronized(CredentialsMatcher.class){
                if(instance==null){
                    instance=new CredentialsMatcher(hashAlgorithmName);
                }
            }
        return instance;
    }
}