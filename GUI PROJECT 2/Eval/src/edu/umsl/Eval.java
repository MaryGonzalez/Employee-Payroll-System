// MARY GONZALEZ
// MASHIYATH HAQUE

package edu.umsl;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
//import java.util.Locale;
//import java.text.*;
import javax.swing.*;
import javax.swing.event.*;
//import org.apache.derby.jdbc.*;
//import sun.jdbc.odbc.JdbcOdbcDriver;

/**
 *
 * @author brilaw
 */
public class Eval extends JFrame implements ActionListener, ItemListener 
{
    //DECLARE THE ELEMENTS OR OBJECTS THAT YOU WILL PUT IN YOUR FRAME
    //NOTICE HOW A PANEL IS CREATED FOR EACH ONE THIS WILL MAKE IT EASIER BUILD

    public JLabel teamLabel;
    private JComboBox teamComboBox;
    public JComboBox imagesComboBox;
    private JPanel teamPanel;
    private JPanel buttonPanel;
    private JButton submitButton;
    private JButton clearButton;
    private JButton calcAvgButton;
    public JTextArea commentBox;
    
    //private JPanel buttonPanel;
    //private JButton submitButton;
    private JPanel sliderPanel;
    private JSlider Q1Slider;
    private JSlider Q2Slider;
    private JSlider Q3Slider;
    private JSlider Q4Slider;
    private JLabel Q1Label;
    private JLabel Q2Label;
    private JLabel Q3Label;
    private JLabel Q4Label;
    private JLabel commentLabel;
    private JLabel calcAvgLabel;
    private JLabel avgLabel;

    //these are fields that will be used to hold the values pulled from the interface
    String teamname;
    String comments;
    int q1;
    int q2;
    int q3;
    int q4;
    //String comments;
    double teamavg;
    int slider_min = 70;
    int slider_max = 100;
    int slider_int = 70;

    // instance variables used to manipulate database
    private Connection myConnection;
    private Statement myStatement;
    private ResultSet myResultSet;

   //MAIN METHOD: NOTICE WE TAKE IN THE ARGUMENTS THAT ARE
    //PASSED IN AND INSTANTIATE OUR CLASS WITH THEM
    public static void main(String args[]) 
    {
        // check command-line arguments
        //if ( args.length == 2 )
        //{
        // get command-line arguments
        String databaseDriver = "org.apache.derby.jdbc.ClientDriver";
        //String databaseDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
        String databaseURL = "jdbc:derby://localhost:1527/PureEval;";

        // create new Eval
        Eval eval = new Eval(databaseDriver, databaseURL);
        eval.createUserInterface();
        eval.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //}
        //else // invalid command-line arguments
        //{
        //   System.out.println( "Usage: java EVAL needs databaseDriver databaseURL" );
        //}
    }

    //CONSTRUCTOR: WE SET UP OUR DATABASE HERE THEN MAKE A CALL
    //TO A FUNCTION CALLED CREATEUSERINTERFACE TO BUILD OUR GUI
    public Eval(String databaseDriver, String databaseURL) {
        // establish connection to database
        try {
         // load Sun driver
            //Class.forName( databaseDriver );
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            // connect to database
            myConnection = DriverManager.getConnection(databaseURL);

            // create Statement for executing SQL
            myStatement = myConnection.createStatement();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
      //catch ( ClassNotFoundException exception )
        // {
        //   exception.printStackTrace();
        //}
        // set up accountNumberJComboBox

     // createUserInterface(); // set up GUI
    }

    private void createUserInterface() {
        // get content pane for attaching GUI components
        Container contentPane = getContentPane();

        contentPane.setLayout(null);

        // panel for team selection
        teamPanel = new JPanel();
        teamPanel.setBounds(40, 20, 500, 48);
        teamPanel.setBorder(BorderFactory.createEtchedBorder());
        teamPanel.setLayout(null);
      //contentPane.add( teamPanel );

        // Slider Panel
        sliderPanel = new JPanel();
        sliderPanel.setBounds(40, 100, 500, 250);
        sliderPanel.setBorder(BorderFactory.createEtchedBorder());
        sliderPanel.setLayout(null);

        // set up Team Select Label
        teamLabel = new JLabel();
        teamLabel.setBounds(25, 15, 100, 20);
        teamLabel.setText("Teams:");
        //teamPanel.add( teamLabel );

        //add q1 Output
        Q1Label = new JLabel();
        Q1Label.setBounds(360, 30, 380, 50);
        Q1Label.setText("Technical Score :");
        //sliderPanel.add(Q1Label);

        //add q2 output
        Q2Label = new JLabel();
        Q2Label.setBounds(360, 85, 380, 50);
        Q2Label.setText("Usefulness Score :");
        //sliderPanel.add(Q2Label);

        //add q3 output
        Q3Label = new JLabel();
        Q3Label.setBounds(360, 135, 380, 50);
        Q3Label.setText("Clarity Score :");
        //sliderPanel.add(Q3Label);

        //add q4 output 
        Q4Label = new JLabel();
        Q4Label.setBounds(360, 185, 380, 50);
        Q4Label.setText("Overall Score :");
        //sliderPanel.add(Q4Label);

        // label for comments
        commentLabel = new JLabel();
        commentLabel.setBounds(25, 35, 100, 20);
        commentLabel.setText("Comments:");
        //buttonPanel.add( commentLabel );

        // label for average
        calcAvgLabel = new JLabel();
        calcAvgLabel.setBounds(25, 100, 250, 20);
        calcAvgLabel.setText("Computed average from questions above:");
        //buttonPanel.add(calcAvgLabel);
        
        // print average
        avgLabel = new JLabel();
        avgLabel.setBounds(350, 150, 250, 20);
        
        String hold = Double.toString(teamavg);
        avgLabel.setText(hold);
        //avgLabel.setText(String.valueOf(hold)); 
        //buttonPanel.add(calcAvgLabel);
        
        

        // set up q1 slider     
        Q1Slider = new JSlider(JSlider.HORIZONTAL, slider_min, slider_max, slider_int);
        Q1Slider.setBounds(25, 30, 325, 50);
        Q1Slider.setMajorTickSpacing(8);
        Q1Slider.setPaintTicks(true);
        Q1Slider.setPaintLabels(true);
        Font font = new Font("Serif", Font.PLAIN, 10);
        Q1Slider.setFont(font);

        Q1Slider.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                Q1Label.setText("Technical Score : "
                        + ((JSlider) e.getSource()).getValue());
            }
        });

      // set up q2 slider     
        Q2Slider = new JSlider(JSlider.HORIZONTAL, slider_min, slider_max, slider_int);
        Q2Slider.setBounds(25, 85, 325, 50);
