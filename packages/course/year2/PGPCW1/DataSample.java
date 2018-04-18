// 6521878 zy21878 Yuyang ZHOU
/** The DataSample class for PGP CW1. 
* This class represent a sample of data consists of one label and several attributes.
* @author Yuyang ZHOU
* April 2018
*/
public class DataSample{
	private int label;
	private int numOfAttributes;
	private double[] attributes;
	
	/** Constructor built as instructed.
	* @param lb The label of data sample.
	* @param atr An array of attributes of type double.
	*/
	public DataSample(int lb, double[] atr){
		this.label=lb;
		this.attributes=atr;
		this.numOfAttributes=atr.length;
	}
	
	/** The getter for label.
	* @return The label of this data sample.
	*/
	public int getLabel(){
		return label;
	}
	
	/** The setter for label of data sample.
	* @param newLabel An integer as new label number.
	*/
	public void setLabel(int newLabel){
		this.label=newLabel;
	}
	
	/** The getter for numOfAttributes.
	* @return The number of attributes in the data sample.
	*/
	public int getnumOfAttributes(){
		return numOfAttributes;
	}
	
	/** The getter for attributes.
	* @return Array of attributes in the data sample.
	*/
	public double[] getAttributes(){
		return attributes;
	}
	
	/** The method to calculate the distance from another data sample to this data sample.
	* @param dat Another data sample to be calculated.
	* @return The distance between two samples which is defined in the given document.
	* If the number of attributes in two samples are different, 
	* the method will print a error message and terminate the program with exiting code 1.
	*/
	public double distance(DataSample dat){
		double[] datAtr=dat.getAttributes();
		int datNumOfAtr=dat.getnumOfAttributes();
		if(datNumOfAtr!=this.numOfAttributes){
			System.out.println("Error: Cannot compare two samples with different number of attributes!");
			System.exit(1);
			return -1;
		}
		else{
			double dist=0;
			for(int i=0;i<numOfAttributes;i++){
				dist+=(double)(attributes[i]-datAtr[i])*(attributes[i]-datAtr[i]);
			}
			dist=Math.pow(dist,0.5);
			return dist;
		}
	}
	
}