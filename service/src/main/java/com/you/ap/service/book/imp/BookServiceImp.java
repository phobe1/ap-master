package com.you.ap.service.book.imp;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.you.ap.common.helper.DateUtil;
import com.you.ap.common.helper.StringUtil;
import com.you.ap.dao.book.IBookTeachDAO;
import com.you.ap.domain.enums.book.BookDayStatusEnum;
import com.you.ap.domain.model.book.BookDayDetailModel;
import com.you.ap.domain.schema.book.BookDayDetailDO;
import com.you.ap.service.book.IBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
@Created by gaoxiaoning 2017/12/14
 */
@Service
public class BookServiceImp implements IBookService {
	
	private Logger logger= LoggerFactory.getLogger(BookServiceImp.class);
	
	@Autowired private IBookTeachDAO bookTeachDAO;
	
	private static ExecutorService executorService = new ThreadPoolExecutor(
            5, 5, 1L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(300),
            new ThreadFactoryBuilder().setNameFormat("bookteach-pool-%d").build(),
            new ThreadPoolExecutor.AbortPolicy());


	@Override
	public BookDayDetailDO getById(int bookDayId) {
		return bookTeachDAO.getById(bookDayId);
	}

	@Override
	public BookDayDetailModel converDO2Model(BookDayDetailDO bookDayDetailDO) {
		if (bookDayDetailDO == null ){
			return null;
		}
		return new BookDayDetailModel(bookDayDetailDO).of(
						StringUtil.splitStr(bookDayDetailDO.getPubBookHourBox()),
						StringUtil.splitStr(bookDayDetailDO.getBookedHourBox()));
	}

	@Override
	public void updateBookDay(BookDayDetailDO bookDayDetailDO,BookDayDetailModel bookDayDetailModel) {
		if (bookDayDetailDO==null || bookDayDetailModel == null){
			return;
		}
		String pub =StringUtil.formate2Str(bookDayDetailModel.getAllPublishHourIdSet());
        String booked = StringUtil.formate2Str(bookDayDetailModel.getBookedHourBoxs().keySet());
        if (bookDayDetailModel.getFreeHourBoxs().keySet().size()>0){
			bookDayDetailDO.setStatus(BookDayStatusEnum.NORMAL_BOOK.key);
		}else if (bookDayDetailModel.getBookedHourBoxs().keySet().size()>0){
        	bookDayDetailDO.setStatus(BookDayStatusEnum.FULL_BOOK.key);
		}else{
			bookDayDetailDO.setStatus(BookDayStatusEnum.NOT_BOOK.key);
		}
		bookDayDetailDO.setLocationId(bookDayDetailModel.getLocationId());
		bookTeachDAO.updateBookDayDetail(bookDayDetailDO.updateHourBoxs(pub,booked));
	}

	/*
	初始化老师的 预约book_day
	 */
	@Override
	public void autoAddBookDay(final int teacherId) {

		try {
			logger.info("autoAddBookDay, teacherId:{}"+teacherId);
			Integer maxBookDay = bookTeachDAO.getMaxShowDay(teacherId);
			Date start = new Date();
			if (maxBookDay != null ){
				start = DateUtil.addDays(DateUtil.parseDateFromYMDIntDay(maxBookDay),1);
			}
			if (start == null || start.before(new Date())){
				logger.info("autoAddBookDay.start is null or start is before now,use start=now ");
				start = new Date();
			}
			Date end = DateUtil.addDays(new Date(),30);
			if(!start.before(end)){
				logger.info("autoAddBookDay.start not before end,return");
				return;
			}
			while(true){
				final Date dateCopy = start;
			    executorService.execute(new Runnable() {
				@Override
				public void run() {
					addBookDay(dateCopy,teacherId);
				}
			    });
				start =DateUtil.addDays(start, 1);
				if (start.after(end)){
					break;
				}
			}
		} catch (Exception e) {
			logger.error("autoAddBookDay",e);
		}
	}

	private void addBookDay(Date date ,int teacherId) {
		BookDayDetailDO bookDayTeachDO =new BookDayDetailDO(DateUtil.formateDateToYMDIntDay(date),teacherId);
		bookTeachDAO.addBookDay(bookDayTeachDO);
		logger.info("addBookDaySuccess!! date:{},teacherId:{}",DateUtil.formateDateToYMDIntDay(date)+"",teacherId);
	}
}
