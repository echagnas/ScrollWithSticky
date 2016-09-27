package manu.scrollwithsticky;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by emmanuelchagnas on 30/08/16.
 */
public class StickyAdapter extends RecyclerView.Adapter<StickyAdapter.ViewHolder>{

    private List<Cell> cellList;

    public List<Cell> getCellList(){
        return cellList;
    }

    public void setCellList(List<Cell> cellList){
        this.cellList = cellList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType){
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty, parent, false);
                break;
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.normal, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.title, parent, false);
                break;
            case 3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subtitle, parent, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cell cell = cellList.get(position);
        if (cell.getTypeView() != 0) {
            holder.setText(cell.getText());
        }
        holder.setTag(position);
    }

    @Override
    public int getItemViewType(int position) {
        Cell cell = cellList.get(position);
        return cell.getTypeView();
    }

    @Override
    public int getItemCount() {
        return cellList != null ? cellList.size() : 0;
    }

    public Cell getCellAtPosition(int position){
        return cellList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }

        public void setText(String text){
            TextView textView = (TextView) view.findViewById(R.id.text);
            if(textView != null) {
                textView.setText(text);
            }
        }

        public void setTag(int tag) {
            view.setTag(tag);
        }
    }
}
