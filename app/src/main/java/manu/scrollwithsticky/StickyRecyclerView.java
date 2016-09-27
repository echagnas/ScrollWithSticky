package manu.scrollwithsticky;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

/**
 * Created by emmanuelchagnas on 30/08/16.
 */
public class StickyRecyclerView extends RecyclerView{

    private static final String TAG = "STICKYRECYCLERVIEW";

    private LinearLayoutManager linearLayoutManager;
    private TextView stickyText;
    private View stickyView;

    private TextView stickySubText;
    private View stickySubView;

    public StickyRecyclerView(Context context) {
        super(context);
        addOnScrollListener(new StickyScrollListener());
    }

    public StickyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        addOnScrollListener(new StickyScrollListener());
    }

    public StickyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        addOnScrollListener(new StickyScrollListener());
    }

    public void setStickyTitle(View stickyView){
        this.stickyView = stickyView;
        this.stickyText = (TextView)stickyView.findViewById(R.id.text);
        this.stickyView.setVisibility(View.INVISIBLE);
    }

    public void setStickySubTitle(View stickySubView){
        this.stickySubView = stickySubView;
        this.stickySubText = (TextView)stickySubView.findViewById(R.id.text);
        this.stickySubView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setLayoutManager(LayoutManager layoutManager) {
        super.setLayoutManager(layoutManager);
        this.linearLayoutManager = (LinearLayoutManager) layoutManager;
    }

    private class StickyScrollListener extends OnScrollListener{
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            //Log.d(TAG, "onScrollStateChanged: State = "+newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int firstVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition();
            //Log.d(TAG, "onScrolled: scrollY="+dy+" First visible = "+firstVisiblePosition+" Completly = "+linearLayoutManager.findFirstCompletelyVisibleItemPosition());
            Cell firstVisibleCell = ((StickyAdapter)getAdapter()).getCellAtPosition(firstVisiblePosition);

            //============ Set TITLE =============
            if(firstVisibleCell.getTypeView() == 2){
                stickyView.setVisibility(View.VISIBLE);
                stickySubView.setVisibility(View.INVISIBLE);
                stickyText.setText(firstVisibleCell.getText());
            }else{
                //Remonter la liste jusqu'à avoir un titre
                List<Cell> cellList = ((StickyAdapter) getAdapter()).getCellList();
                boolean findTitle = false;
                for(int i=firstVisiblePosition-1; i>-1; i--){
                    Cell cell = cellList.get(i);
                    if(cell.getTypeView() == 2){
                        stickyView.setVisibility(View.VISIBLE);
                        stickyText.setText(cell.getText());
                        findTitle = true;
                        break;
                    }
                }
                if(!findTitle){
                    stickyView.setVisibility(View.INVISIBLE);
                }
            }

            //Get view under Title sticky view
            int measuredHeight = stickyView.getMeasuredHeight();
            //Log.d(TAG, "current view height = "+measuredHeight);
            View childViewUnder = recyclerView.findChildViewUnder(0, measuredHeight);
            int tagPosition = (int)childViewUnder.getTag();
            //Log.d(TAG, "Tag position = "+tagPosition);
            Cell cellAtPosition = ((StickyAdapter) getAdapter()).getCellAtPosition(tagPosition);
            //============ Set SUBTITLE =============
            if(cellAtPosition.getTypeView() == 3){
                stickySubView.setVisibility(View.VISIBLE);
                stickySubText.setText(cellAtPosition.getText());
            }else{
                //Remonter la liste jusqu'à avoir un sous-titre
                List<Cell> cellList2 = ((StickyAdapter) getAdapter()).getCellList();
                boolean findSubTitle = false;
                for(int i=tagPosition-1; i>-1; i--){
                    Cell cell = cellList2.get(i);
                    if(cell.getTypeView() == 2){
                        break;
                    }
                    if(cell.getTypeView() == 3){
                        stickySubView.setVisibility(View.VISIBLE);
                        stickySubText.setText(cell.getText());
                        findSubTitle = true;
                        break;
                    }
                }
                if(!findSubTitle){
                    stickySubView.setVisibility(View.INVISIBLE);
                }
            }
        }
    }
}
