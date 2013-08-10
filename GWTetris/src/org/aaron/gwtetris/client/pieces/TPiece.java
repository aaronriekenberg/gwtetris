package org.aaron.gwtetris.client.pieces;

import org.aaron.gwtetris.client.TetrisCoordinate;

import com.google.common.collect.ImmutableList;

public class TPiece extends AbstractTetrisPiece {

	private static ImmutableList<TetrisCoordinate> buildCoordinates(
			TetrisCoordinate centerCoordinate, int orientation) {
		switch (orientation) {
		case 0:
			return ImmutableList.of(centerCoordinate,
					centerCoordinate.plusColumns(1),
					centerCoordinate.plusColumns(-1),
					centerCoordinate.plusRows(1));
		case 1:
			return ImmutableList.of(centerCoordinate.plusRows(-1),
					centerCoordinate, centerCoordinate.plusRows(1),
					centerCoordinate.plusColumns(1));
		case 2:
			return ImmutableList.of(centerCoordinate,
					centerCoordinate.plusColumns(1),
					centerCoordinate.plusColumns(-1),
					centerCoordinate.plusRows(-1));
		default:
			return ImmutableList.of(centerCoordinate.plusRows(-1),
					centerCoordinate, centerCoordinate.plusRows(1),
					centerCoordinate.plusColumns(-1));
		}
	}

	public TPiece(TetrisCoordinate centerCoordinate, int orientation) {
		super(centerCoordinate, TetrisPieceColor.CYAN, orientation,
				buildCoordinates(centerCoordinate, orientation));
	}

	public TPiece(TetrisCoordinate centerCoordinate) {
		this(centerCoordinate, 0);
	}

	@Override
	public int getNumOrientations() {
		return 4;
	}

	@Override
	public TetrisPiece makeTetrisPiece(TetrisCoordinate centerCoordinate,
			int orientation) {
		return new TPiece(centerCoordinate, orientation);
	}

}
