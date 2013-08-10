package org.aaron.gwtetris.client.pieces;

import org.aaron.gwtetris.client.TetrisCoordinate;

import com.google.common.collect.ImmutableList;

public class SquarePiece extends AbstractTetrisPiece {

	private static ImmutableList<TetrisCoordinate> buildCoordinates(
			TetrisCoordinate centerCoordinate) {
		return ImmutableList.of(centerCoordinate, centerCoordinate.plusRows(1),
				centerCoordinate.plusColumns(1),
				centerCoordinate.plusRowsAndColumns(1, 1));
	}

	public SquarePiece(TetrisCoordinate centerCoordinate, int orientation) {
		super(centerCoordinate, TetrisPieceColor.GREEN, orientation,
				buildCoordinates(centerCoordinate));
	}

	public SquarePiece(TetrisCoordinate centerCoordinate) {
		this(centerCoordinate, 0);
	}

	@Override
	public int getNumOrientations() {
		return 1;
	}

	@Override
	public AbstractTetrisPiece makeTetrisPiece(
			TetrisCoordinate centerCoordinate, int orientation) {
		return new SquarePiece(centerCoordinate, orientation);
	}

}
