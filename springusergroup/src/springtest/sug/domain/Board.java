package springtest.sug.domain;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;


public class Board {
	int seqNo;        		 					// 순번
	
	@Size(min=2, max=50)
    String title;      							// 제목
	
	@Size(min=2, max=500)
    String content;    							// 내용
	
	@Size(min=4, max=12)
    String writer;     							// 작성자
	
	@DateTimeFormat
    java.sql.Date wdate ;					 	// 저장일자
	
	@DateTimeFormat
    java.sql.Time wtime ; 						// 저장시각
	
    int listSize;     				 			// 리스트 사이즈
    
    private boolean firstPage = false; 			// 게시글 목록의 첫번째 페이지인지 여부
    private boolean lastPage = false;           // 게시글 목록의 마지막 페이지인지 여부
    private int pageNum; 						// 게시글 목록 페이지의 수

    
    public Board() {							// no-arg 생성자(파라미터가 없는 생성자)
    }
    
	public Board(int seqNo, String title, String content, String writer, Date wdate, Time wtime) {
		super();
		this.seqNo = seqNo;
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.wdate = wdate;
		this.wtime = wtime;
	}
    
	
    public int getSeqNo() {
        return (seqNo);
    }
    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }
    public String getTitle() {
//        return toUnicode(title);
        return (title);
    }
    public void setTitle(String title) {
		this.title = title;
	}    
    public String getContent() {
//        return toUnicode(content);
        return (content);
    }
 	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
//        return toUnicode(writer);
        return (writer);
    }
	public void setWriter(String writer) {
		this.writer = writer;
	}
    public Date getDate() {
         return wdate;
    }
	public void setDate(Date wdate) {
		this.wdate = wdate;
	}
    public Time getTime() {
         return wtime;
    }
	public void setTime(Time wtime) {
		this.wtime = wtime;
	}

    
	public void initDates() {
		GregorianCalendar now = new GregorianCalendar();
	    Date tempDate = new Date(now.getTimeInMillis());	//java.sql.Date 타입으로  (날짜만)
	    Time tempTime = new Time(now.getTimeInMillis()); 	//java.sql.Time 타입으로  (시간만)
	    if (this.wdate == null) this.wdate = tempDate;
	    if (this.wtime == null) this.wtime = tempTime;	    
//	    Timestamp ts = new Timestamp(now.getTimeInMillis());//java.sql.TimeStamp 타입으로 변환 (날짜 + 시간 출력)
	}


    public boolean isFirstPage() {
    	 return firstPage;
    }
    public boolean isLastPage() {
         return lastPage;
    }
	
    public void setFirstPage(boolean firstPage) {
    	this.firstPage = firstPage;
    }
    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }
    

    public int getListSize() {        // 게시글의 수를 리턴하는 메서드
//      return seqNoList.size();
      return listSize;
    }
    public void setListSize(int listSize) {
        this.listSize = listSize;
    }
    
    
	public String toString() {
		return "Board [title=" + title + ", content=" + content + ", seqNo=" + seqNo 
				+ ", writer=" + writer + ", wdate=" + wdate + ", wtime=" + wtime + "]";
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + seqNo;
		return result;
	}
   
    public int getPageNum() { 				// 페이지의 수를 리턴하는 메서드
    	return pageNum;
    }    
    public void setPageNum(int pageNum) {	// 페이지의 수를 설정하는 메서드
    	 this.pageNum = pageNum;
    }

	public void setModified() {				// update 날짜값 설정.
		GregorianCalendar now = new GregorianCalendar();
	    Date tempDate = new Date(now.getTimeInMillis());	//java.sql.Date 타입으로  (날짜만)
	    Time tempTime = new Time(now.getTimeInMillis()); 	//java.sql.Time 타입으로  (시간만)
	    if (this.wdate == null) this.wdate = tempDate;
	    if (this.wtime == null) this.wtime = tempTime;	    
	}
}
	
	





/*	
public void initDates() {
	Calendar calendar = Calendar.getInstance();
//	java.util.Date nowDateTime = calendar.getTime();
//	java.util.Date nowTime = calendar.getTime();
	
	GregorianCalendar now = new GregorianCalendar();
	java.util.Date nowDateTime = now.getTime();
	
	java.util.Date uDate = now.getTime();
	java.util.Date uTime = now.getTime();
	
	java.sql.Date tempDate = new java.sql.Date(uDate.getTime());
	java.sql.Time tempTime = new java.sql.Time(uDate.getTime());
	
    if (this.wdate == null) this.wdate = tempDate;
    if (this.wtime == null) this.wtime = tempTime;	    
    
    System.out.println("this.wdate = "+ this.wdate);
    System.out.println("this.wtime = "+ this.wtime);
		
}
*/	
