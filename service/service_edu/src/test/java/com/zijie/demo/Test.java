package com.zijie.demo;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author DZJ
 * @create 2021-10-25 10:49
 * @Description
 */
public class Test {

    @org.junit.Test
    public void test1() {
        int[] nums = {-1,0,1,2,-1,-4};
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        Arrays.sort(nums);
        for(int i = 0;i<nums.length;i++){
            int left = i + 1,right = nums.length - 1;
            int num = nums[i];
            if(i > 0 && num == nums[i - 1]) continue;
            while(left < right){
                if(num + nums[left] + nums[right] > 0){
                    --right;
                }
                if(num + nums[left] + nums[right] < 0){
                    ++left;
                }
                if(num + nums[left] + nums[right] == 0){
                    List<Integer> l = new ArrayList<>();
                    l.add(num);
                    l.add(nums[left]);
                    l.add(nums[right]);
                    list.add(l);
                    ++left;
                    --right;
                }
            }
        }
    }
}
