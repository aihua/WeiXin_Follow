package com.grouping.android.utils;

import com.grouping.android.mode.GroupingModel;

import java.util.Comparator;

/**
 * Created by sujizhong on 16/7/13.
 */
public class ClientComparator implements Comparator<GroupingModel> {
    @Override
    public int compare(GroupingModel lhs, GroupingModel rhs) {
        if (lhs.textGrouptitle.equals("@") || lhs.textGrouptitle.equals("#")) {
            return -1;
        } else if (rhs.textGrouptitle.equals("#") || rhs.textGrouptitle.equals("@")) {
            return -1;
        } else if (lhs.textGrouptitle.equals("[0-9]") || rhs.textGrouptitle.equals("[0-9]")) {
            return -1;
        } else if (lhs.textGrouptitle.charAt(0) > rhs.textGrouptitle.charAt(0)) {
            return 1;
        } else if (lhs.textGrouptitle.charAt(0) < rhs.textGrouptitle.charAt(0)) {
            return -1;
        } else if (lhs.textGrouptitle.charAt(0) == rhs.textGrouptitle.charAt(0)) {
            return 0;
        } else {
            return lhs.textGrouptitle.compareTo(rhs.textGrouptitle);
        }
    }
}

