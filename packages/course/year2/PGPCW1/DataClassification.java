// 6521878 zy21878 Yuyang ZHOU
import java.util.*;
import java.io.*;

/** The DataClassification class inheriting DataSet for PGP CW1. 
* This class represent the new data set as testing data set.
* @author Yuyang ZHOU
* April 2018
*/
public class DataClassification extends DataSet{
	private DataSet newData;
	
	/** The constructor that take two strings as filenames.
	* @param trainfilename The name of file for the training data set.
	* @param newdatafilename The name of file for the testing data set.
	*/
	public DataClassification(String trainfilename, String newdatafilename){
		super(trainfilename);
		newData = new DataSet(newdatafilename);
	}
	
	/** The getter for the testing data set.
	* @return The testing data set.
	*/
	public DataSet getNewData(){
		return newData;
	}
	
	/** The method that is designed according to the requirements of task 4.
	* It will loop through all the data samples in the testing data set and classify them with nearest neighbor classification,
	* then change the label of those testing data samples. Finally, it will print the lines required to the file given as argument.
	* @param filename The name of file that will hold the results.
	* If the file with the given name was not created or found, 
	* the method will print an error message and exit the program with exiting code 2.
	*/
	public void task4Output(String filename){
		int numOfIndex=newData.getDataSet().length;
		int sampleIndex=0;
		int testSampleLabel=0;
		PrintWriter output=null;
		try{
			output=new PrintWriter(new File(filename));
			for(;sampleIndex<numOfIndex;sampleIndex++){
				
				newData.getDataSet()[sampleIndex].setLabel(nnClassification(newData.getDataSet()[sampleIndex]));
				testSampleLabel=newData.getDataSet()[sampleIndex].getLabel();
				String taskOutputString = "The " + Integer.toString(sampleIndex+1) + 
				"-th new sample belongs to class " + Integer.toString(testSampleLabel) + "\n";
				output.print(taskOutputString);
			}
		}
		catch(FileNotFoundException e){
			System.out.println("Error: Failed to find or create target file "+filename+"!");
			System.exit(2);
		}
		finally{
			if(output!=null){
				output.close();
			}
		}
	}
	
	/** The method to carry out near neighbor classification.
	* @param datasp The given testing data sample.
	* @return The label of data sample in train data set with the least distance to the given sample.
	*/
	public int nnClassification(DataSample datasp){
		double leastDistance=this.getDataSet()[0].distance(datasp);
		int newLabel=this.getDataSet()[0].getLabel();
		
		for(DataSample dSample : this.getDataSet()){
			double newDistance=dSample.distance(datasp);
			if(leastDistance>newDistance){
				leastDistance=newDistance;
				newLabel=dSample.getLabel();
			}
		}
		return newLabel;
	}
}