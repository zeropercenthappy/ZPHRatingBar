package com.zeropercenthappy.ratingbarlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ybq
 *         2017/5/3
 */

public class ZPHRatingBar extends LinearLayout implements View.OnClickListener {
    private float itemWidth;
    private float itemHeight;
    private float itemSpacing;
    private int itemCountNumber;
    private int itemSelectedNumber;
    private Drawable itemSelected;
    private Drawable itemUnSelected;
    private boolean itemClickAble;
    private OnRatingClickCallback onRatingClickCallback;
    private List<ItemModel> itemModelList;

    public ZPHRatingBar(Context context) {
        super(context);
    }

    public ZPHRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        itemModelList = new ArrayList<>();
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ZPHRatingBar);
        itemWidth = typedArray.getDimension(R.styleable.ZPHRatingBar_itemWidth, 60);
        itemHeight = typedArray.getDimension(R.styleable.ZPHRatingBar_itemHeight, 60);
        itemSpacing = typedArray.getDimension(R.styleable.ZPHRatingBar_itemSpacing, 30);
        itemCountNumber = typedArray.getInteger(R.styleable.ZPHRatingBar_itemCountNumber, 5);
        itemSelectedNumber = typedArray.getInteger(R.styleable.ZPHRatingBar_itemSelectedNumber, 0);
        itemSelected = typedArray.getDrawable(R.styleable.ZPHRatingBar_itemSelected);
        itemUnSelected = typedArray.getDrawable(R.styleable.ZPHRatingBar_itemUnSelected);
        itemClickAble = typedArray.getBoolean(R.styleable.ZPHRatingBar_itemClickAble, false);
        typedArray.recycle();
        initView();
    }

    public OnRatingClickCallback getOnRatingClickCallback() {
        return onRatingClickCallback;
    }

    public void setOnRatingClickCallback(OnRatingClickCallback onRatingClickCallback) {
        this.onRatingClickCallback = onRatingClickCallback;
    }

    public int getItemCountNumber() {
        return itemCountNumber;
    }

    public void setItemCountNumber(int itemCountNumber) {
        this.itemCountNumber = itemCountNumber;
        initView();
    }

    public int getItemSelectedNumber() {
        return itemSelectedNumber;
    }

    public void setItemSelectedNumber(int itemSelectedNumber) {
        this.itemSelectedNumber = itemSelectedNumber;
        initView();
    }

    private void initView() {
        itemModelList.clear();
        removeAllViews();

        if (itemSelectedNumber < 0) {
            itemSelectedNumber = 0;
        }
        if (itemCountNumber < 0) {
            itemCountNumber = 5;
        }

        if (itemSelectedNumber > 0 && itemSelectedNumber <= itemCountNumber) {
            //已选择的item小于或等于总item
            //已选择
            for (int i = 0; i < itemSelectedNumber; i++) {
                ImageView imageView;
                if (i == 0) {
                    //第一个item
                    imageView = createImageView(true, 0);
                } else {
                    //中间的item
                    imageView = createImageView(true, 1);
                }
                addView(imageView);
            }
            //未选择
            for (int i = 0; i < itemCountNumber - itemSelectedNumber; i++) {
                ImageView imageView;
                if (i == itemCountNumber - itemSelectedNumber - 1) {
                    //最后一个item
                    imageView = createImageView(false, 2);
                } else {
                    //中间的item
                    imageView = createImageView(false, 1);
                }
                addView(imageView);
            }
        } else if (itemSelectedNumber > 0 && itemSelectedNumber > itemCountNumber) {
            //已选择的item大于总item
            for (int i = 0; i < itemCountNumber; i++) {
                ImageView imageView;
                if (i == 0) {
                    //第一个item
                    imageView = createImageView(true, 0);
                } else if (i == itemCountNumber - 1) {
                    //最后一个item
                    imageView = createImageView(true, 2);
                } else {
                    //中间的item
                    imageView = createImageView(true, 1);
                }
                addView(imageView);
            }
        } else if (itemSelectedNumber == 0) {
            //没有选择的item
            for (int i = 0; i < itemCountNumber; i++) {
                ImageView imageView;
                if (i == 0) {
                    //第一个item
                    imageView = createImageView(false, 0);
                } else if (i == itemCountNumber - 1) {
                    //最后一个item
                    imageView = createImageView(false, 2);
                } else {
                    //中间的item
                    imageView = createImageView(false, 1);
                }
                addView(imageView);
            }
        }
    }

    /**
     * 创建item的ImageView
     *
     * @param selected     是否选中状态
     * @param positionType item的位置类型（0：第一个，1：中间的，2：最后一个）
     */
    private ImageView createImageView(boolean selected, int positionType) {
        ImageView imageView = new ImageView(getContext());

        if (selected) {
            imageView.setImageDrawable(itemSelected);
        } else {
            imageView.setImageDrawable(itemUnSelected);
        }

        LayoutParams layoutParams = new LayoutParams(Math.round(itemWidth), Math.round(itemHeight));
        if (positionType == 0) {
            layoutParams.setMargins(Math.round(itemSpacing), 0, Math.round(itemSpacing / 2), 0);
        } else if (positionType == 1) {
            layoutParams.setMargins(Math.round(itemSpacing / 2), 0, Math.round(itemSpacing / 2), 0);
        } else if (positionType == 2) {
            layoutParams.setMargins(Math.round(itemSpacing / 2), 0, Math.round(itemSpacing), 0);
        }
        imageView.setLayoutParams(layoutParams);

        if (itemClickAble) {
            imageView.setOnClickListener(this);
        }

        if (itemModelList == null) {
            itemModelList = new ArrayList<>();
        }

        ItemModel itemModel = new ItemModel();
        itemModel.imageView = imageView;
        itemModel.position = itemModelList.size();
        itemModel.selected = selected;
        itemModelList.add(itemModel);

        return imageView;
    }

    @Override
    public void onClick(View view) {
        int clickPosition = -1;

        if (itemModelList != null) {
            for (ItemModel itemModel : itemModelList) {
                if (view == itemModel.imageView) {
                    clickPosition = itemModel.position;
                    break;
                }
            }
        }

        if (itemModelList != null && clickPosition != -1) {
            setItemSelectedNumber(clickPosition + 1);
            if (onRatingClickCallback != null) {
                onRatingClickCallback.onItemClick(itemModelList.get(clickPosition).position);
            }
        }

    }
}
