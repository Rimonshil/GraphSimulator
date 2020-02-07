
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;



// CANVAS FOR DRAWING GRAPH
class MyCanvas extends JPanel {
    Node root;
    MyThread thread;
    int t = 0;
    MyCanvas canvas;
    public MyWindow gui;
    public char nodeCount = 'A';
    public int flag;
    Stroke st;
    Graphics2D g2d;
    FontMetrics fm;
    public int choice;
    public ArrayList<Node> nodes;
    ArrayList<Line> lines;
    public Node currentNode;
    Font font;
    public Line currentLine;
    public Node ref;//determinig root node 
    public Node childColorChange;
    public Node select;//for poSitioning
    public Node temp;
    public int element[];
    public int elementCount = 0;
    boolean cf=false;
    public ArrayList<Line> eP, sP;
    boolean drawName=true;
    public Color rootColor=new Color(135,85,200);
    public Font f2;
    public MyCanvas() {
        
        
        super.setPreferredSize(new Dimension(1160, 636));
        super.setBackground(Color.WHITE);
        nodes = new ArrayList<Node>();
        lines = new ArrayList<Line>();
        MyMouse myMouse = new MyMouse();

        super.addMouseListener(myMouse);
        super.addMouseMotionListener(myMouse);

        font = new Font(Font.SANS_SERIF, Font.PLAIN, 30);
        f2=new Font(Font.SANS_SERIF, Font.BOLD, 16);
        element = new int[300];

    }

    public void setThread(MyThread thread) {
        this.thread = thread;
    }

    
    public void setCanvas(MyCanvas canvas) {
        this.canvas = canvas;
    }

    public void setNode(Node a) { //SETTING CURRENT NODE FOR VARIOUS OPERATION LIKE MOVING NODE
        this.currentNode = a;
    }

    public void setL(Line l) { //SETTING CURRENT EDGE FOR VARIOUS OPERATION LIKE RESIZING
        this.currentLine = l;
    }

    public void setGui(MyWindow gui) {
        this.gui = gui;               //SETTING REFERENCE OF MyWindow CLASS FOR VARIOUS OPERATION LIKE BALENCING 
    }

