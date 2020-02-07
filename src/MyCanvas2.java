
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

//EnCoded By Joy
public class MyCanvas2 extends JPanel{
    Graphics2D g2d;
    public int flag=1;
    MyWindow window;
    MyCanvas canvas;
      FontMetrics fm;
      
      Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 28);

    LinkedList<TempNode> arrayList = new LinkedList<TempNode>();
    
    public MyCanvas2() {
        
        
        super.setPreferredSize(new Dimension(100, 636));
        super.setBackground(new Color(240,240,240));
      
      super.setOpaque(true);
       
    
        
        
    }

    public void setWindow(MyWindow window) {
        this.window = window;
    }

    public void setCanvas(MyCanvas canvas) {
        this.canvas = canvas;
    }
    
    
    
    public void paintComponent(Graphics g)
    {
        g2d=(Graphics2D) g;
        g2d.setFont(font);
        g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        fm = g2d.getFontMetrics();
       
        
           g2d.setColor(new Color(148,0,211));
           g2d.drawRect(0, 0, this.getWidth(),this.getHeight());
            
        if(flag==1)
        {
        g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(Color.BLACK);
       
//            System.out.println("jj");
//            System.out.println(this.getX()+"  "+this.getY());
            
            g2d.drawRect(10, 10, this.getWidth()-20,this.getHeight()-20);
            
            
         for(TempNode x:arrayList)
         {
             
             System.out.println(x.name);
             g2d.setColor(x.defaultColor);
             g2d.fillOval(x.centerX, x.centerY, x.radius, x.radius);
             String s = String.valueOf(x.name);
            double textWidth = fm.getStringBounds(s, g2d).getWidth();
            g.setColor(x.nameColor);
            g2d.drawString(s, (int) (x.centerX + (x.radius / 2) - textWidth / 2), x.centerY + (x.radius / 2+8));
             
             
         }
            
            
            
            
        }
            
            
        
        
        
        
     
    }
}
    
    
    
        
    
        
    
    
    
    
    

      


