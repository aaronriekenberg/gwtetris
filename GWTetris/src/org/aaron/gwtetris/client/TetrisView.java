package org.aaron.gwtetris.client;

import java.util.HashMap;
import java.util.Map;

import org.aaron.gwtetris.client.pieces.TetrisPieceColor;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class TetrisView implements TetrisModelListener {

	// private static final Logger log = Logger.getLogger("TetrisView");

	private static final String CANVAS_HOLDER_ID = "canvasholder";

	private static final String LINES_HOLDER_ID = "linesholder";

	private static final String UPGRADE_MESSAGE = "Your browser does not support the HTML5 Canvas. Please upgrade your browser to view this demo.";

	private final HashMap<TetrisCoordinate, TetrisCellPixelCoordinates> tetrisCoordinatesToPixelCoordinate = new HashMap<TetrisCoordinate, TetrisCellPixelCoordinates>();

	private final CssColor black = CssColor.make(0, 0, 0);

	private final CssColor red = CssColor.make(255, 0, 0);

	private final CssColor green = CssColor.make(0, 255, 0);

	private final CssColor blue = CssColor.make(0, 0, 255);

	private final CssColor yellow = CssColor.make(255, 255, 0);

	private final CssColor orange = CssColor.make(255, 102, 0);

	private final CssColor grey = CssColor.make(212, 212, 212);

	private final CssColor cyan = CssColor.make(0, 255, 255);

	private final TetrisModel model;

	private Canvas canvas = null;

	private int previousCanvasWidth = 0;

	private int previousCanvasHeight = 0;

	private Label linesLabel = null;

	public TetrisView(TetrisModel model) {
		this.model = model;

		canvas = Canvas.createIfSupported();
		if (canvas == null) {
			RootPanel.get(CANVAS_HOLDER_ID).add(new Label(UPGRADE_MESSAGE));
			return;
		}

		// init the canvases
		canvas.setWidth(300 + "px");
		canvas.setHeight(550 + "px");
		canvas.setCoordinateSpaceWidth(300);
		canvas.setCoordinateSpaceHeight(550);

		RootPanel.get(CANVAS_HOLDER_ID).add(canvas);

		linesLabel = new Label();
		RootPanel.get(LINES_HOLDER_ID).add(linesLabel);

		model.registerListener(this);
		handleTetrisModelUpdated(
				CurrentPieceUpdatedStatus.CURRENT_PIECE_UPDATED,
				StackCellsUpdatedStatus.STACK_CELLS_UPDATED);
	}

	public Canvas getCanvas() {
		return canvas;
	}

	private void updatePixelCoordinates() {
		if ((previousCanvasWidth == canvas.getCoordinateSpaceWidth())
				&& (previousCanvasHeight == canvas.getCoordinateSpaceHeight())) {
			return;
		}

		previousCanvasWidth = canvas.getCoordinateSpaceWidth();
		previousCanvasHeight = canvas.getCoordinateSpaceHeight();

		tetrisCoordinatesToPixelCoordinate.clear();

		final int canvasWidth = canvas.getCoordinateSpaceWidth() - 1;
		final int canvasHeight = canvas.getCoordinateSpaceHeight() - 1;

		final int normalWidthPixels = (canvasWidth - 1)
				/ TetrisConstants.COLUMNS_SET.size();
		final int extraWidthPixels = (canvasWidth - 1)
				% TetrisConstants.COLUMNS_SET.size();
		final int normalHeightPixels = (canvasHeight - 1)
				/ TetrisConstants.ROWS_SET.size();
		final int extraHeightPixels = (canvasHeight - 1)
				% TetrisConstants.ROWS_SET.size();

		int currentYCoordinate = 1;
		for (int row : TetrisConstants.ROWS_SET) {
			final int rowHeight;
			if (row < extraHeightPixels) {
				rowHeight = normalHeightPixels + 1;
			} else {
				rowHeight = normalHeightPixels;
			}
			int currentXCoordinate = 1;
			for (int column : TetrisConstants.COLUMNS_SET) {
				final int width;
				if (column < extraWidthPixels) {
					width = normalWidthPixels + 1;
				} else {
					width = normalWidthPixels;
				}
				tetrisCoordinatesToPixelCoordinate.put(TetrisCoordinate.of(row,
						column), new TetrisCellPixelCoordinates(
						currentXCoordinate, currentYCoordinate, width,
						rowHeight));
				currentXCoordinate += width;
			}
			currentYCoordinate += rowHeight;
		}
	}

	private CssColor translateColor(TetrisPieceColor color) {
		CssColor cssColor = black;
		switch (color) {
		case BLUE:
			cssColor = blue;
			break;
		case GREEN:
			cssColor = green;
			break;
		case RED:
			cssColor = red;
			break;
		case YELLOW:
			cssColor = yellow;
			break;
		case ORANGE:
			cssColor = orange;
			break;
		case GREY:
			cssColor = grey;
			break;
		case CYAN:
			cssColor = cyan;
			break;
		}
		return cssColor;
	}

	public void redrawCanvas() {
		if (canvas == null) {
			return;
		}

		final Context2d context = canvas.getContext2d();

		final int canvasWidth = canvas.getCoordinateSpaceWidth();
		final int canvasHeight = canvas.getCoordinateSpaceHeight();

		updatePixelCoordinates();

		context.setFillStyle(black);
		context.fillRect(0, 0, canvasWidth, canvasHeight);

		final Map<TetrisCoordinate, TetrisPieceColor> drawableCells = model
				.getAllDrawableCells();

		for (TetrisCoordinate tetrisCoordinate : TetrisCoordinates.ALL_VALID_TETRIS_COORDINATES) {
			final TetrisPieceColor color = drawableCells.get(tetrisCoordinate);
			if (color != null) {
				final TetrisCellPixelCoordinates pixelCoordinates = tetrisCoordinatesToPixelCoordinate
						.get(tetrisCoordinate);
				context.setFillStyle(translateColor(color));
				context.fillRect(pixelCoordinates.getX(),
						pixelCoordinates.getY(), pixelCoordinates.getWidth(),
						pixelCoordinates.getHeight());

				context.setStrokeStyle(black);
				context.strokeRect(pixelCoordinates.getX(),
						pixelCoordinates.getY(), pixelCoordinates.getWidth(),
						pixelCoordinates.getHeight());
			}
		}

		canvas.setFocus(true);
	}

	private void updateLinesLabel() {
		if (linesLabel == null) {
			return;
		}

		if (model.isPaused()) {
			linesLabel.setText("Paused");
		} else if (model.isGameOver()) {
			linesLabel.setText("Game Over");
		} else {
			linesLabel.setText("Num Lines: " + model.getNumLines());
		}
	}

	@Override
	public void handleTetrisModelUpdated(
			CurrentPieceUpdatedStatus currentPieceUpdatedStatus,
			StackCellsUpdatedStatus stackCellsUpdatedStatus) {
		redrawCanvas();
		updateLinesLabel();
	}
}
