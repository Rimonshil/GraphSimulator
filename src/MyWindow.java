
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



public class MyWindow extends JFrame{

    DfSThread dfSThread = new DfSThread();
    JSlider Source;
    MyThread thread=new MyThread();
    ArrayList<JButton> buttonS ;
    JPanel jPanel1 ;
    public JLabel label;
    ArrayList<JButton> btnS;
   public  Node root;
     Box box1;
    JSlider jSlider;
     JButton nodeBtn,edgeBtn,rePosition,delNode,delEdge;
    Color color1,color2;
    MyCanvas canvas;
    MyCanvas2 myCanvas2;
    MyWindow myGui;
    boolean f;
    int count=1;
   static  MyWindow myWindow;
    public MyWindow()
    {
        color1=new Color(172,221,126);
        color2=new Color(66,133,244);
        super.setTitle("Graph Simulator");
        super.setPreferredSize(new Dimension(1260, 700));
        super.setResizable(true);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
       //ADD NODE BUTTON
        ImageIcon addNode = new ImageIcon(getClass().getClassLoader().getResource("images/addNode.png"));
       nodeBtn=new JButton(addNode);
       nodeBtn.setFocusPainted(false);
       nodeBtn.setPreferredSize(new Dimension(150,44));
       nodeBtn.setBackground(color1);
       nodeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                 
                canvas.flag=1;
               reSet();
                ButtonColorChange(nodeBtn);
                
            }
        });
        
        //ADD EDGE BUTTON
       
        ImageIcon addEdge = new ImageIcon(getClass().getClassLoader().getResource("images/line_1.png"));
        edgeBtn=new JButton(addEdge);
        edgeBtn.setFocusPainted(false);

        edgeBtn.setPreferredSize(new Dimension(150,44));
        edgeBtn.setBackground(color1);
        edgeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                 reSet();
               
                if (canvas.nodes.size()<2) {
                     
                   
                    JOptionPane.showMessageDialog(myWindow, "Please Enter Atleast Two Nodes.", "Error !!!", JOptionPane.ERROR_MESSAGE);
                    
                }
                
                else{
               
                    ButtonColorChange(edgeBtn);

                canvas.flag=2;
                Line line = new Line();
                canvas.setL(line);
                canvas.lines.add(line);
                canvas.element[canvas.elementCount++]=2;
                
                
                }
         
            }
        });
        
        
        
        
        //RE-ARRANGE BUTTON
        ImageIcon reArrange = new ImageIcon(getClass().getClassLoader().getResource("images/move.png"));
       rePosition=new JButton(reArrange);
        rePosition.setFocusPainted(false);

       rePosition.setPreferredSize(new Dimension(150,44));
       rePosition.setBackground(color1);
       rePosition.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(canvas.nodes.isEmpty())
                {
                    System.out.println("mm");
                    JOptionPane.showMessageDialog(myWindow, "Graph Empty!.", "Error !!!", JOptionPane.ERROR_MESSAGE);
                    return ;

                }
                 reSet();
                ButtonColorChange(rePosition);

                canvas.flag = 3;
            }
        });
       
       
       
       
       //DELETE NODE BUTTON
         delNode = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/Delete.png")));
         delNode.setPreferredSize(new Dimension(150,44));
         delNode.setBackground(color1);
         delNode.setFocusPainted(false);

         delNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(canvas.nodes.isEmpty())
                {
                    
                    JOptionPane.showMessageDialog(myWindow, "Node Doesn't Exist.", "Error !!!", JOptionPane.ERROR_MESSAGE);
                    return ;
                }
                 reSet();
                ButtonColorChange(delNode);

                repaint();
                canvas.flag=4;
            }
        });
         
         
         
         
         
         //DELETE EDGE BUTTON
       
       delEdge = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/deleteEdge.png")));
      delEdge.setPreferredSize(new Dimension(150,44));
               delEdge.setFocusPainted(false);


       delEdge.setBackground(color1);
       delEdge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
              
                if(canvas.lines.isEmpty())
                {
                    JOptionPane.showMessageDialog(myWindow, "No Edge Found.", "Error !!!", JOptionPane.ERROR_MESSAGE);
                    return ;
                }

                reSet();
                ButtonColorChange(delEdge);

                repaint();
                canvas.flag=5;
            }
        });
       
       //Set Root
       JButton setRoot = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/SelectRoot.png")));
        setRoot.setFocusPainted(false);

       setRoot.setBackground(color1);
        setRoot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                reSet();
                 if(canvas.nodes.isEmpty())
               {
                   JOptionPane.showMessageDialog(myWindow, "Add AtleaSt one Node.", "Error !!!", JOptionPane.ERROR_MESSAGE);
                    return ;
               }
                 ButtonColorChange(setRoot);

               canvas.cf=false;
               canvas.flag=6;
            
               
                
                
            }
        });
       
       
       
       //BFS BUTTON
       
        JButton jButton =  new JButton("Run BFS",new ImageIcon(getClass().getClassLoader().getResource("images/bfS.png")));
       
        jButton.setBackground(color1);
                jButton.setFocusPainted(false);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                 reSet();
                 canvas.flag=7;
                 canvas.drawName=false;
                 if(canvas.root==null)
               {
                   JOptionPane.showMessageDialog(myWindow, "Select Root First.", "Error !!!", JOptionPane.ERROR_MESSAGE);
                    return ;
               }
                 ButtonColorChange(jButton);

                 thread.setMyCanvas2(myCanvas2);
                 thread.setCanvas(canvas);
                 thread.setBox(box1);
                 thread.setWin(myWindow);
                 canvas.setThread(thread);
                 thread.root=root;
                 thread.returnFrom=false;
                 thread.start();
                
                 
                
                
                
                
                
            }
        });
        JButton dfS = new JButton("Run DFS");
        dfS.setFocusPainted(false);

        Font font = new Font(Font.SERIF, Font.ITALIC, 13);
        dfS.setBackground(color1);
        dfS.setFont(font);
        dfS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
              
                canvas.flag=8;
                reSet();
                 if(canvas.root==null)
               {
                   JOptionPane.showMessageDialog(myWindow, "Select Root First.", "Error !!!", JOptionPane.ERROR_MESSAGE);
                    return ;
               }
                 ButtonColorChange(dfS);

                dfSThread.setCanvas(canvas);
                dfSThread.start();
            
               
                
                
            }
        });
        
        JButton pauSe = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/pauSe.png")));
        pauSe.setFocusPainted(false);

        pauSe.setBackground(color1);
        pauSe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                   
                   
                    thread.iSpauSe=true; 
                    ButtonColorChange(pauSe);

                   // System.out.println("ll");

                } catch (Exception e) {
                }
            }
        });
        JButton reSume = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/reSume.png")));
        reSume.setBackground(color1);
        reSume.setFocusPainted(false);

        reSume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //System.out.println("ll");
                try {
                   
                        thread.iSpauSe=false;
                        ButtonColorChange(reSume);


                                       
                } catch (Exception e) {
                }
            }
        });
        
        JButton Save = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/Save.png")));
                Save.setFocusPainted(false);

        Save.setBackground(color1);
        Save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
               // savefile();
                ButtonColorChange(Save);

                makePanelImage(canvas);
                
            }
        });
        
        
        JButton refrS = new JButton(new ImageIcon(getClass().getClassLoader().getResource("images/refrSh.png")));
        refrS.setFocusPainted(false);

        refrS.setBackground(color1);
        refrS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // savefile();
                ButtonColorChange(refrS);
                canvas.nodeCount='A';
                clear();
                

                

            }
        });
        
       
         jSlider = new JSlider(JSlider.VERTICAL, 0, 5, 0);
        jSlider.setMajorTickSpacing(1);
       

        jSlider.setMinorTickSpacing(0);
        jSlider.setBorder(new EmptyBorder(4,4,4,10));
        jSlider.setBackground(color1);
        jSlider.setValue(2);
        jSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                
                 Source=(JSlider)ce.getSource();
                thread.delay=Source.getValue();
                
            }
        });
        
        buttonS = new ArrayList<JButton>();
        buttonS.add(nodeBtn);
        buttonS.add(edgeBtn);
        buttonS.add(rePosition);
        buttonS.add(delEdge);
        buttonS.add(delNode);
        buttonS.add(pauSe);
        buttonS.add(reSume);
        buttonS.add(dfS);
        buttonS.add(jButton);
        buttonS.add(setRoot);
        buttonS.add(Save);
        buttonS.add(refrS);
        
        
        
        
        
        
        
       //ADDING THE BUTTONS IN A PANEL AND LAYING -OUT
        JPanel jPanel = new JPanel();
        jPanel.setBackground(new Color(255,228,196));
        jPanel.setPreferredSize(new Dimension(1260,64));
        jPanel.setLayout(new GridLayout(1,0,5,4));
        jPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        jPanel.add(nodeBtn);
        jPanel.add(edgeBtn);
        jPanel.add(rePosition);
        jPanel.add(delNode);
        jPanel.add(delEdge);
        jPanel.add(setRoot);
        jPanel.add(jButton);
        jPanel.add(dfS);
        jPanel.add(pauSe);
        jPanel.add(reSume);
        jPanel.add(Save);
        jPanel.add(refrS);
        jPanel.add(jSlider);
        
        
        
        jPanel.setBorder(new EmptyBorder(4,4,4,4));
        Box box = Box.createVerticalBox();
        
        
       
       
      canvas=new MyCanvas();
      LineBorder border = new LineBorder(new Color(148,0,211), 2);
      canvas.setBorder(border);
      canvas.setLayout(new BorderLayout());
      //canvas.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
      label = new JLabel();
      label.setBorder(new EmptyBorder(4,4,4,10));
      label.setText("");
      label.setFont(canvas.font);
     
        jPanel1 = new JPanel();
        jPanel1.setPreferredSize(new Dimension(1260,64));
        jPanel1.setBackground(new Color(255,228,196));
        jPanel1.setLayout(new FlowLayout(FlowLayout.CENTER));
        border = new LineBorder(new Color(148,0,211), 1);
        jPanel1.setBorder(border);
       
        

        jPanel1.add(label);
        
        
        
        
        canvas.add(jPanel1,BorderLayout.SOUTH);
        
        
        
        
        
        

        
     // canvas.add(label,BorderLayout.SOUTH);
      
      
      box.add(jPanel);
     box.add(Box.createVerticalStrut(2));
       myCanvas2 = new MyCanvas2();
      LineBorder border1 = new LineBorder(new Color(148,0,211), 2);
      myCanvas2.setBorder(border1);
      
      myCanvas2.setCanvas(canvas);
      myCanvas2.setWindow(myWindow);
         box1 =Box.createHorizontalBox();
        box1.add(Box.createHorizontalStrut(1));
        
        box1.add(canvas);
        box1.add(Box.createHorizontalStrut(2));
        box1.add(myCanvas2);
      
      box.add(box1);
      canvas.setGui(myWindow);
      canvas.setCanvas(canvas);
      //ADDING ALL COMPONENT TO FRAME AND PREPARING A COMPLETE WINDOW
      super.add(box);
      super.revalidate();
      super.repaint();
      super.pack();
      super.setVisible(true);
      super.setLocationRelativeTo(null);
        
    }
    
    
    
    
    
      
    public static void main(String[] args) {
        
        myWindow = new MyWindow();
    }

  
 
    public void reSet()
    {
        
        thread.returnFrom=true;
        thread=new MyThread();
        if(Source!=null)
            thread.delay=Source.getValue();
        dfSThread=new DfSThread();
        label.setText("");
        myCanvas2.arrayList.removeAll(myCanvas2.arrayList);
        for(Node x: canvas.nodes)
        {
            x.color=x.defaultColor;
            x.level=-1;
            x.nameColor=Color.BLUE;
        }
        
        for(Line x: canvas.lines)
        {
            x.color=Color.BLACK;
        }
        //canvas.root=null;
        canvas.drawName = true;
        canvas.repaint();
    }
    
    
    
    public void ButtonColorChange(JButton current)
    {
        
        for(JButton x: buttonS)
        {
            
            if(x==current)
            {
                x.setBackground(color2);
                repaint(0);
            }
            
            else
            {
                x.setBackground(color1);
                repaint(0);
            }
            
            
        }
        
        
        
    }
    
    public void savefile()
{
    BufferedImage image2 = new BufferedImage(canvas.getWidth(), canvas.getHeight(),     BufferedImage.TYPE_INT_RGB);
    JFileChooser jFile = new JFileChooser();
    jFile.showSaveDialog(null);
    Path pth = jFile.getSelectedFile().toPath();
    JOptionPane.showMessageDialog(null, pth.toString());
    Graphics2D graphics2D = image2.createGraphics();
    try {
        ImageIO.write(image2, "png", new File(pth.toString()));
    } catch (IOException ox) {
        // TODO: handle exception
        ox.printStackTrace();

}
}
    
    public  void makePanelImage(Component panel) {
        Dimension size = panel.getSize();
        BufferedImage image = new BufferedImage(
                size.width, size.height-64,
                 BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        panel.paint(g2);
        JFileChooser jFile = new JFileChooser();
        jFile.showSaveDialog(null);
        Path pth = null ;
        try {
                     pth = jFile.getSelectedFile().toPath();

        } catch (Exception e) {
        }
        if(pth!=null){
        JOptionPane.showMessageDialog(null,"Image Saved on "+  pth.toString());
        try {
            ImageIO.write(image, "png", new File(pth.toString()));
            System.out.println("Panel saved as Image.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    }

    
    public void clear()
    {
        canvas.lines.removeAll(canvas.lines);
        canvas.nodes.removeAll(canvas.nodes);
        
        
    }
    
    
}
