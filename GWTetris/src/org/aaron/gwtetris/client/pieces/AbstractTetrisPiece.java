package org.aaron.gwtetris.client.pieces;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import org.aaron.gwtetris.client.TetrisCoordinate;

import com.google.common.collect.ImmutableList;

public abstract class AbstractTetrisPiece implements TetrisPiece {

	private final TetrisCoordinate centerCoordinate;

	private final TetrisPieceColor color;

	private final int orientation;

	private final ImmutableList<TetrisCoordinate> coordinates;

	public AbstractTetrisPiece(TetrisCoordinate centerCoordinate,
			TetrisPieceColor color, int orientation,
			ImmutableList<TetrisCoordinate> coordinates) {
		this.centerCoordinate = checkNotNull(centerCoordinate);
		this.color = checkNotNull(color);
		this.orientation = orientation;
		this.coordinates = checkNotNull(coordinates);
	}

	@Override
	public TetrisCoordinate getCenterCoordinate() {
		return centerCoordinate;
	}

	@Override
	public int getCenterRow() {
		return centerCoordinate.getRow();
	}

	@Override
	public int getCenterColumn() {
		return centerCoordinate.getColumn();
	}

	@Override
	public TetrisPieceColor getColor() {
		return color;
	}

	@Override
	public int getOrientation() {
		return orientation;
	}

	@Override
	public List<TetrisCoordinate> getCoordinates() {
		return coordinates;
	}

	@Override
	public int getNextOrientation() {
		return ((getOrientation() + 1) % getNumOrientations());
	}

	@Override
	public TetrisPiece cloneWithNewCenterCoordinate(
			TetrisCoordinate newCenterCoordinate) {
		return makeTetrisPiece(newCenterCoordinate, getOrientation());
	}

	@Override
	public TetrisPiece cloneWithNewCenterRow(int newCenterRow) {
		return cloneWithNewCenterCoordinate(TetrisCoordinate.of(newCenterRow,
				getCenterColumn()));
	}

	@Override
	public TetrisPiece cloneWithNewCenterColumn(int newCenterColumn) {
		return cloneWithNewCenterCoordinate(TetrisCoordinate.of(getCenterRow(),
				newCenterColumn));
	}

	@Override
	public TetrisPiece cloneWithNextOrientation() {
		return makeTetrisPiece(getCenterCoordinate(), getNextOrientation());
	}

}
