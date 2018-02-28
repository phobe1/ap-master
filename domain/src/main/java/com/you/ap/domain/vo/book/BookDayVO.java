package com.you.ap.domain.vo.book;

import com.you.ap.domain.enums.book.BookDayStatusEnum;
import com.you.ap.domain.enums.book.BookDayStatusFormTeacher;
import com.you.ap.domain.schema.book.BookDayDetailDO;
import org.apache.commons.lang3.StringUtils;

public class BookDayVO {

    //21071212
    private int bookDay;

    private int id;

    private int status;



    public BookDayVO(BookDayDetailDO bookTeachDayDO,Integer locationId){
        if (locationId != null){
            if(locationId != bookTeachDayDO.getLocationId()){
                if (bookTeachDayDO.getStatus()== BookDayStatusEnum.NORMAL_BOOK.key){
                    this.status=BookDayStatusEnum.NORMAL_BOOK_OF_OTHER_PLCAE.key;
                }else if (bookTeachDayDO.getStatus()== BookDayStatusEnum.FULL_BOOK.key){
                    this.status=BookDayStatusEnum.FULL_BOOK_OTHER_POSITION.key;
                }else{
                    this.status= BookDayStatusEnum.NOT_BOOK.key;
                }
            }else{
                this.status= bookTeachDayDO.getStatus();
            }
        }
        else{
            if (!StringUtils.isEmpty(bookTeachDayDO.getBookedHourBox())){
                this.status = BookDayStatusFormTeacher.HAS_BOOK.key;
            }else if(bookTeachDayDO.getStatus() == BookDayStatusEnum.NOT_BOOK.key){
                this.status = BookDayStatusFormTeacher.NOT_OPEN_BOOK.key;
            }else if (bookTeachDayDO.getStatus() == BookDayStatusEnum.NORMAL_BOOK.key){
                this.status = BookDayStatusFormTeacher.PUBLISH_BOOK.key;
            }else if (bookTeachDayDO.getStatus() == BookDayStatusEnum.FULL_BOOK.key){
                this.status = BookDayStatusFormTeacher.FULL_BOOK.key;
            }
        }
        this.id=bookTeachDayDO.getId();
        this.bookDay=bookTeachDayDO.getBookDay();
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
