// 6521878 zy21878 Yuyang ZHOU

/** The main class required in PGP CW1.
* This class will composite other classes to carry out the tasks in PGP CW1.
* @author Yuyang ZHOU
* April 2018
*/
public class main{
	
	/** The main method that is where the program starts.
	* @param args The command line arguments.
	*/
	public static void main(String[] args){
		DataSet train = new DataSet("wine.txt");
		
		train.task2Output("task2Result.txt");
		
		DataClassification newtest = new DataClassification("wine.txt","testwine.txt");
		
		newtest.task4Output("test4Result.txt");
		
	}
	
}