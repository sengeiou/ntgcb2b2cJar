package com.aebiz.app.dec.commons.comps.category.vo;


import com.aebiz.app.dec.commons.comps.category.StoreCategoryCompController;
import com.aebiz.app.dec.commons.vo.BaseCompModel;

/**
 * Created by 金辉 on 2016/12/15.
 */
public class StoreCategoryCompModel extends BaseCompModel {
    private static final long serialVersionUID = -1;

    /*分类展示类型*/
    private int showType =0;
    /*分类显示级数*/
    private int showLevel=0;
    /*是否显示图标*/
    private int isShowImage=0;
    /*第一级分类显示数量*/
    private int firstLevelNum = 7;
    /*第二级分类显示数量*/
    private int secondLevelNum = 3;

    /*分类展示类型：固定*/
    public static int SHOWTYPE_FIXED=0;
    /*分类展示类型：下拉*/
    public static int SHOWTYPE_DROP=1;
    /*分类显示级数：一级*/
    public static int SHOWLEVEL_ONE=0;
    /*分类显示级数：一级和二级*/
    public static int SHOWLEVEL_TWO=1;
    /*是否显示图标：是*/
    public static int IS_SHOW_IMAGE=0;
    /*是否显示图标：否*/
    public static int NOT_SHOW_IMAGE=1;

    public StoreCategoryCompModel() {
        super(StoreCategoryCompController.class,"/storeCategoryComp/toParamsDesign");
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public int getShowLevel() {
        return showLevel;
    }

    public void setShowLevel(int showLevel) {
        this.showLevel = showLevel;
    }

    public int getIsShowImage() {
        return isShowImage;
    }

    public void setIsShowImage(int isShowImage) {
        this.isShowImage = isShowImage;
    }

    public int getFirstLevelNum() {
        return firstLevelNum;
    }

    public void setFirstLevelNum(int firstLevelNum) {
        this.firstLevelNum = firstLevelNum;
    }

    public int getSecondLevelNum() {
        return secondLevelNum;
    }

    public void setSecondLevelNum(int secondLevelNum) {
        this.secondLevelNum = secondLevelNum;
    }
}
