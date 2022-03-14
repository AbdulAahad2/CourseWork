import java.io.Reader;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

        
public class Coursework2 {
  public static int count = 0;
  public static int cycles=0;
  public static BufferedReader br;
  final static int  hidden_layer_neuron=64;
    static double Weight_neuron[][]=new double[hidden_layer_neuron][64];
    static double Weight_output[][]=new double[10][hidden_layer_neuron];
    static double accuracy=0;
    static double[] neuron_hiddenlayer=new double[hidden_layer_neuron];
    static double[] outputNeuron=new double[10];
    static double[] dataSample=new double[65];
    static double errorOutput[]=new double[10];
    static double errorHidden[]=new double[hidden_layer_neuron];
    static int the_65_value=0;
    static int success=0;
    static double rand= Random();
    static double[] map=new double[10];
  public static void main(String [] args) throws FileNotFoundException {

    for (int i = 0; i < hidden_layer_neuron; i++) {
        for (int j = 0; j < 64; j++) {
            Weight_neuron[i][j] = Random();
            //System.out.println(Weight_neuron[i][j]);
        }
    }
    for(int i=0;i<hidden_layer_neuron;i++){
        errorHidden[i]=Random();
    }

    for (int i = 0; i < 10; i++) {
        for (int j = 0; j < hidden_layer_neuron; j++) {
            Weight_output[i][j] = Random();
        }
    }

     while (cycles<=10) {
            String line;
            
            br = new BufferedReader(new FileReader("cw2DataSet1.csv"));
            try {
              while ((line = br.readLine()) != null) {
                  count++; 
                  //Going through the File 
                  String[] string = line.split(",");
                  for (int i = 0; i < string.length; i++) {
                      dataSample[i] = Integer.valueOf(string[i]);
                  }
                 //Splitting the data with commas
                  String[] cols = line.split(",");
                  
                  the_65_value = Integer.parseInt(cols[64]);
                  //Making a Map for the output neuron
                  
                  for(int i=0;i<10;i++){
                      map[i]=0;
                  }
                  map[the_65_value] = 1;
                  feedForward();
                  if (testError()) {
                      training();
                  } else{
                      success++;
                  }
                      
                  
                      
              }
              accuracy=(success*100)/count;
             
               
            } catch (NumberFormatException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            } catch (IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
            accuracy=(success*100)/count;
            System.out.println(accuracy);
            count=0;
            cycles++;
            //To show Success rate
            //System.out.println("Success= "+success);
            success=0;
            //To show Accuracy
            //System.out.println(accuracy);

        }
        try {
          testing(0);
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

        // for(int i =0; i<dataSample.length;i++){
        //     System.out.println(dataSample[i]);
        // }
    
  }
    private static Reader FileReader(String string) {
    return null;
  }
    
   
    private static void feedForward(){
        neuron_hiddenlayer=HiddenSum();
        double[] temp = HiddenSum();
        // for(int i = 0; i < neuron_hiddenlayer.length; i ++){
        //     System.out.println(temp[i]);
        // }
        outputNeuron=OutputSum();
    }
    private static double[] HiddenSum(){
        double sum_hidden_Node=0;
        double sigmoidValue=0;
        //summing the hidden layer
        for(int i=0;i< neuron_hiddenlayer.length;i++){
            for(int j=0;j<dataSample.length-1;j++){
                sum_hidden_Node=sum_hidden_Node+(Weight_neuron[i][j]*dataSample[j]);
            }
            sigmoidValue=(int)(1/(1+Math.pow(2.7,-sum_hidden_Node)));
            neuron_hiddenlayer[i]=sigmoidValue;
        }
        return neuron_hiddenlayer;
    }
    private static double[] OutputSum(){
        double sum_output_Node=0;
        //output neurons
        for(int i=0;i<10;i++){
            for(int j=0;j<hidden_layer_neuron;j++){
                sum_output_Node=sum_output_Node+(Weight_output[i][j]*neuron_hiddenlayer[j]);
            }
            
            if(sum_output_Node>0.0){
                outputNeuron[i]=1;
            }
            else
                outputNeuron[i]=0;
        }
        return outputNeuron;
    }
    private static boolean testError(){
        double arr[] = new double[10];
        boolean ans = false;
        for(int i = 0; i < 10; i ++){
           if (map[i] != outputNeuron[i]) {
               ans = false;
                return false;
            }
            else {
                ans = true;
                return true;
            }
        }
        return ans;
            
    }
    private static void training(){
        for(int i =0; i<10;i++){
         errorOutput[i]=map[i]-outputNeuron[i];   
        }
        
        int errorTemp=0;
        for(int j=0;j<hidden_layer_neuron;j++){
            errorTemp=0;
            for(int i=0;i<10;i++){
                errorTemp+=(errorOutput[i]*Weight_output[i][j]);
            }
            errorHidden[j]=neuron_hiddenlayer[j]*(1-neuron_hiddenlayer[j])*errorTemp;
        }
        for(int i=0;i<10;i++){
            for(int j=0;j<hidden_layer_neuron;j++) {
                Weight_output[i][j]=(int)(Weight_output[i][j]+(0.001*neuron_hiddenlayer[j]*errorOutput[i]));
            }
        }
        for (int i = 0; i < hidden_layer_neuron; i++) {
            for (int j = 0; j < 64; j++) {
                Weight_neuron[i][j] = (int)(Weight_neuron[i][j]+(0.001*dataSample[j] * errorHidden[i]));
            }
        }



    }
    private static void testing(int count) throws IOException {
        String line;
        BufferedReader br = new BufferedReader(new FileReader("cw2DataSet2.csv"));
        while ((line = br.readLine()) != null) {
            count++;
            String[] string = line.split(",");
            for (int i = 0; i < string.length; i++) {
                dataSample[i] = Integer.valueOf(string[i]);
            }
            the_65_value = Integer.parseInt(string[64]);
            double[] map=new double[10];
            // for(int i=0;i<10;i++){
            //     map[i]=0;
            // }
            map[the_65_value] = 1;
            feedForward();
            if (!testError()){
                success++;
            }
        }
        accuracy=(success*100)/count;
        System.out.println(accuracy);
    }
    public static double Random(){
        double max = 0.5;
       double min = -0.5;
       double range = max - min + 1;
      double rand = (double)(Math.random() * range) + 1;
        return rand;
    }

}

