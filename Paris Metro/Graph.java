/*
	This class represents an array of priority queues for stations.
	
	Each index is mapped to a station. The station is stored as the dummy node at its index.
	The dummy node points to a priority queue made of connecting stations.
*/
public class Graph{
	private Station[] stations;
	private int size = 0;
	
	// Constructor.
	public Graph(int numStations){
		stations = new Station[numStations];
		size = numStations;
	}
	
	public Graph(Graph g) {
		size = g.getSize();
		stations = new Station[size];
		
		for (int i = 0; i < g.getSize(); i++) {
			addStation(g.getStation(i).getName(), i);
		}
	}
	
	// Creates the dummy node for priority queue.
	public void addStation(String name, int index){
		if (stations[index] == null){
			// Create linked list.
			stations[index] = new Station(name, index);
			stations[index].setHead(true);
		}
	}
	
	public void removeStation(int index) { 
		if (index < 0 || size <= index)
			return;
		
		// Remove connections to station.
		for (int i = 0; i < size; i++){
			if (stations[i] != null)
				removeConnection(i, index);
		}
		
		stations[index] = null;
		Station s = null;
	}
	
	// Creates a connecting station.
	public void addConnection(int prime, int connecting, int time) {
		if (stations[prime] == null) return;
			
		// Add connection.
		Station n = new Station(stations[connecting], time);
		//System.out.println("Adding to " + " " + Integer.toString(prime) + stations[connecting].getName() + " " + Integer.toString(time) + " " + Integer.toString(stations[connecting].getIndex()));
		addToPriorityQueue(n, prime);
	}
	
	public void removeConnection(int index, int connection) {
		if (index >= size || stations[index] == null)
			return;
		
		if (stations[index] == null)
			return;
		
		Station prev = stations[index];
		Station next = stations[index];
		
		if (next == null)
			return;
		
		while (next != null) {
			if (next.getNext() == null)
				break;
			
			prev = next;
			next = next.getNext();
			
			if (next.getIndex() == connection) {
				// Remove connection.
				prev.setNext(next.getNext());
				next = null;
				break;
			}
		}
	}
	
	public Station getStation(int index) {
		return stations[index];
	}
	
	public int getSize(){
		return size;
	}
	
	public Station dequeueConnection(int index) {
		if (index >= stations.length)
			return null;
		
		Station removed = stations[index].getNext();
		if (stations[index].getNext() != null)
			stations[index].setNext(stations[index].getNext().getNext());
		
		return removed;
	}
	
	// Inserts station into priority queue at index.
	private void addToPriorityQueue(Station n, int index) {
		// First connection.
		if (stations[index].getNext() == null){
			stations[index].setNext(n);
			return;
		}
		
		// Priority is time.
		boolean end = true;
		Station current = stations[index];
		while (current.getNext() != null) {
			if (n.getTime() < current.getNext().getTime()) {
				// Insert the station.
				n.setNext(current.getNext());
				current.setNext(n);
				end = false;
				break;
			}
			// Check next station.
			current = current.getNext();
		}
		if (end){
			n.setNext(current.getNext());
			current.setNext(n);
		}
	}
}