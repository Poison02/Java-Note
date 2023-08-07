package com.zch.iterator.social_networks;

import com.zch.iterator.iterators.ProfileIterator;

/**
 * 通用的社交网络接口
 * @author Zch
 * @date 2023/8/7
 **/
public interface SocialNetwork {

    ProfileIterator createFriendsIterator(String profileEmail);

    ProfileIterator createCoworkersIterator(String profileEmail);

}
