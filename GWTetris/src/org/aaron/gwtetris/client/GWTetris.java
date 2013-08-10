package org.aaron.gwtetris.client;

import java.util.logging.Logger;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Timer;

public class GWTetris implements EntryPoint {

	private static final Logger log = Logger.getLogger("GWTetris");

	private TetrisModel model = null;

	private TetrisView view = null;

	private Timer timer = null;

	public void onModuleLoad() {
		log.info("begin onModuleLoad");

		model = new TetrisModel();

		view = new TetrisView(model);

		setupKeyboardHandler();

		timer = new Timer() {
			@Override
			public void run() {
				timerPop();
			}
		};
		timer.scheduleRepeating(250);
	}

	private void setupKeyboardHandler() {
		final Canvas canvas = view.getCanvas();
		if (canvas != null) {
			canvas.addKeyDownHandler(new KeyDownHandler() {
				@Override
				public void onKeyDown(KeyDownEvent event) {
					log.info("onKeyDown " + event.getNativeKeyCode());
					switch (event.getNativeKeyCode()) {
					case KeyCodes.KEY_LEFT:
						model.moveCurrentPieceLeft();
						break;
					case KeyCodes.KEY_RIGHT:
						model.moveCurrentPieceRight();
						break;
					case KeyCodes.KEY_DOWN:
						model.moveCurrentPieceDown();
						break;
					case ' ':
						model.dropCurrentPiece();
						break;
					case KeyCodes.KEY_UP:
						model.rotateCurrentPiece();
						break;
					case 'P':
						model.togglePause();
						break;
					case 'R':
						model.reset();
						break;
					}
				}
			});
		}
	}

	private void timerPop() {
		log.info("timerPop");
		model.periodicUpdate();
	}
}
