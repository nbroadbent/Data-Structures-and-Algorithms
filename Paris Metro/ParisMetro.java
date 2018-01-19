import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.*;

public class ParisMetro {
	private static Graph graph;

	public static void main(String[] args){
		//long start = System.nanoTime()/(int)1e6;
		
		int len = args.length;
		try {
			readMetro("metro.txt");
		} catch (Exception e) {
			System.out.println("Error: Could not read file metro.txt");
		}
		
		// Select function.
		switch (len) {
		case 1:
			// Question 2-i).
			Station[] line = stationsInLine(strToInt(args[0]));		
			for (Station s : line) {
				System.out.println ("id: " + s.getIndex() + "	name: " + s.getName());
			}
		
			break;
		case 2:
			// Question 2-ii).
			if (args[0].equals(args[1])) 
				System.out.println("No path to travel to the same station");
			else 
				shortestPath(strToInt(args[0]), strToInt(args[1]));		
			
			//long end = System.nanoTime()/(int)1e6;
			//System.out.print("Time: " + (end - start));
			
			break;
		case 3:
			// Question 2-iii).
			if (args[0].equals(args[1])) 
				System.out.println("No path to travel to the same station");
			else 
				shortestPath(strToInt(args[0]), strToInt(args[1]), strToInt(args[2]));		
			break;
		}
	}

	// Q1. Prints all stations on the same line in travel order.
	private static Station[] stationsInLine(int index) {
		Map<Integer, Integer> visited = new HashMap<>(graph.getSize());
		Deque<Integer> stack = new ArrayDeque<Integer>(graph.getSize());
		ArrayList<Station> line = new ArrayList<>();
		
		Station station = null;
		Station connecting = null;
		boolean exit = true;
		
		// Push initial stations.
		stack.push(index);
		do {
			station = graph.getStation(stack.pop());	
			if (visited.get(index) == null)
				visited.put(index, 0);
			if (visited.get(index).equals(0))
				visited.put(index, 1);
			
			line.add(station);		
			
			if (station != null) {
				
				connecting = station.getNext();
				// Get stations connection.
				do {
					if (connecting != null) {
						if (visited.get(connecting.getIndex()) == null)
							visited.put(connecting.getIndex(), 0);
						
						if (visited.get(connecting.getIndex()).equals(0) && connecting.getTime() != 90) {
							// Station has not been visited.
							stack.push(connecting.getIndex());
							visited.put(connecting.getIndex(), 1);
						}
						connecting = connecting.getNext();
					}
					else {
						break;
					}
				} while (connecting != null);
			}
			else {
				break;
			}
		} while (!stack.isEmpty());
		
		return line.toArray(new Station[0]);
	}

