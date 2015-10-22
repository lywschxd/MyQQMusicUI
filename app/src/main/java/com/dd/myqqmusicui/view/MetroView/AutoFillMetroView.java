package com.dd.myqqmusicui.view.MetroView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

public class AutoFillMetroView extends MetroView {

    private boolean isHorizontal = false;

    private int mRows = 0, mCols = 0;

    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, MetroItem> mMetroPool = new HashMap<Integer, MetroItem>();

    public AutoFillMetroView(Context context) {
        super(context);
        initViewGroup(context);
    }

    public AutoFillMetroView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViewGroup(context);
    }

    public AutoFillMetroView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViewGroup(context);
    }

    private void initViewGroup(Context context) {
        mRows = 0;
        mCols = 0;

        mMetroPool.clear();
    }

    private int[] getPosition(View v, int rowspan, int colspan) {

        if (v == null)
            throw new IllegalArgumentException("invalid item");

        int[] position = new int[2];
        if (isHorizontal) {
            // check rowspan
            if (rowspan > visibleRows)
                throw new IllegalArgumentException("invalid row span");

            getPositionByCol(v, rowspan, colspan, position);
        } else {
            // check colspan
            if (colspan > visibleCols)
                throw new IllegalArgumentException("invalid col span");

            getPositionByRow(v, rowspan, colspan, position);
        }

        return position;
    }

    private MetroItem getNextItemByCol(MetroItem item, int direct) {
        MetroItem nextItem = null;

        // TODO:
        int row = item.getRow();
        int col = item.getCol();
        int nextRow = 0;
        int nextCol = 0;

        if (0 == direct) {
            // Notify: find next top focus item
            nextRow = row;
            nextCol = col;
            do {
                nextCol -= 1;
                if (nextCol < 0) {
                    return null;
                }

                int id = nextCol * visibleRows + nextRow;
                nextItem = mMetroPool.get(id);
                if (nextItem != null) {
                    break;
                }
            } while (nextItem == null);
        } else if (1 == direct) {
            // Notify: find next up focus item
            nextRow = row;
            nextCol = col + item.getColSpan() - 1;
            do {
                int tempCol = nextCol;
                nextRow -= 1;
                if (nextRow < 0) {
                    return null;
                }

                do {
                    int id = tempCol * visibleRows + nextRow;
                    nextItem = mMetroPool.get(id);
                    if (nextItem != null) {
                        if ((nextItem.getCol() + nextItem.getColSpan() - 1) != item
                                .getCol()) {
                            nextItem = null;
                        }
                        break;
                    }
                    tempCol -= 1;
                } while (nextItem == null && tempCol >= 0);
            } while (nextItem == null);
        }

        return nextItem;
    }

    private MetroItem getNextItemByRow(MetroItem item, int direct) {
        MetroItem nextItem = null;

        // TODO:

        return nextItem;
    }

    private MetroItem getNextItem(MetroItem item, int direct) {
        MetroItem nextItem = null;

        if (isHorizontal) {
            nextItem = getNextItemByCol(item, direct);
        } else {
            nextItem = getNextItemByRow(item, direct);
        }

        return nextItem;
    }

    private void updateItemPosition(MetroItem item) {
        int[] position = getPosition(item.getMetroView(), item.getRowSpan(),
                item.getColSpan());
        addMark(item);
        item.setPosition(position[0], position[1]);
    }

    private void getPositionByRow(View v, int rowspan, int colspan,
            int[] position) {

        boolean isAppended = false;

        // id = x + cols * y
        for (int y = 0; y < mRows; y++) {
            for (int x = 0; x < visibleCols; x++) {

                // check span
                if (x + colspan > visibleCols)
                    break;

                boolean hasSpace = true;
                for (int row = 0; row < rowspan; row++) {
                    for (int col = 0; col < colspan; col++) {

                        int id = (x + col) + visibleCols * (y + row);

                        if (mMetroPool.containsKey(id)) {
                            hasSpace = false;
                            break;
                        }
                    }

                    if (!hasSpace)
                        break;
                }

                if (hasSpace) {

                    position[0] = y;
                    position[1] = x;

                    isAppended = true;
                    break;
                }
            }

            if (isAppended) {
                break;
            }
        }

        if (!isAppended) {
            position[0] = mRows;
            position[1] = 0;
        }
    }

    private void getPositionByCol(View v, int rowspan, int colspan,
            int[] position) {

        boolean isAppended = false;

        // id = x * rows + y
        for (int x = 0; x < mCols; x++) {
            for (int y = 0; y < visibleRows; y++) {

                // check span
                if (y + rowspan > visibleRows)
                    break;

                boolean hasSpace = true;
                for (int row = 0; row < rowspan; row++) {
                    for (int col = 0; col < colspan; col++) {

                        int id = (x + col) * visibleRows + (y + row);

                        if (mMetroPool.containsKey(id)) {
                            hasSpace = false;
                            break;
                        }
                    }

                    if (!hasSpace)
                        break;
                }

                if (hasSpace) {

                    position[0] = y;
                    position[1] = x;

                    isAppended = true;
                    break;
                }
            }

            if (isAppended) {
                break;
            }
        }

        if (!isAppended) {
            position[0] = 0;
            position[1] = mCols;
        }
    }

    private void addMark(MetroItem item) {
        int row = item.getRow();
        int rowspan = item.getRowSpan();
        int col = item.getCol();
        int colspan = item.getColSpan();

        if (isHorizontal)
            mCols = Math.max(mCols, col + colspan);
        else
            mRows = Math.max(mRows, row + rowspan);

        // add flag
        for (int x = col; x < col + colspan; x++)
            for (int y = row; y < row + rowspan; y++) {

                int id = 0;

                if (isHorizontal)
                    id = x * visibleRows + y;
                else
                    id = x + visibleCols * y;

                mMetroPool.put(id, item);
                item.getMetroView().setId(id);
            }
    }

    private void setNextFocusItem(MetroItem item) {
        MetroItem leftItem = getNextItem(item, 0);
        if (leftItem != null) {
            if (leftItem.getMetroView().getNextFocusRightId() == -1) {
                leftItem.getMetroView().setNextFocusRightId(
                        item.getMetroView().getId());
            }
            item.getMetroView().setNextFocusLeftId(
                    leftItem.getMetroView().getId());
        }
        MetroItem upItem = getNextItem(item, 1);
        if (upItem != null) {
            Log.d("LIF", "row = " + item.getRow() + ", col = " + item.getCol());
            Log.d("LIF", "nextRow = " + upItem.getRow() + ", nextCol = "
                    + upItem.getCol());
            if (upItem.getMetroView().getNextFocusDownId() == -1) {
                upItem.getMetroView().setNextFocusDownId(
                        item.getMetroView().getId());
            }
            item.getMetroView().setNextFocusUpId(upItem.getMetroView().getId());
        }
    }

    @Override
    public void setVisibleItems(int rowCount, int colCount) {
        super.setVisibleItems(rowCount, colCount);

        // update item position
        initViewGroup(getContext());
        for (MetroItem item : mMetroItems) {
            updateItemPosition(item);
        }

        // update count
        if (isHorizontal)
            mColsCount = mCols;
        else
            mRowsCount = mRows;

        // goto 0,0
        // snapTo(0, 0);
    }

    @Override
    public void setOrientation(OrientationType orientation) {

        if (orientation != OrientationType.Horizontal
                && orientation != OrientationType.Vertical)
            throw new IllegalArgumentException("invalid orientation type");

        super.setOrientation(orientation);

        if (orientation == OrientationType.Horizontal)
            isHorizontal = true;
        else if (orientation == OrientationType.Vertical)
            isHorizontal = false;
    }

    public void addAutoFillMetroItem(AutoFillMetroItem item) {
        int[] position = getPosition(item.getMetroView(), item.getRowSpan(),
                item.getColSpan());
        MetroItem mi = new MetroItem(item.getMetroView(), position[0],
                item.getRowSpan(), position[1], item.getColSpan());
        addMark(mi);
        addMetroItem(mi);
        setNextFocusItem(mi);
    }
}
