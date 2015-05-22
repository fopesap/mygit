package springtest.sug.boardjdbc;


import java.io.*;
import java.sql.*;


public class BBSJdbcList  {
    
    private boolean firstPage = false;  // �Խñ� ����� ù��° ���������� ����
    private boolean lastPage = false;   // �Խñ� ����� ������ ���������� ����
    private int pageNum; 				// �Խñ� ��� �������� ��

    private int listSize;      			// ����Ʈ ������
    private int firstSeq = 0;      			// ����Ʈ ù��°
    private int lastSeq = 0;      			// ����Ʈ ������
    
    
    public BBSJdbcList() {
    }
    
    public void setFirstPage(boolean firstPage) {
    	this.firstPage = firstPage;
    }
    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }
    public void setPageNum(int pageNum) { // �������� ���� �����ϴ� �޼���
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
    
    
 
     
    public int getListSize() {        // �Խñ��� ���� �����ϴ� �޼���
//      return seqNoList.size();
      return listSize;
    }
    public int getPageNum() { 		  // �������� ���� �����ϴ� �޼���
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
