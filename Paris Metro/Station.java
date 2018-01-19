public class Station implements Comparable<Station> {
	// Instance variables.
	private Station next = null;;
	private String name = null;
	private int connections;
	private int index;
	private int time;
	private boolean head = false;;
	
	// Constructors.
	public Station(String name, int index){
		this.name = name;
		this.index = index;
		connections = 0;
	}
	public Station(String name, int index, int time) {
		this.name = name;
		this.index = index;
		this.time = time;
	}
	public Station(Station s, int time){
		name = s.getName();
		index = s.getIndex();
		this.time = time;
	}
	
	@Override
	public int compareTo(Station other) {
		return Integer.compare(this.time, other.time);
	}
	
	// Setter and getter for next.
	public void setNext(Station next){
		this.next = next;
	}
	public Station getNext() {
		return next;
	}
	
	// Setter and getter for head.
	public void setHead(boolean head){
		this.head = head;
	}
	public boolean getHead(){
		return head;
	}
	
	// Getter for name.
	public String getName(){
		return name;
	}
	
	// Getter for index.
	public int getIndex(){
		return index;
	}
	
	// Setter for time.
	public void setTime(int time){
		this.time = time;
	}
	
	// Getter for time.
	public int getTime(){
		return time;
	}
}