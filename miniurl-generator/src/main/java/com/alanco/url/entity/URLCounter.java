package com.alanco.url.entity;

import com.datastax.driver.mapping.annotations.*;
 
//Urlcounter table entity class
@Table(keyspace = "getShortie", name = "url",
readConsistency = "QUORUM",
writeConsistency = "QUORUM",
caseSensitiveKeyspace = false,
caseSensitiveTable = false)
public class URLCounter {

	@PartitionKey
	private int id;
	private int usage;

	public URLCounter() {

	}

	public URLCounter(int id, int usage) {

		this.id = id;
		this.usage = usage;
	}

	public int getid() {
		return this.id;
	}

	public void setid(int _id) {
		this.id = _id;
	}

	public int getusage() {
		return this.usage;
	}

	public void setusage(int _usage) {
		this.usage = _usage;
	}

	@Override
	public String toString() {
		return "User{" + "id='" + this.id + "', usage='" + this.usage + "'}";
	}
}
