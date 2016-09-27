package manu.scrollwithsticky;

/**
 * Created by emmanuelchagnas on 30/08/16.
 */
public class Cell {


    //EMPTY = 0
    //NORMAL = 1
    //TITLE = 2
    //SUBTITLE = 3

    public enum CELL_TYPE{
        TITLE, SUBTITLE, EMPTY, NORMAL
    }

    private CELL_TYPE type;
    private String text;

    public Cell(CELL_TYPE type, String text){
        this.type = type;
        this.text = text;
    }

    public int getTypeView(){
        switch(type){
            case EMPTY:
                return 0;
            case NORMAL:
                return 1;
            case TITLE:
                return 2;
            case SUBTITLE:
                return 3;
        }
        return 0;
    }

    public String getText(){
        return text;
    }
}
