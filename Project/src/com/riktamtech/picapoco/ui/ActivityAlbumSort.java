package com.riktamtech.picapoco.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.riktamtech.picapoco.R;
import com.riktamtech.picapoco.albumsortutils.DragController;
import com.riktamtech.picapoco.albumsortutils.DragLayer;
import com.riktamtech.picapoco.albumsortutils.DragSource;
import com.riktamtech.picapoco.albumsortutils.ImageCellAdapter;
import com.riktamtech.picapoco.beans.ReviewerDesignBean;

public class ActivityAlbumSort extends Activity implements OnClickListener,
		OnLongClickListener {
	private ImageView acceptButton;
	private ImageView rejectButton;
	private ReviewerDesignBean designBean;
	private DragController mDragController; // Object that handles a drag-drop
	// sequence. It intersacts with
	// DragSource and DropTarget
	// objects.
	private DragLayer mDragLayer; // The ViewGroup within which an object can be

	// dragged.

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		designBean = (ReviewerDesignBean) getIntent().getSerializableExtra(
				"designBean");
		setContentView(R.layout.activity_sortalbum);
		acceptButton = (ImageView) findViewById(R.id.AcceptButton);
		rejectButton = (ImageView) findViewById(R.id.CloseButton);
		GridView gridView = (GridView) findViewById(R.id.image_grid_view);

		gridView.setAdapter(new ImageCellAdapter(this, designBean));
		// gridView.setOnItemClickListener (this);

		mDragController = new DragController(this);
		mDragLayer = (DragLayer) findViewById(R.id.drag_layer);
		mDragLayer.setDragController(mDragController);
		mDragLayer.setGridView(gridView);

		mDragController.setDragListener(mDragLayer);
		acceptButton.setOnClickListener(this);
		rejectButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.AcceptButton:
			finish();
			break;
		case R.id.CloseButton:
			finish();
			break;
		default:
			break;
		}

	}

	/**
	 * Start dragging a view.
	 * 
	 */

	public boolean startDrag(View v) {
		DragSource dragSource = (DragSource) v;

		// We are starting a drag. Let the DragController handle it.
		mDragController.startDrag(v, dragSource, dragSource,
				DragController.DRAG_ACTION_MOVE);

		return true;
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		return startDrag(v);
	}
}