    @Override
    public void paintComponent(Graphics g) {   
        
        super.paintComponent(g);
        g2d = (Graphics2D) g;

        g2d.setFont(font);
        fm = g2d.getFontMetrics();
       // System.out.println("joy");

       
        g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));//FOR INCRESING WIDTH OF DRAWING
        for (Line x : lines) {
             g2d.setColor(x.color);
            g2d.drawLine(x.startX, x.startY, x.endX, x.endY);

        }
        g2d.setStroke(new BasicStroke(0, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (Node x : nodes) {
            g2d.setColor(x.color);

            g2d.fillOval(x.centerX, x.centerY, x.radius, x.radius);
            String s = String.valueOf(x.name);
            double textWidth = fm.getStringBounds(s, g2d).getWidth();
            g.setColor(x.nameColor);
          //  g2d.drawString(s, (int) (x.centerX + (x.radius / 2) - textWidth / 2), x.centerY + (x.radius / 2) + 10);

           //addition  
             if(drawName)
            g2d.drawString(s, (int) (x.centerX + (x.radius / 2) - textWidth / 2), x.centerY + (x.radius / 2) + 10);
          
             else{
              //g2d.drawString(s, (int) (x.centerX - (x.radius / 2) - textWidth / 2), x.centerY + (x.radius / 2) + 10);
            if(x.level==-1){
                
            
             ImageIcon ico = new ImageIcon(getClass().getClassLoader().getResource("images/inf.png"));
             ico.paintIcon(this, g2d,x.centerX, x.centerY+3);
             
                g2d.setFont(f2);
                fm = g2d.getFontMetrics();
                double textWid = fm.getStringBounds(s, g2d).getWidth();
                g2d.drawString(s, (int) (x.centerX + (x.radius / 2) - textWid / 2), x.centerY + (x.radius / 2)-7 );
                g2d.setFont(font);
                fm = g2d.getFontMetrics();

            }
            
            else
            {
               
                g2d.drawString(s, (int) (x.centerX + (x.radius / 2) - textWidth / 2), x.centerY + (x.radius / 2) + 10);
                
                g2d.setFont(f2);
                fm = g2d.getFontMetrics();
                double textWid = fm.getStringBounds(s, g2d).getWidth();
                g2d.drawString(String.valueOf(x.level), (int) (x.centerX + (x.radius / 2) - textWid/ 2+15), x.centerY + (x.radius / 2) + 14);
                g2d.setFont(font);
                fm = g2d.getFontMetrics();
                
            }
            
            
             }
            

        }

    }

    

    public class MyMouse implements MouseMotionListener, MouseListener {

        @Override
        public void mouseDragged(MouseEvent me) {
            try {

                if (flag == 1) {    //MOVING CURRENT NODE
                    int mx = me.getX();
                    int my = me.getY();
                    if (Math.sqrt(((currentNode.centerX + currentNode.radius / 2) - mx) * ((currentNode.centerX + currentNode.radius / 2) - mx) + ((currentNode.centerY + currentNode.radius / 2) - my) * ((currentNode.centerY + currentNode.radius / 2) - my)) <= currentNode.radius) {
                        currentNode.centerX = me.getX() - currentNode.radius / 2;
                        currentNode.centerY = me.getY() - currentNode.radius / 2;
                        repaint();
                    }

                } else if (flag == 2) {     //RESIZING EDGE
                    currentLine.endX = me.getX();
                    currentLine.endY = me.getY();

                    for (Node x : nodes) //determining children for that ref node
                    {

                        if (Math.sqrt(((x.centerX + x.radius / 2) - currentLine.endX) * ((x.centerX + x.radius / 2) - currentLine.endX) + ((x.centerY + x.radius / 2) - currentLine.endY) * ((x.centerY + x.radius / 2) - currentLine.endY)) <= x.radius && x != ref) //System.out.println("xxx");
                        {

                            childColorChange = x;
                            x.color = Color.YELLOW;

                        } else if (x != ref) {
                            x.color = new Color(0, 255, 255);
                        }
                        repaint();

                    }
                } else if (flag == 3) {

                    int mx = me.getX();
                    int my = me.getY();       // Moving node WITH EDGE //REPOSITIONING
                    if (Math.sqrt(((currentNode.centerX + currentNode.radius / 2) - mx) * ((currentNode.centerX + currentNode.radius / 2) - mx) + ((currentNode.centerY + currentNode.radius / 2) - my) * ((currentNode.centerY + currentNode.radius / 2) - my)) <= currentNode.radius) {
                        currentNode.centerX = me.getX() - currentNode.radius / 2;
                        currentNode.centerY = me.getY() - currentNode.radius / 2;
                        repaint();
                    }

                    try {

                        moveLineWithNode();

                    } catch (Exception e) {
                    }

                }

            } catch (Exception e) {

                System.out.println("Drag exception");
            }

        }

        @Override
        public void mouseMoved(MouseEvent me) {
            int mx = me.getX();
            int my = me.getY();
            if (flag == 3) {//REPOSITIONING

                for (Node x : nodes) {
                    if (Math.sqrt(((x.centerX + x.radius / 2) - mx) * ((x.centerX + x.radius / 2) - mx) + ((x.centerY + x.radius / 2) - my) * ((x.centerY + x.radius / 2) - my)) <= x.radius) 

                        {
                            select = x;
                            setNode(x);                   //SETTING CURRENT NODE
                            x.color=Color.green;
                            repaint(0);
                            sP = new ArrayList<Line>();           //the lineS connected with moving node 
                            eP = new ArrayList<Line>();

                            for (Line y : lines) {
                                //determining Starting and end point of line That connected with moving node 
                                if (Math.sqrt(((currentNode.centerX + currentNode.radius / 2) - y.endX) * ((currentNode.centerX + currentNode.radius / 2) - y.endX) + ((currentNode.centerY + currentNode.radius / 2) - y.endY) * ((currentNode.centerY + currentNode.radius / 2) - y.endY)) <= currentNode.radius) {

                                    eP.add(y);

                                } else if (Math.sqrt(((currentNode.centerX + currentNode.radius / 2) - y.startX) * ((currentNode.centerX + currentNode.radius / 2) - y.startX) + ((currentNode.centerY + currentNode.radius / 2) - y.startY) * ((currentNode.centerY + currentNode.radius / 2) - y.startY)) <= currentNode.radius) {

                                    sP.add(y);

                                }

                            }

                        }
                    
                    else
                    {
                        x.color=new Color(0,255,255);
                            repaint(0);
                        //  System.out.println(mx+" "+my);

                    }

                }
            }
            if(flag==5)//color changing
            {
                
                
                for(Line x:lines)
                {
                    
                  if(  distance(x.startX, x.startY, mx, my) + distance(mx, my, x.endX, x.endY) >= distance(x.startX, x.startY, x.endX, x.endY) - 1 && distance(x.startX, x.startY, mx, my) + distance(mx, my, x.endX, x.endY) <= distance(x.startX, x.startY, x.endX, x.endY) + 1)
                  {
                      x.color=Color.RED;
                      
                      repaint();
                  }
                  
                  else{
                      x.color=Color.BLACK;
                      repaint();   
                  }
                  
                    
                    
                }
                
                
            }
            
            if(flag==4)//CHANGING  COLOR TO RED FOR DELETING EDGE 
            {
                
                 for (Node x : nodes) {
                    if (Math.sqrt((x.centerX  - mx) * (x.centerX  - mx) + (x.centerY - my) * (x.centerY - my)) <= x.radius) {

                       x.color=Color.RED;
                       x.nameColor=Color.WHITE;
                       repaint();
                
                           }
                    else{
                        x.color= new Color(0,255,255);
                        x.nameColor=Color.BLUE;
                        repaint();
                        
                    }
                        
            
            
                    }
            }
            
            if(flag==6 )
            {
                
                
                for (Node x : nodes) {
                    if (Math.sqrt((x.centerX  - mx) * (x.centerX  - mx) + (x.centerY - my) * (x.centerY - my)) <= x.radius) {
                        if (!cf) {
                       x.color=Color.BLUE;
                       x.nameColor=Color.WHITE;
                       repaint();
                        }
                           }
                    else{
                        if(!cf){
                        x.color= new Color(0,255,255);
                        x.nameColor=Color.BLUE;
                        repaint();
                        }
                        
                    }
                        
            
            
                    }
            }
                
        }

        @Override
        public void mouseClicked(MouseEvent me) {
                  //PRINTING ADJACENCY
            if (SwingUtilities.isRightMouseButton(me)) {
                for (Node x : nodes) {
                    System.out.println("\nNeighbour of  " + x.name + ":  ");
                    for (Node c : x.adjacent) {
                        System.out.print(c.name + " ");
                    }

                }

                System.out.println("\n");

                System.out.println("<------->");

            }
            if (flag == 1 && SwingUtilities.isLeftMouseButton(me))//creating new Node
            {
                Node newNode = new Node();
                newNode.centerX = me.getX() - newNode.radius / 2;
                newNode.centerY = me.getY() - newNode.radius / 2;
                newNode.name = String.valueOf(nodeCount);
                nodeCount++;
                setNode(newNode);
                nodes.add(newNode);
                canvas.element[canvas.elementCount++] = 1;
                repaint();
                //  System.out.println("zz");

            }

            if (flag == 4) {
                //DELETING NODE
                int mx, my;
                if (me.getClickCount() == 2) {
                    mx = me.getX();
                    my = me.getY();
                    deleteNode(mx, my);

                }
            }
            
            if (flag == 6 ) {
                //DELETING NODE
                int mx, my;
                if (me.getClickCount() == 2) {
                    mx = me.getX();
                    my = me.getY();
                    setRootNode(mx, my);

                }
            }
            
            if (flag == 5) {
                //DELETING EDGE
                int mx, my;
                if (me.getClickCount() == 2) {
                    mx = me.getX();
                    my = me.getY();
                    deleteEdge(mx, my);

                }
            }
            
            
           
            
        }

        @Override
        public void mousePressed(MouseEvent me) {
            if (flag == 2) {
                currentLine.startX = me.getX();
                currentLine.startY = me.getY();
                currentLine.endX = me.getX();
                currentLine.endY = me.getY();
                repaint();
                creatingLineAndDeterminingAdjacency();
            }
        }

        @Override
        public void mouseReleased(MouseEvent me) {
            int mx=me.getX();
            int my=me.getY();
            
            positioningAndAddingAdjacent();
            if (flag == 2) {
                createNewLine();

            }
            if (flag == 3) {
                select = null;         //  after moving a node,,,, determining next node to move

            }
            
           
            
            
            

        }

        @Override
        public void mouseEntered(MouseEvent me) {
        }

        @Override
        public void mouseExited(MouseEvent me) {
        }

        void positioningAndAddingAdjacent() {
            int rmv = 0;
            if (flag == 2) {
                try {
                    for (Node x : nodes) //determining children for that ref node
                    {

                        if (Math.sqrt(((x.centerX + x.radius / 2) - currentLine.endX) * ((x.centerX + x.radius / 2) - currentLine.endX) + ((x.centerY + x.radius / 2) - currentLine.endY) * ((x.centerY + x.radius / 2) - currentLine.endY)) <= x.radius && x != ref) //System.out.println("xxx");
                        {
                            ref.adjacent.add(x);
                            x.adjacent.add(ref);

                            rmv = 1;
                        }

                    }
                    
                    if (rmv == 0 || nodes.size()<2) {
                        canvas.lines.remove(currentLine);
                        System.out.println("rmv");
                        for (Node x : nodes) //Setting Default color of Selected node
                    {

                        if (Math.sqrt(((x.centerX + x.radius / 2) - currentLine.startX) * ((x.centerX + x.radius / 2) - currentLine.startX) + ((x.centerY + x.radius / 2) - currentLine.startY) * ((x.centerY + x.radius / 2) - currentLine.startY)) <= x.radius ) //System.out.println("xxx");
                        {
                            x.color=x.defaultColor;
                            repaint();
                        }

                    }
                        
                    }

                    childColorChange.color = new Color(0, 255, 255);
                    ref.color = new Color(0, 255, 255);
                    currentLine.startX = ref.centerX + ref.radius / 2;         ///Line poSitioning at center 
                    currentLine.startY = ref.centerY + ref.radius / 2;
                    currentLine.endX = childColorChange.centerX + childColorChange.radius / 2;
                    currentLine.endY = childColorChange.centerY + childColorChange.radius / 2;
                    repaint();

                } catch (Exception e) {
                }

            }

        }

        private void creatingLineAndDeterminingAdjacency() {
            int t = 0;
            for (Node x : nodes) //determining root for this edge
            {

                if (Math.sqrt(((x.centerX + x.radius / 2) - currentLine.startX) * ((x.centerX + x.radius / 2) - currentLine.startX) + ((x.centerY + x.radius / 2) - currentLine.startY) * ((x.centerY + x.radius / 2) - currentLine.startY)) <= x.radius) //System.out.println("xxx");
                {
                    t = 1;
                }
            }

            if (t == 0) {
                canvas.lines.remove(currentLine);

            }
            repaint();
            for (Node x : nodes) //determining root for this edge
            {

                if (Math.sqrt(((x.centerX + x.radius / 2) - currentLine.startX) * ((x.centerX + x.radius / 2) - currentLine.startX) + ((x.centerY + x.radius / 2) - currentLine.startY) * ((x.centerY + x.radius / 2) - currentLine.startY)) <= x.radius) //System.out.println("xxx");
                {

                    ref = x;
                    x.color = Color.YELLOW;

                }

            }

            repaint();
        }

        private void moveLineWithNode() {

            for (Line S : sP) {

                S.startX = select.centerX + select.radius / 2;     //moving lineS with node
                S.startY = select.centerY + select.radius / 2;
                repaint();

            }
            for (Line E : eP) {

                E.endX = select.centerX + select.radius / 2;
                E.endY = select.centerY + select.radius / 2;
                repaint();

            }
        }

        private void createNewLine() {

            Line newLine = new Line();         //creating new line
            setL(newLine);
            canvas.lines.add(newLine);
            canvas.element[canvas.elementCount++] = 2;
        }

        private void deleteNode(int mx, int my) {
            sP = new ArrayList<Line>();           //the lineS connected with moving node 
            eP = new ArrayList<Line>();

            Node t = null;
            for (Node x : nodes) {
                if (Math.sqrt((x.centerX  - mx) * (x.centerX - mx) + (x.centerY - my) * (x.centerY - my)) <= x.radius) {
                    

                    for (Line y : lines) {
                        
                        //determining Starting and end point of line That connected with  node 
                        if (Math.sqrt(((x.centerX + x.radius / 2) - y.endX) * ((x.centerX + x.radius / 2) - y.endX) + ((x.centerY + x.radius / 2) - y.endY) * ((x.centerY + x.radius / 2) - y.endY)) <= x.radius) {
                            try {
                                eP.add(y);

                            } catch (Exception e) {
                                System.out.println("Ep");
                            }

                        } else if (Math.sqrt(((x.centerX + x.radius / 2) - y.startX) * ((x.centerX + x.radius / 2) - y.startX) + ((x.centerY + x.radius / 2) - y.startY) * ((x.centerY + x.radius / 2) - y.startY)) <= x.radius) {
                            try {
                                sP.add(y);
                            } catch (Exception e) {
                                System.out.println("Sp");
                            }

                        }

                    }

                    t = x;
                    break;

                }

            }

            canvas.nodes.remove(t);
            repaint();
            try {
                for (Line x : sP) {
                    if (x != null && !canvas.lines.isEmpty()) {
                        canvas.lines.remove(x);
                    }
                    repaint();

                }
            } catch (Exception e) {

                System.out.println("StartP line exception");
            }

            try {
                for (Line x : eP) {
                    if (x != null && !canvas.lines.isEmpty()) {
                        canvas.lines.remove(x);
                    }
                    repaint();

                }

            } catch (Exception e) {

                System.out.println("EndP line Ex");
            }

            repaint();

            //disconnecting deleted node   
            for (Node x : nodes) {

                try {
                    for (Node y : x.adjacent) {
                        if (y == t) {

                            x.adjacent.remove(t);

                        }

                    }
                } catch (Exception e) {

                }

            }
            
            try {
                root=null;
            } catch (Exception e) {
            }

        }

        private void deleteEdge(int mx, int my) {
            
            Line t = null;
            for (Line x : lines) {

                if (distance(x.startX, x.startY, mx, my) + distance(mx, my, x.endX, x.endY) >= distance(x.startX, x.startY, x.endX, x.endY) - 1 && distance(x.startX, x.startY, mx, my) + distance(mx, my, x.endX, x.endY) <= distance(x.startX, x.startY, x.endX, x.endY) + 1) {
                    t = x;

                }

            }
            //Removing adjacency
            Node Start = null,end = null;
            
            for(Node x:nodes)
            {
              try{  
                 if (Math.sqrt(((x.centerX + x.radius / 2) - t.endX) * ((x.centerX + x.radius / 2) - t.endX) + ((x.centerY + x.radius / 2) - t.endY) * ((x.centerY + x.radius / 2) - t.endY)) <= x.radius ) //System.out.println("xxx");
                
                 {
                     
                     
                     end=x;
                     
                     
                     
                 }
              
              
                 
                 else  if (Math.sqrt(((x.centerX + x.radius / 2) - t.startX) * ((x.centerX + x.radius / 2) - t.startX) + ((x.centerY + x.radius / 2) - t.startY) * ((x.centerY + x.radius / 2) - t.startY)) <= x.radius ) {
                     
                     
                     Start=x;
                     
                 }
                
                
              }
              catch (Exception ex) {
                    
                }
                
                
            }
            try {
                Start.adjacent.remove(end);
            end.adjacent.remove(Start);
            } catch (Exception e) {
            }
            
            
            
            
            
            
            canvas.lines.remove(t);
            repaint();
            
            
            
            
            
            
            
            
            
            
            
            
            
        }
        
        public double distance(int x1,int y1,int x2,int y2)//DISTANCE BETWEEN TWO POINT
                
        {
                            
            return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
            
            
        }

        private void setRootNode(int mx, int my) {
            try {
                
                for (Node x : nodes) {
                if (Math.sqrt((x.centerX  - mx) * (x.centerX - mx) + (x.centerY - my) * (x.centerY - my)) <= x.radius) 
                    {
                       System.out.println(x.name+"kk"  );
                       root=x;
                      
                        System.out.println(root.name);
                        //x.color=new Color(0,243,200);
                        cf=true;
                       break;
                        
                     
                    }
            
            
            
        }
                
                
            } catch (Exception e) {
            }
 
             

    }

}
}