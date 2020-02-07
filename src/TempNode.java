
import java.awt.Color;

//EnCoded By Joy
public class TempNode {
   public  String name;
    public int centerX,centerY,radius=45;
   public  Color defaultColor=new Color(0,255,255);
    public Color nameColor=Color.BLUE;

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public TempNode(String name, int centerX, int centerY) {
        this.name = name;
        this.centerX = centerX;
        this.centerY = centerY;
    }
    
    

    

      

}
