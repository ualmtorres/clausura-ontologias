package GUI;



import java.util.*;
import java.awt.*;
//import java.applet.Applet;
import java.awt.event.*;
import javax.swing.*;


class Node {
    double x;
    double y;

    double dx;
    double dy;

    int tipo;//0 clase  1 interfaz 2 clase o interfaz derivada
    boolean fixed;

    String lbl;
}


class Edge {
    int from;
    int to;
    double len;
    int tipoEdge;//0 Extent, 1 Relacion, 2 Isa
}


class GraphPanel extends Panel
    implements Runnable, MouseListener, MouseMotionListener {
    Graph graph;
    int nnodes;
    Node nodes[] = new Node[100];

    int nedges;
    Edge edges[] = new Edge[200];

    Thread relaxer;
    boolean stress;
    boolean random;

    GraphPanel(Graph graph) {
	this.graph = graph;
	addMouseListener(this);
    }

    int findNode(String lbl,int tipo) {
	for (int i = 0 ; i < nnodes ; i++) {
	    if (nodes[i].lbl.equals(lbl)) {
		return i;
	    }
	}
	return addNode(lbl,tipo);//añadirle el tipo de clase que sera
    }
    int addNode(String lbl,int tipo) {
	Node n = new Node();
	n.x = 10 + 380*Math.random();
	n.y = 10 + 380*Math.random();
	n.lbl = lbl;
        n.tipo=tipo;
	nodes[nnodes] = n;
	return nnodes++;
    }
    void addEdge(String from, String to, int len, int tipo) {
	Edge e = new Edge();

       switch (tipo){
        case 0:{e.from = findNode(from,0);e.to = findNode(to,0);e.tipoEdge=0;}break;
        case 1:{e.from = findNode(from,0);e.to = findNode(to,0);e.tipoEdge=1;}break;
        case 2:{e.from = findNode(from,0);e.to = findNode(to,2);e.tipoEdge=0;}break;
        case 3:{e.from = findNode(from,0);e.to = findNode(to,2);e.tipoEdge=1;}break;
        case 4:{e.from = findNode(from,2);e.to = findNode(to,0);e.tipoEdge=0;}break;
        case 5:{e.from = findNode(from,2);e.to = findNode(to,0);e.tipoEdge=1;}break;
        case 6:{e.from = findNode(from,0);e.to = findNode(to,1);e.tipoEdge=2;}break;
        case 7:{e.from = findNode(from,2);e.to = findNode(to,1);e.tipoEdge=2;}break;
        case 8:{e.from = findNode(from,2);e.to = findNode(to,2);e.tipoEdge=0;}break;
        case 9:{e.from = findNode(from,2);e.to = findNode(to,2);e.tipoEdge=1;}break;
        case 10:{e.from = findNode(from,1);e.to = findNode(to,1);e.tipoEdge=2;}break;
        default:break;
       }
       if(!from.equalsIgnoreCase(to)){
        e.len = len;
	edges[nedges++] = e;
      }
    }

    public void run() {
        Thread me = Thread.currentThread();
	while (relaxer == me) {
	    relax();

	    if (random && (Math.random() < 0.03)) {
		Node n = nodes[(int)(Math.random() * nnodes)];
		if (!n.fixed) {
		    n.x += 100*Math.random() - 50;
		    n.y += 100*Math.random() - 50;
		}
	    }
	    try {
		Thread.sleep(100);
	    } catch (InterruptedException e) {
		break;
	    }
	}
    }

    synchronized void relax() {
	for (int i = 0 ; i < nedges ; i++) {
	    Edge e = edges[i];
	    double vx = nodes[e.to].x - nodes[e.from].x;
	    double vy = nodes[e.to].y - nodes[e.from].y;
	    double len = Math.sqrt(vx * vx + vy * vy);
            len = (len == 0) ? .0001 : len;
	    double f = (edges[i].len - len) / (len * 3);
	    double dx = f * vx;
	    double dy = f * vy;

	    nodes[e.to].dx += dx;
	    nodes[e.to].dy += dy;
	    nodes[e.from].dx += -dx;
	    nodes[e.from].dy += -dy;
	}

	for (int i = 0 ; i < nnodes ; i++) {
	    Node n1 = nodes[i];
	    double dx = 0;
	    double dy = 0;

	    for (int j = 0 ; j < nnodes ; j++) {
		if (i == j) {
		    continue;
		}
		Node n2 = nodes[j];
		double vx = n1.x - n2.x;
		double vy = n1.y - n2.y;
		double len = vx * vx + vy * vy;
		if (len == 0) {
		    dx += Math.random();
		    dy += Math.random();
		} else if (len < 100*100) {
		    dx += vx / len;
		    dy += vy / len;
		}
	    }
	    double dlen = dx * dx + dy * dy;
	    if (dlen > 0) {
		dlen = Math.sqrt(dlen) / 2;
		n1.dx += dx / dlen;
		n1.dy += dy / dlen;
	    }
	}

	Dimension d = getSize();
	for (int i = 0 ; i < nnodes ; i++) {
	    Node n = nodes[i];
	    if (!n.fixed) {
		n.x += Math.max(-5, Math.min(5, n.dx));
		n.y += Math.max(-5, Math.min(5, n.dy));
            }
            if (n.x <= 0) {
                n.x = 200;
            } else if (n.x > d.width) {
                n.x = d.width;
            }
            if (n.y <= 0) {
                n.y = 200;
            } else if (n.y > d.height) {
                n.y = d.height;
            }
	    n.dx /= 2;
	    n.dy /= 2;
	}
	repaint();
    }

    Node pick;
    boolean pickfixed;
    Image offscreen;
    Dimension offscreensize;
    Graphics offgraphics;

    final Color fixedColor = new Color(250, 220, 100);
    final Color selectColor = Color.pink;
    final Color edgeColor = Color.black;
    final Color nodeColor = new Color(250, 220, 100);
    final Color stressColor = Color.darkGray;
    final Color arcColor1 = Color.black;
    final Color arcColor2 = Color.pink;
    final Color arcColor3 = Color.red;
    //Métodos para pintar los nodos en la pantalla.... si es interfaz uso el round y si no el square
    public void paintNode(Graphics g, Node n, FontMetrics fm) {
	int x = (int)n.x;
	int y = (int)n.y;
	g.setColor((n == pick) ? selectColor : (n.fixed ? fixedColor : nodeColor));
	int w = fm.stringWidth(n.lbl) + 10;
	int h = fm.getHeight() + 4;
        //Realizo las comparaciones pertinentes para pintar la clase o la interfaz o la clase derivada
        switch(n.tipo){
        //Clase
        case 0:{ g.setColor((n == pick) ? selectColor : (n.fixed ? fixedColor : nodeColor));
                 g.fillRect(x - w/2, y - h / 2, w, h);
                 g.setColor(Color.black);
                 g.drawRect(x - w/2, y - h / 2, w-1, h-1);
                 g.drawString(n.lbl, x - (w-10)/2, (y - (h-4)/2) + fm.getAscent());
                }break;
        //Interfaz
        case 1:{ g.setColor(Color.cyan);
                 g.fillRoundRect(x - w/2, y - h / 2, w, h,10,10);
                 g.setColor(Color.black);
                 g.drawRoundRect(x - w/2, y - h / 2, w-1, h-1,10,10);
                 g.drawString(n.lbl, x - (w-10)/2, (y - (h-4)/2) + fm.getAscent());
                }break;
        //Clase derivada
        case 2:{ g.setColor(Color.yellow);
                 g.fillRect(x - w/2, y - h / 2, w, h);
                 g.setColor(Color.black);
                 g.drawRect(x - w/2, y - h / 2, w-1, h-1);
                 g.drawString(n.lbl, x - (w-10)/2, (y - (h-4)/2) + fm.getAscent());
                }break;
         //Interfaz Derivada
         case 3:{ g.setColor(Color.lightGray);
                  g.fillRoundRect(x - w/2, y - h / 2, w, h,10,10);
                  g.setColor(Color.black);
                  g.drawRoundRect(x - w/2, y - h / 2, w-1, h-1,10,10);
                  g.drawString(n.lbl, x - (w-10)/2, (y - (h-4)/2) + fm.getAscent());
                }break;

        }


    }

    public synchronized void update(Graphics g) {
	Dimension d = getSize();

	if ((offscreen == null) || (d.width != offscreensize.width) || (d.height != offscreensize.height)) {
	    offscreen = createImage(d.width, d.height);
	    offscreensize = d;
	    if (offgraphics != null) {
	        offgraphics.dispose();
	    }
	    offgraphics = offscreen.getGraphics();
	    offgraphics.setFont(getFont());
	}

	offgraphics.setColor(getBackground());
	offgraphics.fillRect(0, 0, d.width, d.height);
	for (int i = 0 ; i < nedges ; i++) {
	    Edge e = edges[i];
	    int x1 = (int)nodes[e.from].x;
	    int y1 = (int)nodes[e.from].y;
	    int x2 = (int)nodes[e.to].x;
	    int y2 = (int)nodes[e.to].y;
	    int len = (int)Math.abs(Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)) - e.len);
            Color colorCabeza=Color.black;
            //Se establece el color de las flechas
           if(e.tipoEdge==0){
              colorCabeza=Color.black;
	      offgraphics.setColor(colorCabeza);
            }
            if(e.tipoEdge==1){
                colorCabeza=Color.blue;
                offgraphics.setColor(colorCabeza);
            }
            if(e.tipoEdge==2){
              colorCabeza=Color.red;
              offgraphics.setColor(colorCabeza);
            }

            if((x2-x1)>=0){
               if((y2-y1)>=0) {
  	          offgraphics.drawLine(x1, y1, x2-10, y2-10);
               }else{
                   offgraphics.drawLine(x1, y1, x2-10, y2+10);
               }
            }else{

               if((y2-y1)>=0) {
  	          offgraphics.drawLine(x1, y1, x2+10, y2-10);
               }else{
                   offgraphics.drawLine(x1, y1, x2+10, y2+10);
              }

            }
            /////////////////////////////////////////////////////

                    int pos1X = x1;
                    int pos1Y = y1;
                    int pos2X = x2;
                    int pos2Y = y2;
                    int width = 20;
                    int height = 20;
                    int startAngle;
                    int angle = 60;
            if((x2-x1)>=0){
               if((y2-y1)>=0) {
                     pos1X = x1;
                     pos1Y = y1;
                     pos2X = x2-10;
                     pos2Y = y2-10;

               }else{
                    pos1X = x1;
                    pos1Y = y1;
                    pos2X = x2-10;
                    pos2Y = y2+10;

               }
            }else{

               if((y2-y1)>=0) {
  	          offgraphics.drawLine(x1, y1, x2+10, y2-10);
                    pos1X = x1;
                    pos1Y = y1;
                    pos2X = x2+10;
                    pos2Y = y2-10;

               }else{
                   offgraphics.drawLine(x1, y1, x2+10, y2+10);
                    pos1X = x1;
                    pos1Y = y1;
                    pos2X = x2+10;
                    pos2Y = y2+10;

              }

            }
                  // Draw the line
                  offgraphics.setColor (Color.black);
                  //  offgraphics.drawLine (pos1X, pos1Y, pos2X,pos2Y);

                    double lengthX = pos2X - pos1X;
                    double lengthY = pos2Y - pos1Y;

                    // Calulate the length of the line
                    double lineLength = Math.sqrt (lengthX * lengthX + lengthY * lengthY);

                    startAngle = (int)Math.toDegrees(Math.asin(lengthY/lineLength));
                    if (lengthX > 0)
                    startAngle = (180 - startAngle - 30);
                    else
                    startAngle = (startAngle - 30);

                    // Draw the arrow head
                     offgraphics.setColor(colorCabeza);
                     offgraphics.fillArc (pos2X - (width/2), pos2Y - (height/2)+1,width, height,startAngle,angle);
/////////////////////////////////////////////////////


	}

	FontMetrics fm = offgraphics.getFontMetrics();
        //Aqui pinta los nodos
	for (int i = 0 ; i < nnodes ; i++) {
	    paintNode(offgraphics, nodes[i], fm);
	}
	g.drawImage(offscreen, 0, 0, null);
    }

    //1.1 event handling
    public void mouseClicked(MouseEvent e) {
      if( e.getModifiers() == e.BUTTON3_MASK ) {
          this.stop();
      }

    }

    public void mousePressed(MouseEvent e) {
        addMouseMotionListener(this);
	double bestdist = Double.MAX_VALUE;
	int x = e.getX();
	int y = e.getY();
	for (int i = 0 ; i < nnodes ; i++) {
	    Node n = nodes[i];
	    double dist = (n.x - x) * (n.x - x) + (n.y - y) * (n.y - y);
	    if (dist < bestdist) {
		pick = n;
		bestdist = dist;
	    }
	}
	pickfixed = pick.fixed;
	pick.fixed = true;
	pick.x = x;
	pick.y = y;
	repaint();
	e.consume();
    }

    public void mouseReleased(MouseEvent e) {
        removeMouseMotionListener(this);
        if (pick != null) {
            pick.x = e.getX();
            pick.y = e.getY();
            pick.fixed = pickfixed;
            pick = null;
        }
	repaint();
	e.consume();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
	pick.x = e.getX();
	pick.y = e.getY();
	repaint();
	e.consume();
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void start() {
	relaxer = new Thread(this);
	relaxer.start();
    }

    public void stop() {
	relaxer = null;
    }

}


