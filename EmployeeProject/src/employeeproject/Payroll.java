
package employeeproject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Payroll
{
    Employee[] myaccts = new Employee[3];
    
    static Scanner sc = new Scanner(System.in);
        
    public static void main(String[] args) throws IOException
    {
       Payroll myEMP = new Payroll();
       myEMP.SelectMenu();
    }
    
    public void SelectMenu() throws IOException
    {
       int selection;
              
       boolean exit = false;
        
       while(!exit)
       {
           
              System.out.println("1) Populate employee");
              System.out.println("2) Select employee");
              System.out.println("3) Save employee");
              System.out.println("4) Load employee");
              System.out.println("5) Exit");
              System.out.print("Please select an option: ");
              selection = sc.nextInt();
               if (selection < 1 ||  selection > 5)
               {
                   System.out.println("Invalid choice. Try again: ");
               }
          
        
                switch(selection)
                       {
                           case 1:
                               populate();
                               break;
                           case 2:
                               select();                       
                               break;
                           case 3:
                               save();
                               break;
                           case 4:
                               load();
                               break;
                           case 5:
                               exit = true;
                               break;
                        }
       }   
    
    }
    
    public void populate()
    {
        int selectedIndex =0;
        for(int i = 0; i<myaccts.length; i++)
        {
            if(myaccts[i]== null)
            {
                selectedIndex = i;
                break;
            }
        }
        
        char input;
        System.out.print("Are you an Hourly (H), Salary (S), or Commission (C) employee? ");
        input = sc.next().charAt(0);
        
                
        if(input == 'H' || input == 'h')
        {
            myaccts[selectedIndex] = new HourlyEmployee();
            myaccts[selectedIndex].computeGross();
        
        }
        else if(input == 'S' || input == 's')
        {
            myaccts[selectedIndex] = new SalaryEmployee();
            myaccts[selectedIndex].computeGross();
        }
        else if(input == 'C'|| input == 'c')
        {
            myaccts[selectedIndex] = new CommissionEmployee();
            myaccts[selectedIndex].computeGross();
        }
        
    }
    
    public void select() throws IOException
    {
        try 
        {
            System.out.print("Please select an employee 0,1,2: ");
            int input = sc.nextInt();
            myaccts[input].menu();
        }
        catch(Exception ioe)
        {
            System.out.println("Please try a different employee");
            System.out.println(" ");
            select();
        }
       
    }

    public void save()
    {
        try
        {            
        FileOutputStream outStream = new FileOutputStream("E:/file1.out");
        ObjectOutputStream os = new ObjectOutputStream(outStream);
        os.writeObject(myaccts);
        os.flush();
        os.close();
        }
        catch(IOException ioe)
        {
            System.err.println(ioe);
        }
            
            
            
            
            
            
    }
    
    public void load()
    {
        
        try
        {                
        FileInputStream inStream = new FileInputStream("E:/file1.out");
        ObjectInputStream is = new ObjectInputStream(inStream);
        myaccts = (Employee[]) is.readObject();
        is.close();
        }
        catch(Exception ioe)
        {
            System.out.println(ioe.getMessage());
        }
            
            
            
    }
    
}
