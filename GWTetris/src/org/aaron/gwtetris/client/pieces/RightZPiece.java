package org.aaron.gwtetris.client.pieces;

import org.aaron.gwtetris.client.TetrisCoordinate;

import com.google.common.collect.ImmutableList;

public class RightZPiece extends AbstractTetrisPiece {

	private static ImmutableList<TetrisCoordinate> buildCoordinates(
			TetrisCoordinate centerCoordinate, int orientation) {
		switch (orientation) {
		case 0:
			return ImmutableList.of(centerCoordinate,
					centerCoordinate.plusColumns(1),
					centerCoordinate.plusRows(1),
					centerCoordinate.plusRowsAndColumns(1, -1));
		default:
			return ImmutableList.of(centerCoordinate,
					centerCoordinate.plusRows(-1),
					centerCoordinate.plusColumns(1),
					centerCoordinate.plusRowsAndColumns(1, 1));
		}
	}

	public RightZPiece(TetrisCoordinate centerCoordinate, int orientation) {
		super(centerCoordinate, TetrisPieceColor.ORANGE, orientation,
				buildCoordinates(centerCoordinate, orientation));

	}

	public RightZPiece(TetrisCoordinate centerCoordinate) {
		this(centerCoordinate, 0);
	}

	@Override
	public int getNumOrientations() {
		return 2;
	}

	@Override
	public TetrisPiece makeTetrisPiece(TetrisCoordinate centerCoordinate,
			int orientation) {
		return new RightZPiece(centerCoordinate, orientation);
	}

}
