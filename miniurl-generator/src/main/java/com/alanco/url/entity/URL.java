package com.alanco.url.entity;

import com.datastax.driver.mapping.annotations.*;

//Url table entity class
@Table(keyspace = "getShortie", name = "url",
	readConsistency = "QUORUM",
	writeConsistency = "QUORUM",
	caseSensitiveKeyspace = false,
	caseSensitiveTable = false)
public class URL {
	
	@PartitionKey
	@Column(name = "id")
	private int id;
	private String longname;
	private String createddate;
	private String lastusedate;
	private String shortcode;
    
    public URL() {
    	
    }
    
    public URL(int id, String longname, String createddate, String lastusedate, String shortcode) {
        
    	this.id = id;
        this.longname = longname;
        this.createddate = createddate;
        this.lastusedate = lastusedate;
        this.shortcode = shortcode;
    }
    
    public int getid() {
        return this.id;
    }

    public void setid(int _id) {
        this.id = _id;
    }

    public String getlongname() {
        return this.longname;
    }

    public void setlongname(String _longname) {
        this.longname = _longname;
    }

    public String getcreateddate() {
        return this.createddate;
    }

    public void setcreateddate(String _createddate) {
        this.createddate = _createddate;
    }
    
    public String getlastusedate() {
        return this.lastusedate;
    }

    public void setlastusedate(String _lastusedate) {
        this.lastusedate = _lastusedate;
    }
    
    public String getshortcode() {
        return this.shortcode;
    }

    public void setshortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    @Override
    public String toString() {
        return "url{" +
                "id='" + this.id + "'" +
                ", longname='" + this.longname + "'" +
                ", createddate='" + this.createddate + "'" +
                ", lastusedate='" + this.lastusedate + "'" +
                ", shortcode='" + this.shortcode + "'}";
    }
}
