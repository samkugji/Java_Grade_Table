
/*DTO(Data Transfer Object)데이터를 담는 클래스로 
 * private으로 변수를 선언 한 후 Get&Set를 선언 한다.
 * 데이터의 전달을 목적으로 설계된 클래스이다
 * */
public class MemberDTO {
	private int id;
	private String name;
	private int kor;
	private int math;
	private int eng;
	private int sum;
	private double avg;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getKor() {
		return kor;
	}
	public void setKor(int kor) {
		this.kor = kor;
	}
	public int getMath() {
		return math;
	}
	public void setMath(int math) {
		this.math = math;
	}
	public int getEng() {
		return eng;
	}
	public void setEng(int eng) {
		this.eng = eng;
	}
	public int getSum() {
		this.sum = kor + math + eng;
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public double getAvg() {
		avg = (Math.round(this.sum / 3.00 * 100) / 100.00);
		return avg;
	}
	public void setAvg(double avg) {
		this.avg = avg;
	}	   
}