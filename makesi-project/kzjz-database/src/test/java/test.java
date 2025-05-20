//import org.junit.jupiter.api.Test;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Set;
//
//class Solution {
//
//    public int smallestDistancePair(int[] nums, int k) {
//        HashMap<Integer,Integer> map = new HashMap<>();
//        for (int i = 0; i < nums.length-1; ++i) {
//            for (int j = i+1; j < nums.length; ++j) {
//                int d =  Math.abs(nums[i]-nums[j]);
//                if(!map.containsKey(d)){
//                    map.put(d,1);
//                }else {
//                    int dnum = map.get(d);
//                    map.put(d,dnum+1);
//                }
//            }
//        }
//        Object[] arr = map.keySet().toArray();
//        Arrays.sort(arr);
//        int sum = 0;
//        int dd = 0;
//        for (Object o : arr) {
//            int io = (int) o;
//            sum += map.get(io);
//            if (sum >= k) {
//                dd = io;
//                break;
//            }
//        }
//        System.out.println(dd);
//        return dd;
//    }
//
//    @Test
//    public void test(){
//        smallestDistancePair(new int[]{62,100,4},2);
//    }
//}