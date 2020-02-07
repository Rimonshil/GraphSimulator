
import java.awt.Color;
import java.util.ArrayList;


public class Node {
    
    Color color ;
    Color defaultColor;
    Color nameColor;
    public int centerX=-60,centerY=-60,radius=50;
    public String name="A";
    public int level=-1;
    public ArrayList<Node> adjacent;
    public double  dis;
    public Node() {
        
        adjacent=new ArrayList<Node> ();
        color = new Color(0,255,255);
        defaultColor=new Color(0,255,255);
        nameColor=Color.BLUE;
        
        
    }
    
    

    
    
    
 }
