package com.you.ap.domain.vo.book;

import com.google.common.collect.Lists;
import com.you.ap.domain.model.book.BookHourBoxModel;
import com.you.ap.domain.model.book.BookDayDetailModel;
import com.you.ap.domain.model.book.LocationModel;
import com.you.ap.domain.schema.book.LocationDO;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class BookDayDetailVO {

    private List<BookHourBoxVO> allBookHourBoxList = Lists.newArrayList();

    private LocationVO locationVO;


    public BookDayDetailVO(BookDayDetailModel bookDayDetailModel){
       if (bookDayDetailModel == null){
           return;
       }
       this.allBookHourBoxList = bookDayDetailModel.getAllHourBoxModel().stream().map(model->new BookHourBoxVO(model)).collect(toList());
    }

    public BookDayDetailVO of(LocationModel locationModel){
        this.locationVO = new LocationVO(locationModel);
        return this;
    }

    public List<BookHourBoxVO> getAllBookHourBoxList() {
        return allBookHourBoxList;
    }

    public void setAllBookHourBoxList(List<BookHourBoxVO> allBookHourBoxList) {
        this.allBookHourBoxList = allBookHourBoxList;
    }

    public LocationVO getLocationVO() {
        return locationVO;
    }

    public void setLocationVO(LocationVO locationVO) {
        this.locationVO = locationVO;
    }

    public static class BookHourBoxVO{
        private int hourId;
        private int status;

        public BookHourBoxVO(){}

        public BookHourBoxVO(BookHourBoxModel bookHourBoxModel){
            this.hourId=bookHourBoxModel.getHourId();
            this.status= bookHourBoxModel.getStatus();
        }

        public int getHourId() {
            return hourId;
        }

        public void setHourId(int hourId) {
            this.hourId = hourId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
