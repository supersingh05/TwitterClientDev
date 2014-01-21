package com.dev.googlesearch;

import java.io.Serializable;

public class Filter implements Serializable {
	public String imageSize;
	public String colorFilter;
	public String imageType;
	public String domainFilter;
	
	public void setImageSize(String var) {
		this.imageSize = var;
	}
	
	public void setColorFilter(String var) {
		this.colorFilter = var;
	}
	
	public void setDomainFilter(String var) {
		this.domainFilter = var;
	}
	
	public void setImageType(String var) {
		this.imageType = var;
	}
	
	public String getImageSize() {
		return this.imageSize;
		
	}
	
	public String getColorFilter() {
		return this.colorFilter;
		
		
	}
	
	
	public String getDomainFilter() {
		return this.domainFilter;
		
		
	}
	
	public String getImageType() {
		return this.imageType;
		
	}
	
	
}
