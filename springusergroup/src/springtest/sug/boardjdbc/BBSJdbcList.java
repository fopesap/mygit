package springtest.sug.boardjdbc;


import java.io.*;
import java.sql.*;


public class BBSJdbcList  {
    
    private boolean firstPage = false;  // 게시글 목록의 첫번째 페이지인지 여부
    private boolean lastPage = false;   // 게시글 목록의 마지막 페이지인지 여부
    private int pageNum; 				// 게시글 목록 페이지의 수

    private int listSize;      			// 리스트 사이즈
    private int firstSeq = 0;      			// 리스트 첫번째
    private int lastSeq = 0;      			// 리스트 마지막
    
    
    public BBSJdbcList() {
    }
    
    public void setFirstPage(boolean firstPage) {
    	this.firstPage = firstPage;
    }
    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }
    public void setPageNum(int pageNum) { // 페이지의 수를 설정하는 메서드
    	this.pageNum = pageNum;
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }
    public void setFirstSeq(int firstSeq) {
        this.firstSeq = firstSeq;
    }
    public void setLastSeq(int lastSeq) {
        this.lastSeq = lastSeq;
    }
    
    
 
     
    public int getListSize() {        // 게시글의 수를 리턴하는 메서드
//      return seqNoList.size();
      return listSize;
    }
    public int getPageNum() { 		  // 페이지의 수를 리턴하는 메서드
    	return pageNum;
    }    
    public int getFirstSeq() {
    	return firstSeq;
    }    
    public int getLastSeq() {
    	return lastSeq;
    }    
    public boolean getFirstPage() {
    	return firstPage;
    }    
    public boolean getLastPage() {
    	return lastPage;
    }    
     
    
    public boolean isFirstPage() {
    	 return firstPage;
    }
    public boolean isLastPage() {
         return lastPage;
    }
       
    
    

}