public class Graph extends Container implements ActionListener, ItemListener {

    GraphPanel panel;
    Panel controlPanel;


    public Graph(String estructura) {
	setLayout(new BorderLayout());

	panel = new GraphPanel(this);
	add("Center", panel);
	controlPanel = new Panel();

	//String edges ="joe-food,joe-dog,joe-tea,joe-cat,joe-table,table-plate,plate-food,food-mouse,mouse-cat,table-cup,cup-tea,dog-cat,cup-spoon,plate-fork,dog-flea1,dog-flea2,flea1-flea2,plate-knife";
        String edges=estructura;
	for (StringTokenizer t = new StringTokenizer(edges, ",") ; t.hasMoreTokens() ; ) {
	    String str = t.nextToken();
	    int i = str.indexOf('-');
	    if (i > 0) {
		int len = 70;
                int codigoTipoObjetos=0;
		int j = str.indexOf('/');//Ahora esto indica la longitud---> Debera indicar si es extend...etc
                // la barra inicaba la longitud de la linea...Lo reutilizo para indicar el tipo de clase que es.
		if (j > 0) {
		   // len = Integer.valueOf(str.substring(j+1)).intValue();
                   codigoTipoObjetos=Integer.valueOf(str.substring(j+1)).intValue();
		    str = str.substring(0, j);
		}

		panel.addEdge(str.substring(0,i), str.substring(i+1),len,codigoTipoObjetos);
	    }
	}
	Dimension d = getSize();
	String center =null;
	if (center != null){
	    Node n = panel.nodes[panel.findNode(center,0)];
	    n.x = d.width / 2;
	    n.y = d.height / 2;
	    n.fixed = true;
	}

       panel.start();
    }

    public void destroy() {
        remove(panel);
        remove(controlPanel);
    }



    public void stop() {
	panel.stop();
    }

    public void actionPerformed(ActionEvent e) {
	Object src = e.getSource();
    }

    public void itemStateChanged(ItemEvent e) {
	Object src = e.getSource();
	boolean on = e.getStateChange() == ItemEvent.SELECTED;
    }

}

