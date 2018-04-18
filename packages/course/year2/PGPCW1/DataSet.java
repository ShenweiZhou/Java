// 6521878 zy21878 Yuyang ZHOU
import java.util.*;
import java.io.*;

/** The DataSet class for PGP CW1. 
* This class represent the data set as training data set.
* @author Yuyang ZHOU
* April 2018
*/
public class DataSet{
	private DataSample[] dataArray;
	
	/** The constructor that will do quite a lot of things.
	* It will first find the size of the data set, them extract the label 
	* and attributes of each data sample inside to the private field dataArray.
	* @param filename The name of file containing training data set.
	* It will deal with exceptions thrown by other methods, including FileNotFoundException and NumberFormatException.
	*/
	public DataSet(String filename){
		try{
			int dataSetSize=getDataSetSize(filename);
			if(dataSetSize==0){
				System.out.println("Warning: There is no data in the file "+filename+"!");
			}
			
			dataArray=findDataArray(filename,dataSetSize);
		
		}
		catch(FileNotFoundException e){
			System.out.println("Error: Target file does not exist!");
			System.exit(2);
		}
		catch(NumberFormatException e){
			System.out.println("Error: Wrong type of input value!");
			System.exit(3);
		}
		catch(FormatNotMatched e){
			System.out.println("Error: Wrong number of input attributes!");
			System.exit(3);
		}
		catch(Exception e){
			System.out.println("Error: exception detected!");
			System.exit(3);
		}
		
	}
	
	/** The method to get the number of samples in target data set.
	* @param filename The name of file that target data set is in.
	* @return The number of data samples in target file.
	* @throws FileNotFoundException When the scanner cannot find target file, this exception will be thrown.
	*/
	public int getDataSetSize(String filename) throws FileNotFoundException {
		Scanner reader=null;
		int size=0;
		try{
			reader=new Scanner(new File(filename));
			while(reader.hasNextLine()){
				++size;
				reader.nextLine();
			}
			return size;
		}
		finally{ 
			if(reader!=null){
				reader.close();
			}
		}
	}
	
	/** The method that is able to extract the label and attributes of data samples from file.
	* @param filename The target file holding the data samples.
	* @param dataSetSize The total number of samples in data set.
	* @return The array of all data samples in target file.
	* @throws FileNotFoundException When the scanner cannot find target file, this exception will be thrown.
	* @throws NumberFormatException Thrown to indicate that the application has attempted to convert a string to one of the numeric types,
	* 								but that the string does not have the appropriate format.
	*/
	public DataSample[] findDataArray (String filename, int dataSetSize)
	throws FileNotFoundException,NumberFormatException,FormatNotMatched{
		Scanner input=null;
		DataSample[] newDataSample=new DataSample[dataSetSize];
		try{
			input=new Scanner(new File(filename));
			
			for(int i=0;i<dataSetSize;i++){
				newDataSample[i]=createDataSample(input.nextLine());
			}
			return newDataSample;
		}
		finally{
			if(input!=null){
				input.close();
			}
		}
	}
	
	/** The method that can extract label and attributes to form a data sample from a string.
	* @param rawString The original string that may contain the information to build a data sample.
	* @return The data sample extracted from the given string.
	* @throws NumberFormatException Thrown to indicate that the application has attempted to convert a string to one of the numeric types,
	* 								but that the string does not have the appropriate format.
	*/
	public DataSample createDataSample(String rawString) 
	throws NumberFormatException,FormatNotMatched{
		String[] splited=rawString.split(",");
		int label=Integer.parseInt(splited[0]);
		int lengthOfAtrs=splited.length-1;
		if(lengthOfAtrs!=13){
			throw new FormatNotMatched(); 
		}
		double[] atrs=new double[lengthOfAtrs];
		for(int i=0;i<lengthOfAtrs;i++){
			atrs[i]=Double.parseDouble(splited[i+1]);
		}
			
		return new DataSample(label,atrs);
	}
	
	/** The method to get mean value of each attribute for all data samples with particular label.
	* @param label The label to get mean value.
	* @return the mean value of each attribute for all data samples with the given label.
	*/
	public double[] getMean(int label){
		int numOfAtrs=dataArray[0].getnumOfAttributes();
		int numOfDataSamples=dataArray.length;
		int numOfThisLabel=0;
		double[] mean=new double[numOfAtrs];
		
		for(DataSample dArray : dataArray){
			if(dArray.getLabel()==label){
				numOfThisLabel++;
				for(int i=0;i<numOfAtrs;i++){
					mean[i]+=dArray.getAttributes()[i];
				}
			}
		}
		for(int j=0;j<numOfAtrs;j++){
			mean[j]/=numOfThisLabel;
		}
		return mean;
	}
	
	
	/** The method to get standard deviation value of each attribute for all data samples with particular label.
	* @param label The label to get standard deviation value.
	* @return the standard deviation value of each attribute for all data samples with the given label.
	*/
	public double[] getStd(int label){
		int numOfAtrs=dataArray[0].getnumOfAttributes();
		int numOfDataSamples=dataArray.length;
		int numOfLabel=0;
		double[] std=new double[numOfAtrs];
		
		for(DataSample dArray : dataArray){
			if(dArray.getLabel()==label){
				numOfLabel++;
				for(int i=0;i<numOfAtrs;i++){
					std[i]+=Math.pow((dArray.getAttributes()[i] - this.getMean(label)[i]),2);
				}
			}
		}
		for(int j=0;j<numOfAtrs;j++){
			std[j]=Math.pow(std[j]/(numOfLabel-1),0.5);
		}
		
		return std;
	}
	
	
	/** The getter for data set.
	* @return The array of data samples in this data set.
	*/	
	public DataSample[] getDataSet(){
		return dataArray;
	}
	
	/** The method to carry out the task 2 requirements.
	* @param filename The name of target file to hold the results of task 2.
	* It will deal with FileNotFoundException in try block.
	*/
	public void task2Output(String filename){
		String taskOutputString =
		"Mean of class 1: " + Arrays.toString(this.getMean(1)) + "\n"
		+"Std of class 1: " + Arrays.toString(this.getStd(1)) + "\n"
		+ "Mean of class 2: " + Arrays.toString(this.getMean(2)) + "\n"
		+ "Std of class 2: " + Arrays.toString(this.getStd(2)) + "\n"
		+ "Mean of class 3: " + Arrays.toString(this.getMean(3)) + "\n"
		+ "Std of class 3: " + Arrays.toString(this.getStd(3)) + "\n";
		try{
		PrintWriter output=new PrintWriter(new File(filename));
		output.print(taskOutputString);
		output.close();
		}
		catch(FileNotFoundException e){
			System.out.println("Error: Failed to find or create target file "+filename+"!");
			System.exit(2);
		}
	}
	
}

class FormatNotMatched extends Exception{
	
}