package view.ui.gameui.drawTile;

import view.ui.gameui.BoardSettings;

/**‰÷»æπ§≥ß*/
public class DrawTileFactory {
	private final int edge;
		
	public DrawTileFactory(BoardSettings settings) {
		edge = settings.getBlockEdge();
	}
	
	public BasicDrawTile getBasicDrawTile() {
		return new BasicDrawTile();
	}
	
	public EliminateDrawTile getEliminateDrawTile() {
		return new EliminateDrawTile();
	}
	
	public EmptyDrawTile getEmptyDrawTile() {
		return new EmptyDrawTile();
	}
	
	public FocusDrawTile getFocusDrawTile(DrawTile dw) {
		return new FocusDrawTile(dw);
	}
	
	public ItemDrawTile getItemDrawTile() {
		return new ItemDrawTile(edge);
	}
	
	public TipDrawTile getTipDrawTile(DrawTile dw) {
		return new TipDrawTile(dw);
	}
	
	public LockDrawTile getLockDrawTile(DrawTile dw) {
		return new LockDrawTile(dw);
	}
}
