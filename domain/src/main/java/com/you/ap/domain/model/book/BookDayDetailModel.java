package com.you.ap.domain.model.book;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.you.ap.domain.enums.book.BookHourBoxStatusEnum;
import com.you.ap.domain.schema.book.BookDayDetailDO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;


public class BookDayDetailModel implements Serializable {

    private int id;

    // sample: 20171101
    private int bookDay;

    private int teacherId;

    private int status;

    private int locationId;

    private String created;

    private String modified;

    private static final Set<Integer> allHours = Sets.newHashSet(7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23);

    private Map<Integer,BookHourBoxModel> freeHourBoxs = Maps.newHashMap();

    private Map<Integer,BookHourBoxModel> bookedHourBoxs = Maps.newHashMap();

    private Map<Integer,BookHourBoxModel> closeHourBoxs = Maps.newHashMap();

    public BookDayDetailModel(){}

    public BookDayDetailModel(BookDayDetailDO bookTeachDetailDO){
        if (bookTeachDetailDO == null ){
            return ;
        }
        this.id = bookTeachDetailDO.getId();
        this.bookDay= bookTeachDetailDO.getBookDay();
        this.teacherId= bookTeachDetailDO.getTeacherId();
        this.status= bookTeachDetailDO.getStatus();
        this.locationId=bookTeachDetailDO.getLocationId();
        this.created=bookTeachDetailDO.getCreated();
        this.modified=bookTeachDetailDO.getModified();
    }

    public BookDayDetailModel of(Set<Integer> publishHourBoxs, Set<Integer> hasBookHourBoxs){
        this.bookedHourBoxs = hasBookHourBoxs.stream().map(id->new BookHourBoxModel(id,BookHourBoxStatusEnum.HAS_BOOK)).collect(toMap(BookHourBoxModel::getHourId, Function.identity()));
        this.freeHourBoxs = publishHourBoxs.stream().filter(id->!hasBookHourBoxs.contains(id)).map(id->new BookHourBoxModel(id,BookHourBoxStatusEnum.NORMAL_BOOK)).collect(toMap(b->b.getHourId(), Function.identity()));
        this.closeHourBoxs = allHours.stream().filter(id-> !publishHourBoxs.contains(id)).map(id->new BookHourBoxModel(id,BookHourBoxStatusEnum.NO_BOOK)).collect(toMap(b->b.getHourId(), Function.identity()));
        return this;
    }

    public BookDayDetailModel publishBoxHours(Set<Integer> publishHourBoxs,int locationId){
        if (publishHourBoxs == null ) {
            return this;
        }
        if (this.bookedHourBoxs.size()==0 || this.locationId==0){
            this.locationId=locationId;
        }
        publishHourBoxs.addAll(this.bookedHourBoxs.keySet());
        return this.of(publishHourBoxs,this.getBookedHourBoxs().keySet());
    }

    public boolean checkCanBook(Set<Integer> applyIdSet){
        if (CollectionUtils.isEmpty(applyIdSet) || MapUtils.isEmpty(this.freeHourBoxs)){
            return false;
        }
        Set<Integer> newBookedHourBoxSet = this.freeHourBoxs.keySet().stream().filter(id->applyIdSet.contains(id)).collect(toSet());
        if (newBookedHourBoxSet.size()<applyIdSet.size()){
            return false;
        }
        return true;
    }

    public BookDayDetailModel applyBook(Set<Integer> applyIdSet) {
        if (applyIdSet == null ){
            return this;
        }
        Set<Integer> newBookedHourBoxSet = this.freeHourBoxs.keySet().stream().filter(id->applyIdSet.contains(id)).collect(toSet());
        if (newBookedHourBoxSet.size()<applyIdSet.size()){
            throw new RuntimeException("有不能预定的时刻");
        }
        this.bookedHourBoxs.putAll(newBookedHourBoxSet.stream().map(
                id-> new BookHourBoxModel(id,BookHourBoxStatusEnum.HAS_BOOK)).collect(toMap(box->box.getHourId(), Function.identity())));
        newBookedHourBoxSet.forEach(hour->this.freeHourBoxs.remove(hour));
        return this;
    }

    public BookDayDetailModel cancelBook(Set<Integer> cancelIdSet) {
        if (cancelIdSet == null ){
            return this;
        }
        Set<Integer> newCancelHourBoxSet = this.bookedHourBoxs.keySet().stream().filter(id->cancelIdSet.contains(id)).collect(toSet());
        if (newCancelHourBoxSet.size()<cancelIdSet.size()){
            throw new RuntimeException("有不能取消的时刻,bookDayId :{}"+this.id+",newCancle:"+newCancelHourBoxSet+",cancelIdSet:{}"+cancelIdSet.toString()+" booked:"+this.bookedHourBoxs.keySet());
        }
        this.freeHourBoxs.putAll(newCancelHourBoxSet.stream().map(
                id-> new BookHourBoxModel(id,BookHourBoxStatusEnum.NORMAL_BOOK)).collect(toMap(box->box.getHourId(), Function.identity())));
        newCancelHourBoxSet.forEach(hour->this.bookedHourBoxs.remove(hour));
        return this;
    }

    public Set<Integer> getAllPublishHourIdSet(){
        Set<Integer> result = Sets.newHashSet();
        result.addAll(freeHourBoxs.keySet());
        result.addAll(bookedHourBoxs.keySet());
        return result;
    }



    public List<BookHourBoxModel> getAllHourBoxModel(){
        Map<Integer,BookHourBoxModel> total = Maps.newHashMap();
        total.putAll(freeHourBoxs);
        total.putAll(bookedHourBoxs);
        total.putAll(closeHourBoxs);
        return total.values().stream().sorted(comparing(hourBox->hourBox.getHourId())).collect(Collectors.toList());
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

    public static Set<Integer> getAllHours() {
        return allHours;
    }

    public Map<Integer, BookHourBoxModel> getFreeHourBoxs() {
        return freeHourBoxs;
    }

    public void setFreeHourBoxs(Map<Integer, BookHourBoxModel> freeHourBoxs) {
        this.freeHourBoxs = freeHourBoxs;
    }

    public Map<Integer, BookHourBoxModel> getBookedHourBoxs() {
        return bookedHourBoxs;
    }

    public void setBookedHourBoxs(Map<Integer, BookHourBoxModel> bookedHourBoxs) {
        this.bookedHourBoxs = bookedHourBoxs;
    }

    public Map<Integer, BookHourBoxModel> getCloseHourBoxs() {
        return closeHourBoxs;
    }

    public void setCloseHourBoxs(Map<Integer, BookHourBoxModel> closeHourBoxs) {
        this.closeHourBoxs = closeHourBoxs;
    }


}