	// Q2. Finds shortest path between two stations.
	private static void shortestPath(int start, int end) {
		// Stores if node has been visited.
		Map<Integer, Integer> visited = new HashMap<>(graph.getSize());
		// Stores the captured nodes.
		ArrayList<Station> captured = new ArrayList<Station>();
		// Stores the path of the current station from the starting station.
		ArrayList<Station> path = new ArrayList<Station>(graph.getSize());
		// Stores the paths of each station from the starting station.
		HashMap<Integer, ArrayList<Station>> paths = new HashMap<Integer, ArrayList<Station>>();
		// Stores the distance of each station.
		HashMap<Integer, Integer> distance = new HashMap<Integer, Integer>(graph.getSize());
		
		Queue<Integer> pQueue = new PriorityQueue<>();
		
		Station starting = graph.getStation(start);
		Station from = starting;
		Station connecting = null;
		Station minStation = null;
		int sum = 0;
		int min  = 0;
		boolean minCon = true;
		
		path.add(starting);
		
		for (int i = 0; i < graph.getSize(); i++) {
			
			paths.put(i, path);
			distance.put(i, Integer.MAX_VALUE);
		}
		
				
		// Dijkstra's algorithm to find the shortest path to all nodes from starting node.
		captured.add(starting);
		Station current = starting;
		while (current != null) {
			
			if (current == null)
				break;
						
			// Initialize non visited nodes.
			if (visited.get(current.getIndex()) == null)
				visited.put(current.getIndex(), -1);
			
			if (visited.get(current.getIndex()) != 1) {	
				// Initialize the connecting station for the current node.
				connecting = current.getNext();
				
				// Search all of the current nodes connections.
				while (connecting != null){				
					// Check if first time being visited.
					// Compare this path's distance with the current shortest distance.
					if ((distance.get(current.getIndex()) + connecting.getTime()) < distance.get(connecting.getIndex())){
						// This path is closer.
						// Update the path.
						path = new ArrayList<>(paths.get(current.getIndex()));
						path.add(connecting);							
						paths.put(connecting.getIndex(), path);
			
						
						// Update the distance.
						distance.put(connecting.getIndex(), (distance.get(current.getIndex()) + connecting.getTime()));							
					}
					connecting = connecting.getNext();
				}
																					
				minCon = true;
			}
			else {
				// Current node duplicate.
				break;
			}
			
			// Station visited.
			visited.put(current.getIndex(), 1);
			captured.add(current);
			
			for (int i = 0; i < graph.getSize(); i++) {
				graph.removeConnection(i, current.getIndex());
			}
						
			// Find closest to captured nodes.
			minStation = new Station("min", 0);
			minStation.setTime(Integer.MAX_VALUE);
			
			
			int minTime = Integer.MAX_VALUE;
			for (Station s : captured){
				if (s.getNext() == null)
					continue;														
				
				Station next = s.getNext();
				// Find closest connection that's not been visited.
				while (next != null) {
					// Initialize visited.
					if (visited.get(next.getIndex()) == null)
						visited.put(next.getIndex(), -1);
									
					if (visited.get(next.getIndex()) == -1) {
						//System.out.println(" has not beed visited");
						if (next.getTime() < minTime){
							minTime = next.getTime();
							minStation = next;
							from = s;
						}
						break;
					}
					else {
						// Station has already been visited
						graph.dequeueConnection(s.getIndex());
					}
					next = next.getNext();
				}
			}
			
			// Capture closest station.
			graph.dequeueConnection(from.getIndex());
			current = graph.getStation(minStation.getIndex());
			
		}
		
		int time = 0;
		
		// Print path.
		System.out.println("Shortest Path");
		System.out.println("---------------");
		
		// Print path to end station.
		for (Station s : paths.get(end)){
			if (s != null) {
				System.out.print(" id: " + s.getIndex());
				time += s.getTime();
			}
			
		}	
		System.out.println(" Time: " + time);
		System.out.println();
		time = 0;
	}
	
	// Q3. Finds shortest path between two stations when one line is removed.
	private static void shortestPath(int start, int end, int removed){
		Station[] brokenLine = stationsInLine(removed); 
		
		// Check if start or end is in broken line.
		
		// Remove line from graph.
		for (Station s : brokenLine) {
			if (s.getIndex() == start || s.getIndex() == end){
				System.out.println("Start or end is broken");
				return;
			}
			graph.removeStation(s.getIndex());
		}
		
		// Stores if node has been visited.
		Map<Integer, Integer> visited = new HashMap<>(graph.getSize());
		// Stores the captured nodes.
		ArrayList<Station> captured = new ArrayList<Station>();
		// Stores the path of the current station from the starting station.
		ArrayList<Station> path = new ArrayList<Station>(graph.getSize());
		// Stores the paths of each station from the starting station.
		HashMap<Integer, ArrayList<Station>> paths = new HashMap<Integer, ArrayList<Station>>();
		// Stores the distance of each station.
		HashMap<Integer, Integer> distance = new HashMap<Integer, Integer>();
		
		Station starting = graph.getStation(start);
		Station from = starting;
		Station connecting = null;
		Station minStation = null;
		int sum = 0;
		int min  = 0;
		boolean minCon = true;
		
		path.add(starting);
		
		// Initalize distances to infinite.
		for (int i = 0; i < graph.getSize(); i++) {
			paths.put(i, path);
			distance.put(i, Integer.MAX_VALUE);
		}
		
				
		// Dijkstra's algorithm to find the shortest path to all nodes from starting node.
		//captured.add(starting);
		Station current = starting;
		while (current != null) {
			
			if (current == null)
				break;
						
			// Initialize non visited nodes.
			if (visited.get(current.getIndex()) == null)
				visited.put(current.getIndex(), -1);
			
			if (visited.get(current.getIndex()) != 1) {	
				// Initialize the connecting station for the current node.
				connecting = current.getNext();
				
				// Search all of the current nodes connections.
				while (connecting != null){				
					// Check if first time being visited.
					// Compare this path's distance with the current shortest distance.
					if ((distance.get(current.getIndex()) + connecting.getTime()) < distance.get(connecting.getIndex())){
						// This path is closer.
						// Update the path.
						path = new ArrayList<>(paths.get(current.getIndex()));
						path.add(connecting);							
						paths.put(connecting.getIndex(), path);
			
						
						// Update the distance.
						distance.put(connecting.getIndex(), (distance.get(current.getIndex()) + connecting.getTime()));							
					}
					connecting = connecting.getNext();
				}
																					
				minCon = true;
			}
			else {
				// Current node duplicate.
				break;
			}
			
			// Station visited.
			visited.put(current.getIndex(), 1);
			captured.add(current);
			
			if (current.getIndex() == end){
				break;
			}
						
			// Find closest to captured nodes.
			minStation = new Station("min", 0);
			minStation.setTime(Integer.MAX_VALUE);
			int minTime = Integer.MAX_VALUE;
			for (Station s : captured){
				if (s.getNext() == null)
					continue;														
				
				Station next = s.getNext();
				// Find closest connection that's not been visited.
				while (next != null) {
					// Initialize visited.
					if (visited.get(next.getIndex()) == null)
						visited.put(next.getIndex(), -1);
									
					if (visited.get(next.getIndex()) == -1) {
						//System.out.println(" has not beed visited");
						if (next.getTime() < minTime){
							minTime = next.getTime();
							minStation = next;
							from = s;
						}
						break;
					}
					else {
						// Station has already been visited
						graph.dequeueConnection(s.getIndex());
					}
					next = next.getNext();
				}
			}
			
			// Capture closest station.
			graph.dequeueConnection(from.getIndex());
			current = graph.getStation(minStation.getIndex());
		}
		
		int time = 0;
		
		// Print path.
		System.out.println("Shortest Path");
		System.out.println("---------------");
		
		// Print path to end station.
		for (Station s : paths.get(end)){
			if (s != null) {
				System.out.println("id: " + s.getIndex() + "    Name: " + s.getName());
				time += s.getTime();
			}
		}
		System.out.println("Time: " + time);
	}
	
