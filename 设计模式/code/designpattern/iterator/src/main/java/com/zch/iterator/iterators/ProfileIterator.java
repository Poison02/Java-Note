package com.zch.iterator.iterators;

import com.zch.iterator.profile.Profile;

/**
 * 定义档案接口
 * @author Zch
 * @date 2023/8/7
 **/
public interface ProfileIterator {

    boolean hasNext();

    Profile getNext();

    void reset();

}
