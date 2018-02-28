package com.you.ap.domain.schema.book;


import com.you.ap.domain.enums.book.BookDayStatusEnum;

public class BookDayDetailDO {
	
	private int id;
	
	// sample: 20171101
	private int bookDay;
	
	private int teacherId;

	private int status;

	private int locationId;

	private String pubBookHourBox;

	private String bookedHourBox;
	
	private String created;
	
	private String modified;
	
	public BookDayDetailDO(){
		
	}

	public BookDayDetailDO updateHourBoxs(String pubBookHourBox, String bookedHourBox){
		this.pubBookHourBox=pubBookHourBox;
		this.bookedHourBox=bookedHourBox;
		return this;
	}

	
	public BookDayDetailDO(int bookDay, int teacherId){
		this.bookDay=bookDay;
		this.teacherId=teacherId;
		this.status=BookDayStatusEnum.NOT_BOOK.key;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBookDay() {
		return bookDay;
	}

	public void setBookDay(int bookDay) {
		this.bookDay = bookDay;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getPubBookHourBox() {
		return pubBookHourBox;
	}

	public void setPubBookHourBox(String pubBookHourBox) {
		this.pubBookHourBox = pubBookHourBox;
	}

	public String getBookedHourBox() {
		return bookedHourBox;
	}

	public void setBookedHourBox(String bookedHourBox) {
		this.bookedHourBox = bookedHourBox;
	}
}
