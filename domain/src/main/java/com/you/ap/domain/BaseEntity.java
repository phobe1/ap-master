package com.you.ap.domain;

/**
 * @author: ${data.auth} 
 * @date: ${data.datetime}
 * @since ${data.version}
 */
public class BaseEntity<T> {

    protected T id;

    public T getId() {
    	return id;
    }

    public void setId(T id) {
    	this.id = id;
    }
}