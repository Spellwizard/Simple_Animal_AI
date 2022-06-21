

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
//This is used to track keyboard inputs
import java.awt.event.KeyListener;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class OptionsMenu extends JFrame implements ActionListener {

    FileReader fRead = new FileReader("GameSettings.txt");

    Label ButtonInputer = null;

    ArrayList<String> aNames = new ArrayList<String>();

    ArrayList<Label> optionValue = new ArrayList<>();

    ArrayList<JButton> buttonArray = new ArrayList<>();

    ArrayList<JTextField> OptionSetting = new ArrayList<>();

    KeyListener InputTracker = new KeyListener() {

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("KeyPressed: "+e.getKeyCode());
            ButtonInputer.setText("Button Pressed: "+e.getKeyCode());
        }
        //these two are needed but will be unused
        @Override
        public void keyTyped(KeyEvent e){}
        @Override
        public void keyReleased(KeyEvent e) {}

    };

    public OptionsMenu(String windowName){

        initKeyListener();

        aNames.add("isdebug");
        aNames.add("frogEnergy");
        aNames.add("frogWidth");
        aNames.add("frogHeight");
        aNames.add("blueJetFilePath");
        aNames.add("blueJetFlipPath");
        aNames.add("frogdefaultHSpeed");
        aNames.add("frogdefaultVSpeed");
        aNames.add("booleangamePaused");
        aNames.add("Keycmd_PauseGame");
        aNames.add("Keycmd_StepRound");
        aNames.add("Keycmd_DecreaseSpeed");
        aNames.add("Keycmd_IncreaseSpeed");
        aNames.add("pelletCount");
        aNames.add("initPopulationSize");
        aNames.add("Keycmd_repopulateFood");
        aNames.add("frogSight");
        aNames.add("graphicsOn");
        aNames.add("Keycmd_ToggleGraphics");
        aNames.add("roundDuration");

        //make sure that on closure the options window is hidden in favour of the menu window being made visible
        //On Close of game window go back to the menu window
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Closing OptionsWindow");

                Menu.makeVisible();
                            }
        });

        System.out.println("New Sheet made: "+windowName);

        this.setTitle(windowName);

        onlineEXadaptations();
    }


    public void initKeyListener(){
        this.addKeyListener(InputTracker);
        this.setFocusable(true);
    }

    /**
     *
     * @param name Text of the JLabel
     * @param x Pos on the x axis
     * @param y Pos on the y axis
     * @return a jlabel with the given values
     */
    public JLabel createLabel(String name, int x, int y){
        //create a J Label with the string name as given
        JLabel i = new JLabel(name);
        //set the position of the label as given x,y
        //i.setBounds(x,y);
        //i.setSize(width,height);
        i.setVisible(true);
        i.validate();
        //System.out.println("Built Label: "+name+", "+i.validate(););
        return i;
    }


    public JTextField createText(String description, int column){

        JTextField i = new JTextField(description);

        i.setColumns(column);

        return i;
    }

    public JTextField createText(int column, int row, String description){

        JTextField i = new JTextField(description);

        i.setColumns(column);


        return i;
    }

    public void onlineExample(){
       // JFrame f = new JFrame("Text Field Examples");

        JFrame f = this;

        f.getContentPane().setLayout(new FlowLayout());

        f.getContentPane().add(createText("Text field 1",10));
        f.getContentPane().add(createText("Text field 2",10));
        f.getContentPane().add(createText("Text field 3",10));

        f.pack();
        f.setVisible(true);
    }


    public void onlineEXadaptations(){

        Font fFont = new Font(Font.SERIF, Font.PLAIN,  25);


        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints cst = new GridBagConstraints();


        //Add the title to the program options

        //set where the next component will be added on the row and column
        cst.gridx = 0; //column
        cst.gridy = 0; // row

        //add a label
        Label titleLabel = new Label("PROGRAM OPTIONS");
        titleLabel.setBackground(Color.blue);
        titleLabel.setForeground(Color.white);
        titleLabel.setFont(fFont);
        panel.add(titleLabel, cst);

        //somehow this is important for the layout control
        cst.fill = GridBagConstraints.HORIZONTAL;

        Color backColor = Color.lightGray;
        Color textColor = Color.white;

        int rowHeight = 50;
        int columnWidth = 300;



        for(int i =0;i<aNames.size();i++) {

            if(i%2!=0){
                backColor=Color.lightGray;
                textColor = Color.black;
            }
            else{
                backColor = Color.darkGray;
                textColor = Color.white;
            }

            //try to find the string value from the saved options items

            String value = fRead.findActualValue(aNames.get(i));

            //set where the next component will be added on the row and column
            cst.gridx = 0; //column
            cst.gridy = i+1; // row

            //create a label
            Label libel = new Label(aNames.get(i));
            libel.setBackground(backColor);
            libel.setForeground(textColor);
            libel.setPreferredSize(new Dimension(columnWidth,rowHeight));
            libel.setFont(fFont);
            optionValue.add(libel);
            panel.add(libel, cst);

            //somehow this is important for the layout control
            cst.fill = GridBagConstraints.HORIZONTAL;

            //set where the next component will be added on the row and column
            cst.gridx = 1; //column
            cst.gridy = i+1; // row

            System.out.println(fRead.findValue(value));


            //This is an attempt to have radio buttons but i kinda gave up b/c it was really easy to do so
            if(false&&fRead.variableType(fRead.findActualValue(value)) == fRead.BOOLEANRESULT) {
                //
                JRadioButton boolTrue = new JRadioButton("True");
                JRadioButton boolFalse = new JRadioButton("False");

                panel.add(boolTrue);

                cst.gridx = 2; //column
                cst.gridy = i+1; // row

                panel.add(boolFalse);

            }
            else{
                //add a text field and populate with the value
                JTextField textbox = new JTextField(value);
                textbox.setBackground(backColor);
                textbox.setForeground(textColor);
                textbox.setPreferredSize(new Dimension(columnWidth, rowHeight));
                textbox.setFont(fFont);
                textbox.addKeyListener(InputTracker);
                OptionSetting.add(textbox);
                panel.add(textbox, cst);
            }




            //somehow this is important for the layout control
            cst.fill = GridBagConstraints.HORIZONTAL;

        }

        //add the save button to override the given values into the text file
        //set where the next component will be added on the row and column
        cst.gridx = 0; //column
        cst.gridy = aNames.size()+2; // row

        //add a save button that will update the new values into the text file
        ButtonInputer = new Label("Button Pressed: ");

        ButtonInputer.setBackground(new Color(40,40,40));
        ButtonInputer.setForeground(Color.white);
        ButtonInputer.setFont(fFont);
        panel.add(ButtonInputer, cst);

        //somehow this is important for the layout control
        cst.fill = GridBagConstraints.HORIZONTAL;

        //add the save button to override the given values into the text file
        //set where the next component will be added on the row and column
        cst.gridx = 1; //column
        cst.gridy = aNames.size()+2; // row

        //add a save button that will update the new values into the text file
        JButton BSave = new JButton("Save Changes");
        BSave.setActionCommand("BSave");
        BSave.addActionListener(this);

        BSave.setBackground(new Color(50,255,50));
        BSave.setFont(fFont);

        panel.add(BSave, cst);

        //somehow this is important for the layout control
        cst.fill = GridBagConstraints.HORIZONTAL;

        panel.addKeyListener(InputTracker);

        panel.setVisible(true);

        panel.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));

        panel.setBackground(new Color(30,30,175));

        this.getContentPane().add(panel);

        this.getContentPane().setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));

        this.pack();
        this.setVisible(true);

        this.setSize(600,800);


        repaint();

    }


    public void paint(Graphics g){
        super.paint(g);
        g.setColor(Color.red);
        g.fillRect(10,10,10,10);
    }

    //this can be used to check if a button, check box or the like is used
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();

        //check to see what button was pressed and execute relevant actions
        if (action.equals("BSave")) {
           System.out.println("BSave");

           //cleanly read the file and get cooresponding values back
           ArrayList<String> aFile = fRead.cleanRead();

            /**
             * Loop through the aNames and values and find the cooresponding values in the textfile
             * then override the values as long as the exisiting value is of the same type ie boolean can't be a number
             */
            for( int i = 0;i< optionValue.size();i++){

                System.out.println(fRead.overWriteLine(optionValue.get(i).getText(),
                        OptionSetting.get(i).getText()));

                //update the cooresponding text box
                OptionSetting.get(i).setText(fRead.findValue(optionValue.get(i).getText()));
            }


        }
    }

}
