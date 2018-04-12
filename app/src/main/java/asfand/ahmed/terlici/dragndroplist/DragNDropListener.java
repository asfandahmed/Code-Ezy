package asfand.ahmed.terlici.dragndroplist;

import android.view.View;

public class DragNDropListener implements DragNDropListView.OnItemDragNDropListener {
	
	public boolean onItemDragCalled = false;
	public boolean onItemDropCalled = false;
	public DragNDropListView parent = null;
	public View view = null;
	public int position = -1;
	public int startPosition = -1;
	public int endPosition = -1;
	public long id = -1;
	
	@Override
	public void onItemDrag(DragNDropListView parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		onItemDragCalled = true;
		this.parent = parent;
		this.view = view;
		this.position = position;
		this.id = id;
	}

	@Override
	public void onItemDrop(DragNDropListView parent, View view,
			int startPosition, int endPosition, long id) {
		// TODO Auto-generated method stub
		onItemDropCalled = true;
		this.parent = parent;
		this.view = view;
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.id = id;
	}

}
