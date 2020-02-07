//EnCoded By Joy

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import javax.swing.SwingUtilities;

public class DfSThread extends Thread{

//EnCoded By Joy
    MyCanvas canvas;
    Node x;
    

    public void setCanvas(MyCanvas canvas) {
        this.canvas = canvas;
    }
   boolean f;
    @Override
    public void run() {
        if(!f){
        deapthFirstSearchRecursive(canvas.root);
        f=true;
        }

    }
    
     private  void deapthFirstSearchRecursive(Node root) {
        
        Stack<Node> stack = new Stack<Node>();
        stack.push(root);
         root.color=new Color(173, 255, 47);
         
         try {
                        Thread.sleep(1500);
                    } catch (Exception e) {
                    }
                             canvas.repaint();
        
    
        System.out.print(root + " -> ");
        while (!stack.isEmpty()) {
            int count = 0;
            Node currentNode = stack.peek();

            for (Node x: currentNode.adjacent) {
                if (!x.color.equals(new Color(173,255,47))) {
                    stack.push(x);
                    
                    //System.out.print(currentNode.adjacent.get(i).name + " -> ");
                     try {
                        Thread.sleep(1500);
                    } catch (Exception e) {
                    }
                             canvas.repaint();

                    x.color =new Color(173,255,47);
                      for(Line line:canvas.lines)      //Changing edge color
                    {
                              if ((Math.sqrt(((x.centerX + x.radius / 2) - line.endX) * ((x.centerX + x.radius / 2) - line.endX) + ((x.centerY + x.radius / 2) - line.endY) * ((x.centerY + x.radius / 2) - line.endY)) <= x.radius  || Math.sqrt(((x.centerX + x.radius / 2) - line.startX) * ((x.centerX + x.radius / 2) - line.startX) + ((x.centerY + x.radius / 2) - line.startY) * ((x.centerY + x.radius / 2) - line.startY)) <= x.radius) && (Math.sqrt(((currentNode.centerX + currentNode.radius / 2) - line.endX) * ((currentNode.centerX + currentNode.radius / 2) - line.endX) + ((currentNode.centerY + currentNode.radius / 2) - line.endY) * ((currentNode.centerY + currentNode.radius / 2) - line.endY)) <= currentNode.radius  || Math.sqrt(((currentNode.centerX + currentNode.radius / 2) - line.startX) * ((currentNode.centerX + currentNode.radius / 2) - line.startX) + ((currentNode.centerY + currentNode.radius / 2) - line.startY) * ((currentNode.centerY + currentNode.radius / 2) - line.startY)) <= currentNode.radius)) 
                            
                                       line.color=new Color(148,0,211);
                              
                    }
                    break;
                } else {

                    count++;

                }

            }
            if (count == currentNode.adjacent.size()) {
                stack.pop();
            }

        }
        System.out.print(" END ");

        System.out.println("");

    }

}
