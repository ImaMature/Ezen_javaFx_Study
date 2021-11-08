package domain;

//제품의 날짜와 관련된 클래스 
public class ProductDate {
	
	private String date; //x축
	private int count; //y축
	
	public ProductDate() {
		
	}

	public ProductDate(String date, int count) {
		this.date = date;
		this.count = count;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
	

}
