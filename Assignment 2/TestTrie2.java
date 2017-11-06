import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

public class TestTrie {
    
   public static boolean cmpLexTest1(String a, String[] correct)
    {
        String[] s = a.split("[,]+|[ ]+|[\\r\\n]+", 0);
        
        if(s.length != correct.length)
            return false;
        for(int i = 0; i < correct.length; i++)
            if(!correct[i].equals(s[i]))
                return false;
        return true;
    }
  
    public static void Test1(){
    
		System.out.println("-------------------------------------------");
		System.out.println("Test1\n");
		MyTrie tree = new MyTrie();
		System.out.println("Populating the trie:");
		boolean result;
		int[] nodes = new Int[]{ 5, 6, 3, 65, 34, 12, 76, 43 }
		int count = 0;
		int num = nodes.length;
		
        for (int i = 0; i < num; i++){ 
			System.out.println("Inserting " + nodes[i] + ": " + (result = tree.insert(nodes[i])));
			if(!result)
			{
				count++;
				System.out.println("Wrong Answer, Expected true, Given " + result);
			}
		}

		int nod = 0;
		int counter = 0;
		System.out.println("Number of Nodes: " + (nod = tree.numNodes()));
		if(nod != 4)
		{
			counter++;
			System.out.println("Wrong Answer, Expected 4, Given " + nod);
		}

		System.out.println("Printing Strings in Lexicographical Order:");

		// Create a stream to hold the output
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		// IMPORTANT: Save the old System.out!
		PrintStream old = System.out;
		// Tell Java to use your special stream
		System.setOut(ps);
		// Print some output: goes to your special stream
		tree.printStringsInLexicoOrder();
		// Put things back
		System.out.flush();
		System.setOut(old);
		// Show what happened
		System.out.print(baos.toString());
		int lex = 0;
		
		String[] correct = new String[num];
		
		// Sort array.
		for (int i = 0; i < num; i++){
			
		}
		
		if(!cmpLexTest1(baos.toString(), correct))
		{
			lex++;
			System.out.println("Wrong Answer (Lexicographical Order)");
			System.out.println("Expected: 0,000,01,0100,0101,011,111");
		}

		System.out.println("\nTest1 results:");
		System.out.println("Correct Answers (Inserting): " + (10-cnt) + "/10");
		System.out.println("Correct Answers (Number of Nodes): " + (2-counter) + "/2");
		System.out.println("Correct Answers (Lexicographical): " + (1-lex) + "/1");
		System.out.println("-------------------------------------------\n");
    }
   
    public static void main(String[] args) {
  
        Test1();
    }
}
