import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.Box;
import javax.swing.SwingUtilities;

//EnCoded By Joy
public class MyThread extends Thread {

    volatile int delay=2;
    volatile boolean returnFrom;
    MyCanvas canvas;
    MyCanvas2 myCanvas2;
    Box box;
    Node root;
    MyWindow win;
    int f=1;
    volatile boolean iSpauSe=false;
     
    public void setCanvas(MyCanvas canvas) {
        this.canvas = canvas;
    }

    public void setWin(MyWindow win) {
        this.win = win;
    }
    
    

    public void setMyCanvas2(MyCanvas2 myCanvas2) {
        this.myCanvas2 = myCanvas2;
    }

    public void setBox(Box box) {
        this.box = box;
    }
    

    @Override
    public void run() {
        if(canvas.root!=null)
        {
        root=canvas.root;
        //System.out.println(root.name+"Joy");
        int z=0;
        if(f==1)
        {
        //Node root = canvas.nodes.get(0);
        Queue<Node> q = new LinkedList<Node>();
        Node currentNode;
        int level=0; 
        q.add(root);
        win.label.setText("Root iS EnQueued On The Queue");
        win.label.repaint();
        q.add(null);
        root.level=level;
        level++;
        TempNode tempNode = new TempNode(root.name, (myCanvas2.getWidth() / 2)-18, myCanvas2.getHeight() - 60 - z);
        myCanvas2.arrayList.add(tempNode);
       // myCanvas2.arrayList.add(tempNode);
        
        
          
       root.color =new Color(173,255,47);
       
                box.repaint();

         try {
                Thread.sleep(1000*(5-delay));
            } catch (Exception e) {
            }
        
        
        
        Node t = root;
        while (!q.isEmpty()) {
            
            while (iSpauSe) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }

            }
            
             currentNode = q.poll();
             if(currentNode!=null)
             {
                 try {
                     Thread.sleep(1000*(6-delay)/6);
                 } catch (Exception e) {
                 }
                 win.label.setText("DeQueueing "+myCanvas2.arrayList.get(0).name+" From The Queue");
                 win.label.repaint();
                 myCanvas2.arrayList.remove(0);
                 try {
                Thread.sleep(1000*(6-delay)/6);
            } catch (Exception e) {
            }
                 
                 
             }
             
     
            if(currentNode==null)
            {
                System.out.println();

                q.add(null);
                if(q.peek()==null) break;
                else
                    
                {
                    level++;
                    
                    //System.out.print("Level "+level+":");
                    continue;   
                }
             
             
             
            }
             
             boolean zc=false;
             for(int i=0;i<myCanvas2.arrayList.size();i++)
             {
                 TempNode x=myCanvas2.arrayList.get(i);
                 x.centerY=x.centerY+50;
                 zc=true;
                 
                // System.out.println("j");
                
             }
             
             if(!currentNode.equals(canvas.root))
             z-=50;
             
            
//               try {
//                Thread.sleep(1000);
//            } catch (Exception e) {
//            }                 
                                
                            
                            
             
                  box.repaint();

            
                
                
            
                         //System.out.println("joy");

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                          box.repaint();

                }
            });
            
            // t=currentNode;
            for (Node x : currentNode.adjacent) {
                if (!x.color.equals(new Color(173,255,47))) {
                    try {
                     Thread.sleep(1000*(6-delay));
                 } catch (Exception e) {
                 }
                    
                    
                    q.add(x);
                    
                    TempNode tn = new TempNode(x.name, (myCanvas2.getWidth() / 2)-18, myCanvas2.getHeight() - 60 - z);
                    z+=50;
                    myCanvas2.arrayList.add(tn);
                    win.label.setText("EnQueueing "+tn.name + " On The Queue");
                    win.label.repaint();
                    
                    x.color=new Color(173,255,47);
                    x.level=level;
                    
                    for(Line line:canvas.lines)      //Changing edge color
                    {
                              if ((Math.sqrt(((x.centerX + x.radius / 2) - line.endX) * ((x.centerX + x.radius / 2) - line.endX) + ((x.centerY + x.radius / 2) - line.endY) * ((x.centerY + x.radius / 2) - line.endY)) <= x.radius  || Math.sqrt(((x.centerX + x.radius / 2) - line.startX) * ((x.centerX + x.radius / 2) - line.startX) + ((x.centerY + x.radius / 2) - line.startY) * ((x.centerY + x.radius / 2) - line.startY)) <= x.radius) && (Math.sqrt(((currentNode.centerX + currentNode.radius / 2) - line.endX) * ((currentNode.centerX + currentNode.radius / 2) - line.endX) + ((currentNode.centerY + currentNode.radius / 2) - line.endY) * ((currentNode.centerY + currentNode.radius / 2) - line.endY)) <= currentNode.radius  || Math.sqrt(((currentNode.centerX + currentNode.radius / 2) - line.startX) * ((currentNode.centerX + currentNode.radius / 2) - line.startX) + ((currentNode.centerY + currentNode.radius / 2) - line.startY) * ((currentNode.centerY + currentNode.radius / 2) - line.startY)) <= currentNode.radius)) 
                            
                                       line.color=new Color(148,0,211);
                              
                    }
                    
                    
                    
                    
                   while (iSpauSe) {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                        }

                    }   
                    
                 box.repaint();
                      

                   

                }
                

            }
              
            try {
                Thread.sleep(1000*(6-delay)-1000);
            } catch (Exception e) {
            }
            while (iSpauSe) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }

            }
              
        }
        
        }
        
        
        win.label.setText("Execution Completed.");
        f=0;
        
    }
    }

}