//      Q2Slider.addChangeListener(this);
        Q2Slider.setMajorTickSpacing(8);
        Q2Slider.setPaintTicks(true);
        Q2Slider.setPaintLabels(true);
        Q2Slider.setFont(font);

        Q2Slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                Q2Label.setText("Usefulness Score : "
                        + ((JSlider) e.getSource()).getValue());
            }
        });

      // set up q3 slider     
        Q3Slider = new JSlider(JSlider.HORIZONTAL, slider_min, slider_max, slider_int);
        Q3Slider.setBounds(25, 135, 325, 50);
//      Q3Slider.addChangeListener(this);
        Q3Slider.setMajorTickSpacing(8);
        Q3Slider.setPaintTicks(true);
        Q3Slider.setPaintLabels(true);
        Q3Slider.setFont(font);

        Q3Slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                Q3Label.setText("Clarity Score : "
                        + ((JSlider) e.getSource()).getValue());
            }
        });

      // set up q4 slider     
        Q4Slider = new JSlider(JSlider.HORIZONTAL, slider_min, slider_max, slider_int);
        Q4Slider.setBounds(25, 185, 325, 50);
//      Q4Slider.addChangeListener(this);
        Q4Slider.setMajorTickSpacing(8);
        Q4Slider.setPaintTicks(true);
        Q4Slider.setPaintLabels(true);
        Q4Slider.setFont(font);

        Q4Slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                Q4Label.setText("Overall Score : "
                        + ((JSlider) e.getSource()).getValue());
            }
        });

      //add q1 Output
//      sliderPanel.add(Q1Slider);
//      sliderPanel.add(Q2Slider);
//      sliderPanel.add(Q3Slider);
//      sliderPanel.add(Q4Slider);
//      contentPane.add(sliderPanel);
//      submitButton = new JButton("Submit");
//      submitButton.setBounds(50, 360, 78, 380);
//      buttonPanel.add(submitButton);
//      contentPane.add(buttonPanel);
//      
//      submitButton.addActionListener(this);
//      teamComboBox.addItemListener(this);
        // set up Instructor Label
        teamLabel = new JLabel();
        teamLabel.setBounds(160, 15, 100, 20);
        teamLabel.setText("Teams:");
        //teamPanel.add(teamLabel);

        // set up accountNumberJComboBox
        teamComboBox = new JComboBox();
        teamComboBox.setBounds(230, 15, 96, 25);
        teamComboBox.addItem("Select");
        teamComboBox.setSelectedIndex(0);
        
      

        // Panel for comment, calc avg, submit, clear
        buttonPanel = new JPanel();
        buttonPanel.setBounds(40, 365, 500, 270);
        buttonPanel.setBorder(BorderFactory.createEtchedBorder());
        buttonPanel.setLayout(null);

        buttonPanel.add(commentLabel);
        buttonPanel.add(calcAvgLabel);
        buttonPanel.add(avgLabel);
        

        commentBox = new JTextArea("\n \n");
        commentBox.setBounds(110, 10, 350, 70);
        
        calcAvgButton = new JButton("Calculate Average");
        calcAvgButton.setBounds(125, 140, 190, 40);
        
        submitButton = new JButton("Submit");
        submitButton.setBounds(45, 210, 178, 30);
        submitButton.setEnabled(false);
        
        
        
        //buttonPanel.add(submitButton);
        //contentPane.add(buttonPanel);

        clearButton = new JButton("Clear");
        clearButton.setBounds(260, 210, 178, 30);
        

  
        submitButton.addActionListener(this);
        teamComboBox.addItemListener(this);
        clearButton.addActionListener(this);
        calcAvgButton.addActionListener(this);
