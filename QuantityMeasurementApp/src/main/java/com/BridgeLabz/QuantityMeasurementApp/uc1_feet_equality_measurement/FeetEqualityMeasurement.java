/*The QuantityMeasurementApp class is responsible for checking the equality of two numerical values measured in feet within the Quantity Measurement Application. It ensures accurate comparisons and handles various edge cases. */
package com.bridgelabz.quantityMeasurementApp.uc1_feet_equality_measurement;


import java.util.*;
public class FeetEqualityMeasurement {

    public static class Feet{
        private final double value;
        //Constructor to initialize the feet value
        public Feet(double value){
            this.value=value;
        }

        public double getValue(){
            return value;
        }

        @Override
        public boolean equals(Object obj){ //Override equals() from the Object class
            //Check if the object is the same reference (this == obj)
            if(this==obj){
                return true;
            }
            //Check if the object is null or a different type
            if(obj==null||getClass()!=obj.getClass()){
                return false;
            }
            Feet other=(Feet)obj; //Cast to Feet type safely
            return Double.compare(this.value, other.value)==0; //Compare double values using Double.compare() instead of == operator
        }

        @Override
        public int hashCode(){
            return Objects.hashCode(value);
        }
    }

    public static double validateInput(String input){
        try{
            return Double.parseDouble(input);
        }catch(NumberFormatException e){
            throw new IllegalArgumentException("Invalid input");
        }

    }

    public static void main(String args[]){
        //Taking input from the user
        Scanner sc=new Scanner(System.in);

        System.out.println("Enter first feet value:");
        String input1=sc.nextLine();

        System.out.println("Enter second feet value: ");
        String input2=sc.nextLine();
        //The class validates the input values to ensure they are numeric
        try {
            double value1 = validateInput(input1);
            double value2 = validateInput(input2);
            Feet feet1 = new Feet(value1);
            Feet feet2 = new Feet(value2);
            //The class compares the two values for equality.
            boolean result = feet1.equals(feet2);

            System.out.println("Input: " + value1 + " ft and " + value2 + " ft");
            System.out.println("Result: " + result);
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }


    }
}