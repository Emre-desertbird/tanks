package tanks;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import tanks.TanksApp.STATE;

public class MouseInput implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		//PlayButton
		if(mx >= TanksApp.WIDTH / 2 + 100 && mx <= TanksApp.WIDTH + 200) {
			if(my >= 180 && my <= 220) {
				TanksApp.state = STATE.GAME;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