	// Reads metro text file.
	private static void readMetro(String file) throws IOException{
		BufferedReader metroFile = new BufferedReader(new FileReader(new File(file)));
		String line = metroFile.readLine();
		String[] words = line.split(" ", 2);
				
		// Create graph with array size from first line.
		graph = new Graph(strToInt(words[0]));

		// Add stations to graph.
		line = metroFile.readLine();
		while (line != null) {
			// $ indicates stop.
			if (line.equalsIgnoreCase("$")){
				break;
				
			}
			
			words = line.split(" ", 2);
			//System.out.println(words[1] + " " + words[0]);
			graph.addStation(words[1], strToInt(words[0]));

			
			// Read next line.
			line = metroFile.readLine();
		}
				
		// Add the connections to each station.
		line = metroFile.readLine();		
		while (line != null) {
			// $ indicates stop.
			
			words = line.split(" ", 3);
			//System.out.println(words[2]);
			if (words[2].equals("-1")) {
				graph.addConnection(strToInt(words[0]), strToInt(words[1]), 90);
			}
			else {
				graph.addConnection(strToInt(words[0]), strToInt(words[1]), strToInt(words[2]));
			}

			// Read next line.
			line = metroFile.readLine();
		}
		//printGraph();
	}
	
	private static void printGraph(){
		for (int i = 0; i < graph.getSize(); i++){
			System.out.println("----------------------------");
			System.out.println("STATION:");
			System.out.println("----------------------------");
			Station s = graph.getStation(i);
			System.out.println("Name: " + s.getName() + "Number: " + s.getIndex());
			System.out.println("");
			System.out.println("Connections:");
			System.out.println("");
			s = s.getNext();
			while(s != null){
				System.out.println("Name: " + s.getName() + " Number: " + s.getIndex() + " Time: " + s.getTime());
				s = s.getNext();
			}
		}
	}

	// Open text file.
	private static BufferedReader openFile(String file) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(new File(file)));
		} catch (Exception e) {
			System.out.println("Error: Could not open file " + file);
		}
		return in;
	}

	private static String readLine(BufferedReader in) {
		String s = null;
		try {
			s = in.readLine();
		} catch (Exception e) {
			System.out.println("Error: could not in line");
		}
		return s;
	}

	private static int strToInt(String s) {
		int n = -1;
		try {
			n = Integer.parseInt(s);
		} catch (Exception e) {
			System.out.println("Error: Parameters expected integers");
			System.exit(0);
		}
		return n;
	}
}