//        avgBoxLabel.addActionListener(this);
        contentPane.add(teamPanel);

        teamPanel.add(teamLabel);

        sliderPanel.add(Q1Label);
        sliderPanel.add(Q2Label);
        sliderPanel.add(Q3Label);
        sliderPanel.add(Q4Label);

        sliderPanel.add(Q1Slider);
        sliderPanel.add(Q2Slider);
        sliderPanel.add(Q3Slider);
        sliderPanel.add(Q4Slider);
        contentPane.add(sliderPanel);

        buttonPanel.add(submitButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(commentBox);
        buttonPanel.add(calcAvgButton);
       

        teamPanel.add(teamLabel);
        teamPanel.add(teamComboBox);
        contentPane.add(buttonPanel);

        loadTeams();

        setTitle("PROJECT EVALUATION");   // set title bar string
        setSize(600, 700); // set window size
        setVisible(true);  // display window
    }

    private void loadTeams() 
    {
        // get all account numbers from database
        try 
        {

            myResultSet = myStatement.executeQuery("SELECT DISTINCT TEAMNAME FROM APP.TEAMS");

            while (myResultSet.next()) 
            {
                teamComboBox.addItem(myResultSet.getString("TEAMNAME"));
            }

            myResultSet.close(); // close myResultSet

        } // end try
        catch (SQLException exception) 
            {
                exception.printStackTrace();
            }
    }

    @Override
    public void actionPerformed(ActionEvent event) 
    {

        //JOptionPane.showMessageDialog(null, "You pressed: " + teamComboBox.getSelectedItem().toString());
        //Object obj = teamComboBox.g
        //teamname = teamComboBox.getSelectedItem().toString();
        //int x = teamComboBox.getSelectedIndex();
        // q1 = 5;
        //q2 = 3;
        //updateTeams();
        //System.out.println(teamComboBox.getSelectedIndex() + "     " + (String)teamComboBox.getSelectedItem());
        
        Object source = event.getSource();
        
        
        if(source == clearButton)
        {
            q1 = 100;
            q2 = 100;
            q3 = 100;
            q4 = 100;
            
            
            Q1Slider.setValue(slider_int);
            Q2Slider.setValue(slider_int);
            Q3Slider.setValue(slider_int);
            Q4Slider.setValue(slider_int);
            
            commentBox.setText("\n \n");
            
            String hold = Double.toString(teamavg);
            avgLabel.setText("");  
           // avgLabel.setText(null);
            //calcAvgButton.setText(String.valueOf(hold));
            teamComboBox.setSelectedIndex(0);
           
        }
        
        if(source == calcAvgButton)
        {
                
             q1 = Q1Slider.getValue();  
             q2 = Q2Slider.getValue();
             q3 = Q3Slider.getValue();
             q4 = Q4Slider.getValue();
             
            teamavg = (q1 + q2 + q3 + q4)/4;
            String hold = Double.toString(teamavg);
            avgLabel.setText(hold);
            submitButton.setEnabled(true);
        }
        
        if(source == submitButton)
        {
           comments = commentBox.getText();
           //updateTeams();
           JOptionPane.showMessageDialog(null, "Your grades have been submitted \n" + "You gave team " + teamComboBox.getSelectedItem().toString()+  " a(n) " + teamavg + ("% average")); 
           updateTeams();
           submitButton.setEnabled(false);
        }
        

    }

   // @Override
   /* public void itemStateChanged(ItemEvent event)
     {
       
     if ( event.getSource() == rb1 && event.getStateChange() == ItemEvent.SELECTED)
     {
     q1 = Integer.parseInt(rb1.getText());
     }
     else if (event.getSource() == rb2 && event.getStateChange() == ItemEvent.SELECTED)
     {
     q1 = Integer.parseInt(rb2.getText());
     }
     else if (event.getSource() == rb3 && event.getStateChange() == ItemEvent.SELECTED)
     {
     q1 = Integer.parseInt(rb3.getText());
     }
     else if( event.getSource() == rb1 && event.getStateChange() == ItemEvent.DESELECTED)
     {
     JOptionPane.showMessageDialog(null, "Eggs are not supposed to be green.");
     }
     }*/
    private void updateTeams() 
    {
        // update balance in database
        try {
            //
            String sql = "UPDATE APP.TEAMS SET Q1TECHNICAL = " + q1 + ", " + "Q2USEFUL = " + q2 + ", " + "Q3CLARITY = " + q3 + ", "
                    + "Q4OVERALL = " + q4 + ", " + "AVGSCORE = " + teamavg + ", " + "COMMENTS = " + "'" + comments + "'" + " WHERE " + "TEAMNAME = "
                    + "'" + (String)teamComboBox.getSelectedItem() + "'";
            
            myStatement.executeUpdate(sql);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent event) 
    {
//        if ( event.getStateChange() == ItemEvent.SELECTED )
//        {
//                int x = teamComboBox.getSelectedIndex();
//        }
    }

}
