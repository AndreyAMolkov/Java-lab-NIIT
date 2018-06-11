import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.*;

import static javax.swing.text.StyleConstants.Bold;
import static javax.swing.text.StyleConstants.Italic;

public class AutomataGUIDemo extends JFrame{


    private JButton[] buttons;
    private JButton buttonEnterCash;
    private JButton buttonCancel;
    private JButton buttonPayment;
    private JButton[]  buttonsCash;
    private int[]banknotes;
    private JLabel[] labels;
    private int positionPrice;
    private int PositionDescribe;
    private String[][] menu;
    private GridBagLayout gbBuf;
    private GridBagLayout gbInfo;
    private GridBagConstraints gbcMain;
    private GridBagConstraints gbcBuf;
    private GridBagConstraints gbcInfoBlock;
    private JPanel panelInfoBlock;
    private JPanel panelBlockMenu;
    private JPanel panelCancel;
    private JPanel panelButtonPayment;
    private JPanel panelPayment;
    private JPanel panelMain;
    private Automata Automata;
    private JFrame frame ;
    private String separate;
    private JTextArea textArea;
    private JTextArea textCash;

    public AutomataGUIDemo(Automata Automata) {
        this.separate=",";
        this.positionPrice = 1;
        this.PositionDescribe = 0;
        this.menu = Automata.getMenu();
        this.Automata=Automata;
        //create the frame and the panel to it
        this.textArea=new JTextArea(2,20);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        this.textCash=new JTextArea(1,20);
        textCash.setEditable(false);
        this.frame= new JFrame("Coffee");
        frame.setComponentOrientation(ComponentOrientation.UNKNOWN);
        setButton();


    }
private void createMainPanel(){
    panelMain = new JPanel(new GridBagLayout());
    gbcMain = new GridBagConstraints();
    gbcMain.gridwidth = GridBagConstraints.NONE;

    frame.setContentPane(panelMain);
    frame.setSize(600,600);

}
    //create controls in memory
    private void setButton() {
        int maxSize = menu.length;
        buttons = new JButton[maxSize];
        labels = new JLabel[maxSize];
        for (int i = 0; i < maxSize; i++) {
            buttons[i] = new JButton(menu[i][positionPrice]);
            labels[i] = new JLabel(menu[i][PositionDescribe].toString());
            buttons[i].setActionCommand("product"+separate +labels[i].getText()+separate+i);
            buttons[i].addActionListener(new ListenerButton(Automata,this));
        }
// set service
        this.buttonCancel=new JButton("Cancel");
        buttonCancel.setActionCommand("service"+separate +buttonCancel.getText()+separate+"Cancel");
        buttonCancel.addActionListener(new ListenerButton(Automata,this));

        this.buttonEnterCash=new JButton("Enter");
        buttonEnterCash.setActionCommand("service"+separate +buttonEnterCash.getText()+separate+"Enter");
        buttonEnterCash.addActionListener(new ListenerButton(Automata,this));
        buttonEnterCash.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                workPanel();
            }
        });
        this.buttonPayment=new JButton("Payment");

        buttonPayment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                paymentPanel();
            }
        });
        // set Cash banknotes
        this.banknotes= new int[]{1,2,5,10,50,100,200,250};
        this.buttonsCash=new JButton[banknotes.length];
        for(int i=0;i<banknotes.length;i++) {
            buttonsCash[i] = new JButton(banknotes[i]+" "+"RUB");
            buttonsCash[i].setActionCommand("cash"+separate +buttonsCash[i].getText()+separate+banknotes[i]);
            buttonsCash[i].addActionListener(new ListenerButton(Automata,this));
// set default TEXT AREA

        }

    }

  public  JButton[] getButtons() {
        return buttons;
    }

    JLabel[] getLabels() {
        return labels;
    }

   private void createPanelInfoBlock() {
//create a panel
       panelInfoBlock = new JPanel();
       panelInfoBlock.setLayout(new BoxLayout(panelInfoBlock,BoxLayout.Y_AXIS));
       panelInfoBlock.add(textArea);
       panelInfoBlock.add(textCash);

       panelMain.add(panelInfoBlock, gbcMain);

    }
   public void setTextArea (HashMap<String, String> data){

       this.textArea.setText(data.get("text"));
       this.textCash.setText(data.get("cash"));


       textArea.update(textArea.getGraphics());
       textCash.update(textCash.getGraphics());
       if(!textArea.getText().equals(" ")) {
           try {
               Thread.sleep(1000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }

   private void createPanelBlockMenu() {
//create a panel
        panelBlockMenu = new JPanel();
        createGBC(panelBlockMenu);
        int row = 4;
        for (int i = 0; i < buttons.length; i++) {

            gbcBuf.fill =GridBagConstraints.BOTH;
           addComponent(labels[i], row, 1, 1, 1,panelBlockMenu,3);
                  // gbcBuf.fill =GridBagConstraints.BOTH;

            addComponent(buttons[i], row++, 2, 1, 1,panelBlockMenu,3);
           // gbcBuf.fill =GridBagConstraints.BOTH;

        }

       panelMain.add(panelBlockMenu, gbcMain);

    }
    private void createPanelPayment(){
        panelPayment=new JPanel();
        createGBC(panelPayment);
        gbcBuf.fill =GridBagConstraints.BOTH;
        addComponent(buttonsCash[0],0,0,1,2,panelPayment,3);
        addComponent(buttonsCash[1],1,0,1,2,panelPayment,3);
        addComponent(buttonsCash[2],2,0,1,2,panelPayment,3);
        addComponent(buttonsCash[3],3,0,1,2,panelPayment,3);
        addComponent(buttonsCash[4],0,4,1,2,panelPayment,3);
        addComponent(buttonsCash[5],1,4,1,2,panelPayment,3);
        addComponent(buttonsCash[6],2,4,1,2,panelPayment,3);
        addComponent(buttonsCash[7],3,4,1,2,panelPayment,3);
        addComponent(buttonEnterCash,0,8,19,1,panelPayment,3);

        panelMain.add(panelPayment,gbcMain);
    }

private void createCancel(){

    panelCancel = new JPanel();
    createGBC(panelCancel);
    addComponent(buttonCancel,1,0,1,8,panelCancel,3);
    panelMain.add(panelCancel, gbcMain);
}
   private void createButtonForPayment(){

        panelButtonPayment = new JPanel();
       createGBC(panelButtonPayment);
       addComponent(buttonPayment,buttons.length,0,0,8,panelButtonPayment,3);

        panelMain.add(panelButtonPayment, gbcMain);
    }

   private void finalOfCreate(){
//set the size and make the window visible

        frame.setVisible(true);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

    }
    private GridBagConstraints createGBC(JPanel panel){
        gbBuf=new GridBagLayout();
        panel.setLayout(gbBuf);
        gbcBuf=new GridBagConstraints();
        return  gbcBuf;
    }
    private void addComponent (Component c,int gridy, int gridx, int gridheight, int gridwidth,JPanel panel,int insets){
        //Set a layout manager for this panel

        gbcBuf.insets = new Insets(insets, insets, insets, insets);
        gbcBuf.gridx = gridx;
        gbcBuf.gridy = gridy;
        gbcBuf.gridwidth = gridwidth;
        gbcBuf.gridheight = gridheight;
        gbBuf.setConstraints(c, gbcBuf);
        panel.add(c);
       // return gbc;
    }
    void workPanel(){
        frame.setVisible(false);
        createMainPanel();
        createPanelInfoBlock();
        createPanelBlockMenu();
        createCancel();
        createButtonForPayment();
        finalOfCreate();

    }
    void paymentPanel(){

        frame.setVisible(false);
        createMainPanel();
        createPanelInfoBlock();
        createPanelPayment();
        createCancel();
        finalOfCreate();
        this.textArea.setText("");

    }
 }