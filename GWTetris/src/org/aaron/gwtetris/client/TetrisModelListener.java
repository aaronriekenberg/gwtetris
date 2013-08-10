package org.aaron.gwtetris.client;

public interface TetrisModelListener {

	public void handleTetrisModelUpdated(
			CurrentPieceUpdatedStatus currentPieceUpdatedStatus,
			StackCellsUpdatedStatus stackCellsUpdatedStatus);

}