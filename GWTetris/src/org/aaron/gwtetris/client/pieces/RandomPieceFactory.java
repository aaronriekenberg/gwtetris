package org.aaron.gwtetris.client.pieces;

import java.util.List;
import java.util.Random;

import org.aaron.gwtetris.client.TetrisConstants;
import org.aaron.gwtetris.client.TetrisCoordinate;

import com.google.common.collect.ImmutableList;

public class RandomPieceFactory {

	private final Random random = new Random();

	private final List<TetrisPiece> pieces;

	public RandomPieceFactory() {
		final TetrisCoordinate newPieceCenterCoordinate = TetrisCoordinate.of(
				0, (TetrisConstants.COLUMNS_SET.size() / 2) - 1);

		final ImmutableList.Builder<TetrisPiece> piecesListBuilder = new ImmutableList.Builder<TetrisPiece>();
		piecesListBuilder.add(new SquarePiece(newPieceCenterCoordinate));
		piecesListBuilder.add(new LeftLPiece(newPieceCenterCoordinate));
		piecesListBuilder.add(new RightLPiece(newPieceCenterCoordinate));
		piecesListBuilder.add(new LeftZPiece(newPieceCenterCoordinate));
		piecesListBuilder.add(new RightZPiece(newPieceCenterCoordinate));
		piecesListBuilder.add(new LinePiece(newPieceCenterCoordinate));
		piecesListBuilder.add(new TPiece(newPieceCenterCoordinate));

		this.pieces = piecesListBuilder.build();
	}

	public TetrisPiece createRandomPiece() {
		return pieces.get(random.nextInt(pieces.size()));
	}

}
