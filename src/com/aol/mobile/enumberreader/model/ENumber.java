package com.aol.mobile.enumberreader.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ENumber implements Parcelable{

	public String name;
	public String number;
	public String wikipediaUrl;
	
	
	public ENumber(){
		
	}
	
	public ENumber(String name, String number, String wikipediaUrl) {
		super();
		this.name = name;
		this.number = number;
		this.wikipediaUrl = wikipediaUrl;
	}
	
	public String getName() {
		return name;
	}
	public String getNumber() {
		return number;
	}
	public String getWikipediaUrl() {
		return wikipediaUrl;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public void setWikipediaUrl(String wikipediaUrl) {
		this.wikipediaUrl = wikipediaUrl;
	}
	
	
	@Override
	public String toString(){
		return "E"+number;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(name);
		out.writeString(number);
		out.writeString(wikipediaUrl);
	}
	
	 public static final Parcelable.Creator<ENumber> CREATOR = new Parcelable.Creator<ENumber>() {
		 public ENumber createFromParcel(Parcel in) {
			 return new ENumber(in);
		 }

		 public ENumber[] newArray(int size) {
		     return new ENumber[size];
		 }
	};

	private ENumber(Parcel in) {
		name = in.readString();
		number = in.readString();
		wikipediaUrl = in.readString();
	}
	
}
