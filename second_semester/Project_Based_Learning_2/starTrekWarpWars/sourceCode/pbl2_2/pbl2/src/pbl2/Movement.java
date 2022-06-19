package pbl2;

import enigma.core.Enigma;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Movement {
	
	public static enigma.console.Console cn = Enigma.getConsole("Star Trek Warp Wars", 120,35,13);
	public KeyListener klis;
	public int keypr;
	public int rkey;
	
	Movement() throws Exception {
		klis=new KeyListener() {
	         public void keyTyped(KeyEvent e) {}
	         public void keyPressed(KeyEvent e) {
	            if(keypr==0) {
	               keypr=1;
	               rkey=e.getKeyCode();
	            }
	         }
	         public void keyReleased(KeyEvent e) {}
	    };
	    cn.getTextWindow().addKeyListener(klis);       
	}
}
