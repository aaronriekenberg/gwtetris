package org.aaron.gwtetris.client;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;

import org.aaron.gwtetris.client.pieces.TetrisPieceColor;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Collections2;

public class StackCells {

	private final ArrayList<ArrayList<Optional<TetrisPieceColor>>> stackCells = new ArrayList<ArrayList<Optional<TetrisPieceColor>>>();

	public StackCells() {
		reset();
	}

	public void reset() {
		stackCells.clear();
		stackCells.addAll(buildEmptyStackCellsColumnsList());
	}

	public Optional<TetrisPieceColor> get(TetrisCoordinate tetrisCoordinate) {
		checkNotNull(tetrisCoordinate);
		checkArgument(tetrisCoordinate.isValid(),
				"Invalid tetrisCoordinate %s", tetrisCoordinate);

		return stackCells.get(tetrisCoordinate.getRow()).get(
				tetrisCoordinate.getColumn());
	}

	public void set(TetrisCoordinate tetrisCoordinate,
			Optional<TetrisPieceColor> colorOption) {
		checkNotNull(tetrisCoordinate);
		checkArgument(tetrisCoordinate.isValid(),
				"Invalid tetrisCoordinate %s", tetrisCoordinate);
		checkNotNull(colorOption);

		stackCells.get(tetrisCoordinate.getRow()).set(
				tetrisCoordinate.getColumn(), colorOption);
	}

	public boolean rowIsFull(int row) {
		checkArgument(TetrisConstants.ROWS_SET.contains(row), "Invalid row %s",
				row);

		return (!stackCells.get(row).contains(Optional.absent()));
	}

	public void removeRow(int row) {
		checkArgument(TetrisConstants.ROWS_SET.contains(row), "Invalid row %s",
				row);

		stackCells.remove(row);
		stackCells.add(0, buildEmptyStackCellsRowList());
	}

	private ArrayList<ArrayList<Optional<TetrisPieceColor>>> buildEmptyStackCellsColumnsList() {
		return new ArrayList<ArrayList<Optional<TetrisPieceColor>>>(
				Collections2
						.transform(
								TetrisConstants.ROWS_SET,
								new Function<Integer, ArrayList<Optional<TetrisPieceColor>>>() {
									@Override
									public ArrayList<Optional<TetrisPieceColor>> apply(
											Integer row) {
										return buildEmptyStackCellsRowList();
									}
								}));
	}

	private ArrayList<Optional<TetrisPieceColor>> buildEmptyStackCellsRowList() {
		return new ArrayList<Optional<TetrisPieceColor>>(
				Collections2.transform(TetrisConstants.COLUMNS_SET,
						new Function<Integer, Optional<TetrisPieceColor>>() {
							@Override
							public Optional<TetrisPieceColor> apply(
									Integer column) {
								return Optional.absent();
							}
						}));
	}

}
