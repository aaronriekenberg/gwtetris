package org.aaron.gwtetris.client.pieces;

import java.util.List;

import org.aaron.gwtetris.client.TetrisCoordinate;

public interface TetrisPiece {

	public TetrisCoordinate getCenterCoordinate();

	public int getCenterRow();

	public int getCenterColumn();

	public TetrisPieceColor getColor();

	public List<TetrisCoordinate> getCoordinates();

	public int getOrientation();

	public int getNumOrientations();

	public int getNextOrientation();

	public TetrisPiece makeTetrisPiece(TetrisCoordinate centerCoordinate,
			int orientation);

	public TetrisPiece cloneWithNewCenterCoordinate(
			TetrisCoordinate newCenterCoordinate);

	public TetrisPiece cloneWithNewCenterRow(int newCenterRow);

	public TetrisPiece cloneWithNewCenterColumn(int newCenterColumn);

	public TetrisPiece cloneWithNextOrientation();

}
